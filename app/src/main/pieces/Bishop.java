package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import app.src.main.Board;

public class Bishop extends Piece {

    public Bishop(int colour, int rank, int file) {
        super(colour, rank, file, 3);
    }

    public void addImage(Graphics2D g2) {
        Image bishop = null;
        if (getColour() == 1) {
            bishop = new ImageIcon("res/pieces/w_bishop.png").getImage();
        } else {
            bishop = new ImageIcon("res/pieces/b_bishop.png").getImage();
        }
        g2.drawImage(bishop, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    @Override
    /*
     * Bishop moves diagonally as long as it does not jump over any pieces
     */
    public boolean canMove(int destRank, int destFile) {
        if (!(Math.abs(destRank - getRank()) == Math.abs(destFile - getFile()))
                || (destRank == getRank() && destFile == getFile())) {
            return false;
        }

        if (!Piece.getDiagonalPiecesBetween(this, destRank, destFile).isEmpty()) {
            return false;
        }

        Piece capture = Piece.getPieceOnTile(destRank, destFile);

        if (capture == null) {
            return true;
        }

        return capturePiece(capture);
    }
}
