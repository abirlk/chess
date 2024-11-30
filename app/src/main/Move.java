package app.src.main;

import app.src.main.pieces.Piece;

public class Move {
    public Piece piece;
    // where the piece can move to
    public int rank;
    public int file;

    public Move(Piece piece, int rank, int file) {
        this.piece = piece;
        this.rank = rank;
        this.file = file;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }
}
