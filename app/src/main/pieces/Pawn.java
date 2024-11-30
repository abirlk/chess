package app.src.main.pieces;

import app.src.main.Board;
import app.src.main.Panel;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pawn extends Piece {
    private boolean moved = false;

    public Pawn(int colour, int rank, int file) {
        super(colour, rank, file, 1);
    }

    public void addImage(Graphics2D g2) {
        Image pawn = null;
        if (getColour() == 1) {
            pawn = new ImageIcon("res/pieces/w_pawn.png").getImage();
        } else {
            pawn = new ImageIcon("res/pieces/b_pawn.png").getImage();
        }
        g2.drawImage(pawn, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    private boolean checkTwoSquares(int destRank, int destFile) {
        if (Panel.playerTurn == Board.WHITE) {
            if (destRank == getRank() - 2 && destFile == getFile() && getPieceOnTile(destRank, destFile) == null) {
                return true;
            }

            return false;
        } else {
            if (destRank == getRank() + 2 && destFile == getFile() && getPieceOnTile(destRank, destFile) == null) {
                return true;
            }

            return false;
        }
    }

    @Override
    /*
     * Pawn moves one tile front if the tile is vacant or diagonally if it can
     * capture.
     * On its first move, can move upto 2 tiles front.
     */
    public boolean canMove(int destRank, int destFile) {
        if (!moved && checkTwoSquares(destRank, destFile)) {
            moved = true;
            return true;
        }

        int direction = (Panel.playerTurn == Board.WHITE) ? 1 : -1;

        if (getRank() - direction == destRank && destFile == getFile()
                && Piece.getPieceOnTile(destRank, destFile) == null) {
            moved = true;
            return true;
        }
        // check left and right diagonals
        if ((getRank() - direction == destRank && getFile() + 1 == destFile)
                || (getRank() - direction == destRank && getFile() - 1 == destFile)) {
            Piece capture = Piece.getPieceOnTile(destRank, destFile);
            // must capture on diagonal
            if (capture == null) {
                return false;
            }
            if (capturePiece(capture)) {
                moved = true;
                return true;
            }
            return false;
        }

        return false;
    }

}
