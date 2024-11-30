package app.src.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import app.src.main.pieces.Piece;

public class Mouse extends MouseAdapter {
    public int x, y;
    Panel panel;

    public Mouse(Panel panel) {
        this.panel = panel;
    }

    private boolean pieceClicked(Piece piece, int x, int y) {
        if ((piece.getRank() == x) && (piece.getFile() == y)) {
            return true;
        }

        return false;
    }

    private Piece getPieceOnClick(int x, int y) {
        for (Piece piece : Panel.pieces) {
            if ((piece.getColour() == Panel.playerTurn) && pieceClicked(piece, x, y)) {
                panel.setCurrentPiece(piece);
                return piece;
            }
        }

        return null;
    }

    private void movePiece() {
        // place the piece in the place where mouse released
        // note that the rank is the y coord of the mouse divided by the tile size
        // and opposite for the file
        panel.getCurrentPiece().setRank(y / Board.SQUARE_SIZE);
        panel.getCurrentPiece().setFile(x / Board.SQUARE_SIZE);

        panel.repaint();
        panel.checkEndGame();

        panel.setCurrentPiece(null);
        Panel.switchPlayer();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if (panel.getCurrentPiece() == null) {
            // this sets the current piece if the mouse pressed on a piece
            if (getPieceOnClick(y / Board.SQUARE_SIZE, x / Board.SQUARE_SIZE) != null) {
                panel.repaint();
                System.out.println("picked up");
            }
        } else {
            // if mouse pressed on a piece and now mouse has released on a different tile,
            // move the piece to that tile if it is a valid move\
            if (panel.getCurrentPiece().canMove(y / Board.SQUARE_SIZE, x / Board.SQUARE_SIZE)) {
                movePiece();
                System.out.println("placed");
                panel.computerMove();
            } else {
                // drop the piece that was picked up
                panel.setCurrentPiece(null);
                panel.repaint();
                System.out.println("dropped");
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
