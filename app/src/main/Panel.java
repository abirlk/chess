package app.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.src.main.pieces.*;

public class Panel extends JPanel {
    public boolean initialised = false;
    public Mouse mouse = new Mouse(this);
    public static List<Piece> pieces = new ArrayList<Piece>();

    public static int whiteMaterial;
    public static int blackMaterial;

    private Piece currentPiece;
    public static int playerTurn = Board.WHITE;

    public void createGrid(Graphics2D g) {
        for (int i = 0; i < Board.NUM_RANKS; i++) {
            for (int j = 0; j < Board.NUM_FILES; j++) {
                boolean isDark = (i + j) % 2 == 0;
                if (isDark) {
                    g.setColor(new Color(90, 90, 90));
                } else {
                    g.setColor(new Color(220, 220, 220));
                }
                g.fillRect(i * Board.SQUARE_SIZE, j * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            }
        }

    }

    public static void switchPlayer() {
        switch (playerTurn) {
            case Board.WHITE:
                playerTurn = Board.BLACK;
                return;
            case Board.BLACK:
                playerTurn = Board.WHITE;
                return;
            default:
                return;
        }
    }

    private void addPawns(int colour) {
        // white pawns on rank 2, black pawns on rank 7
        int rank = (colour == 1) ? 6 : 1;
        for (int i = 0; i < Board.NUM_RANKS; i++) {
            pieces.add(new Pawn(colour, rank, i));
        }
    }

    private void addKnights(int colour) {
        // white knight on rank 1, black knight on rank 8, file 2, 7
        int rank = (colour == 1) ? 7 : 0;
        pieces.add(new Knight(colour, rank, 1));
        pieces.add(new Knight(colour, rank, 6));
    }

    private void addRooks(int colour) {
        // white rook on rank 1, black rook on rank 8, file 1, 8
        int rank = (colour == 1) ? 7 : 0;
        pieces.add(new Rook(colour, rank, 0));
        pieces.add(new Rook(colour, rank, 7));
    }

    private void addKing(int colour) {
        // white king on rank 1, black rook on rank 8, file 5
        int rank = (colour == 1) ? 7 : 0;
        pieces.add(new King(colour, rank, 4));
    }

    private void addQueen(int colour) {
        // white queen on rank 1, black queen on rank 8, file 4,
        int rank = (colour == 1) ? 7 : 0;
        pieces.add(new Queen(colour, rank, 3));
    }

    private void addBishops(int colour) {
        // white bishop on rank 1, white rook on rank 8, file 3
        int rank = (colour == 1) ? 7 : 0;
        pieces.add(new Bishop(colour, rank, 2));
        pieces.add(new Bishop(colour, rank, 5));
    }

    public void initialPlacements() {
        addPawns(Board.WHITE);
        addKnights(Board.WHITE);
        addKing(Board.WHITE);
        addQueen(Board.WHITE);
        addRooks(Board.WHITE);
        addBishops(Board.WHITE);

        addPawns(Board.BLACK);
        addKnights(Board.BLACK);
        addKing(Board.BLACK);
        addQueen(Board.BLACK);
        addRooks(Board.BLACK);
        addBishops(Board.BLACK);
    }

    public void checkEndGame() {
        // check if remaining pieces are all of one colour
        boolean noWhite = !pieces.stream().anyMatch(p -> p.getColour() == Board.WHITE);
        boolean noBlack = !pieces.stream().anyMatch(p -> p.getColour() == Board.BLACK);

        if (noWhite) {
            JOptionPane.showMessageDialog(this, "You have won!");
        } else if (noBlack) {
            JOptionPane.showMessageDialog(this, "Computer has won!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        createGrid(g2);

        if (initialised == false) {
            initialPlacements();
            addMouseListener(mouse);
            initialised = true;
        }

        for (Piece piece : pieces) {
            piece.addImage(g2);
        }

        if (currentPiece != null) {
            // highlight square of the piece
            g.setColor(Color.BLUE);
            g.drawRect(currentPiece.getX(), currentPiece.getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE);
        }
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public static void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void reset(List<Piece> pieces, int whiteMaterial, int blackMaterial, Piece currentPiece,
            int playerTurn) {
        Panel.pieces = new ArrayList<>(pieces);
        Panel.blackMaterial = blackMaterial;
        Panel.whiteMaterial = whiteMaterial;
        this.currentPiece = currentPiece;
        Panel.playerTurn = playerTurn;
    }

    public void computerMove() {
        // save the game state
        boardBeforeMove board = new boardBeforeMove(Panel.pieces, Panel.whiteMaterial, Panel.blackMaterial,
                currentPiece, Panel.playerTurn);

        AlphaBeta computer = new AlphaBeta(this);
        Move optimalMove = computer.getOptimalMove();

        // set the panel back to what it was pre-computation
        reset(board.getPieces(), board.getWhiteMaterial(), board.getBlackMaterial(), board.getCurrentPiece(),
                board.getPlayerTurn());

        for (Piece piece : pieces) {
            if (piece.getColour() == optimalMove.getPiece().getColour()
                    && piece.getRank() == optimalMove.getPiece().getRank()
                    && piece.getFile() == optimalMove.getPiece().getFile()) {

                // make the move
                // problem here is there emight be a capture and we are iterating through the
                // loop -> concurrency issue
                piece.canMove(optimalMove.getRank(), optimalMove.getFile());
                piece.setRank(optimalMove.getRank());
                piece.setFile(optimalMove.getFile());

                repaint();
                checkEndGame();

                setCurrentPiece(null);
                Panel.switchPlayer();
            }
        }
    }

}
