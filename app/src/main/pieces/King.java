package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import app.src.main.Board;

public class King extends Piece {

    public King(int colour, int rank, int file) {
        super(colour, rank, file, 4);
    }

    public void addImage(Graphics2D g2) {
        Image king = null;
        if (getColour() == Board.WHITE) {
            king = new ImageIcon("res/pieces/w_king.png").getImage();
        } else {
            king = new ImageIcon("res/pieces/b_king.png").getImage();
        }

        g2.drawImage(king, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    /*
     * King can move in any direction by 1 square.
     * Cannot move on a tile which is already occupied by the player.
     */
    @Override
    public boolean canMove(int destRank, int destFile) {
        if ((Math.abs(getFile() - destFile) * Math.abs(getRank() - destRank) == 1)
                || (Math.abs(getFile() - destFile) + Math.abs(getRank() - destRank) == 1)) {

            Piece capture = Piece.getPieceOnTile(destRank, destFile);
            if (capture == null) {
                return true;
            } else {
                return capturePiece(capture);
            }
        }

        return false;
    }
}
