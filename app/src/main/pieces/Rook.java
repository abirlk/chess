package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import app.src.main.Board;

public class Rook extends Piece {
    public Rook(int colour, int rank, int file) {
        super(colour, rank, file, 5);
    }

    public void addImage(Graphics2D g2) {
        Image rook = null;
        if (getColour() == 1) {
            rook = new ImageIcon("res/pieces/w_rook.png").getImage();
        } else {
            rook = new ImageIcon("res/pieces/b_rook.png").getImage();
        }

        g2.drawImage(rook, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    @Override
    /*
     * Rook can move on the same rank or file as long as it does not jump over any
     * pieces
     */
    public boolean canMove(int destRank, int destFile) {
        if ((destRank == getRank() && destFile == getFile()) || (destRank != getRank() && destFile != getFile())) {
            return false;
            // moving horizontally
        } else if (destRank == getRank()) {
            if (!getHorizontalPiecesBetween(this, destFile).isEmpty()) {
                return false;
            }
        } else {
            if (!getVerticalPiecesBetween(this, destRank).isEmpty()) {
                return false;
            }
        }
        Piece capture = Piece.getPieceOnTile(destRank, destFile);

        if (capture == null) {
            return true;
        }

        return capturePiece(capture);
    }

}
