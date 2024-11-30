package app.src.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import app.src.main.pieces.Piece;

public class AlphaBeta {
    private static final int DEPTH = 5;
    private Move optimalMove;
    private Panel panel;

    public AlphaBeta(Panel panel) {
        this.panel = panel;
    }

    private List<Move> getMoves(int player) {
        List<Move> moves = new ArrayList<>();
        List<Piece> playerPieces = Panel.pieces.stream().filter(p -> p.getColour() == player)
                .collect(Collectors.toList());

        for (Piece piece : playerPieces) {
            moves.addAll(getMoves(piece));
        }

        // sort moves based on most valuable piece
        moves = moves.stream().sorted(Comparator.comparingInt(m -> m.getPiece().getValue()))
                .collect(Collectors.toList());

        return moves;

    }

    private List<Move> getMoves(Piece piece) {
        List<Move> moves = new ArrayList<>();
        for (int rank = 0; rank < Board.NUM_RANKS; rank++) {
            for (int file = 0; file < Board.NUM_FILES; file++) {
                // save the game state
                boardBeforeMove board = new boardBeforeMove(Panel.pieces,
                        Panel.whiteMaterial, Panel.blackMaterial,
                        null, Panel.playerTurn);

                if (piece.canMove(rank, file)) {
                    moves.add(new Move(piece, rank, file));
                    // set the panel back to what it was pre-computation
                    panel.reset(board.getPieces(), board.getWhiteMaterial(),
                            board.getBlackMaterial(), board.getCurrentPiece(),
                            board.getPlayerTurn());
                }
            }
        }

        return moves;
    }

    public Move getOptimalMove() {
        maximiser(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return optimalMove;
    }

    private int maximiser(int depth, int alpha, int beta) {
        if (depth == 0) {
            return Panel.blackMaterial - Panel.whiteMaterial;
        }

        List<Move> possibleMoves = getMoves(Board.BLACK);

        for (Move move : possibleMoves) {
            // save the game state
            boardBeforeMove board = new boardBeforeMove(Panel.pieces,
                    Panel.whiteMaterial, Panel.blackMaterial,
                    null, Panel.playerTurn);

            move.getPiece().canMove(move.getRank(), move.getFile());
            int score = minimiser(depth - 1, alpha, beta);

            if (score > alpha) {
                alpha = score;
                if (depth == DEPTH) {
                    // set the panel back to what it was pre-computation
                    panel.reset(board.getPieces(), board.getWhiteMaterial(),
                            board.getBlackMaterial(), board.getCurrentPiece(),
                            board.getPlayerTurn());

                    optimalMove = move;
                }
            }
            // pruning
            if (alpha >= beta) {
                return alpha;
            }
        }

        return alpha;
    }

    private int minimiser(int depth, int alpha, int beta) {
        if (depth == 0) {
            return -(Panel.blackMaterial - Panel.whiteMaterial);
        }

        List<Move> possibleMoves = getMoves(Board.WHITE);

        for (Move move : possibleMoves) {
            move.getPiece().canMove(move.getRank(), move.getFile());
            int score = maximiser(depth - 1, alpha, beta);

            if (score <= beta) {
                beta = score;
            }

            // pruning
            if (alpha >= beta) {
                return beta;
            }
        }

        return beta;
    }

}
