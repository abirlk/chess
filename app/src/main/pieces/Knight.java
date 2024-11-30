package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import app.src.main.Board;

public class Knight extends Piece {
    public Knight(int colour, int rank, int file) {
        super(colour, rank, file, 3);
    }

    public void addImage(Graphics2D g2) {
        Image knight = null;
        if (getColour() == 1) {
            knight = new ImageIcon("res/pieces/w_knight.png").getImage();
        } else {
            knight = new ImageIcon("res/pieces/b_knight.png").getImage();
        }
        g2.drawImage(knight, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    @Override
    /*
     * Knight moves 2 ranks and 1 file OR 2 files and 1 rank
     */
    public boolean canMove(int destRank, int destFile) {
        if (Math.abs(destRank - getRank()) * Math.abs(destFile - getFile()) == 2) {
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
