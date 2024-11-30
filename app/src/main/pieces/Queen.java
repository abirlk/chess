package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import app.src.main.Board;

public class Queen extends Piece {

    public Queen(int colour, int rank, int file) {
        super(colour, rank, file, 8);
    }

    public void addImage(Graphics2D g2) {
        Image queen = null;
        if (getColour() == 1) {
            queen = new ImageIcon("res/pieces/w_queen.png").getImage();
        } else {
            queen = new ImageIcon("res/pieces/b_queen.png").getImage();
        }

        g2.drawImage(queen, getX(), getY(), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    @Override
    /*
     * Queen can move like the bishop and rook combined
     */
    public boolean canMove(int destRank, int destFile) {
        // method forwarding - create imaginary pieces with the same properties as the
        // current queen
        Bishop bishop = new Bishop(getColour(), getRank(), getFile());
        Rook rook = new Rook(getColour(), getRank(), getFile());

        return bishop.canMove(destRank, destFile) || rook.canMove(destRank, destFile);

    }

}
