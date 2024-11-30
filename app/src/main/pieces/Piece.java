package app.src.main.pieces;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.src.main.Board;
import app.src.main.Panel;

public abstract class Piece {
    public Image image;
    public int x, y, file, rank;
    public int colour;
    public List<Piece> captures = new ArrayList<>();
    int value;

    public Piece(int colour, int rank, int file, int value) {
        this.colour = colour;
        this.file = file;
        this.rank = rank;
        this.value = value;
    }

    public int getX() {
        // x coord will be the file times by the size of square
        return file * Board.SQUARE_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return rank * Board.SQUARE_SIZE;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getColour() {
        return colour;
    }

    public int getValue() {
        return value;
    }

    // Retrieves all the pices horizontally between the current piece and the
    // prospective file
    // e.g P T D has T in-between
    public static List<Piece> getHorizontalPiecesBetween(Piece piece, int destFile) {
        // moving left
        if (piece.getFile() > destFile) {
            return Panel.pieces.stream()
                    .filter(p -> p.getRank() == piece.getRank() && destFile < p.getFile()
                            && p.getFile() < piece.getFile())
                    .collect(Collectors.toList());
        }
        // moving right
        else {
            return Panel.pieces.stream()
                    .filter(p -> p.getRank() == piece.getRank() && piece.getFile() < p.getFile()
                            && p.getFile() < destFile)
                    .collect(Collectors.toList());
        }
    }

    public static List<Piece> getVerticalPiecesBetween(Piece piece, int destRank) {
        // moving down
        if (piece.getFile() > destRank) {
            return Panel.pieces.stream()
                    .filter(p -> p.getFile() == piece.getFile() && destRank < p.getRank()
                            && p.getRank() < piece.getRank())
                    .collect(Collectors.toList());
        }
        // moving up
        else {
            return Panel.pieces.stream()
                    .filter(p -> p.getFile() == piece.getFile() && piece.getRank() < p.getRank()
                            && p.getRank() < destRank)
                    .collect(Collectors.toList());
        }

    }

    public static List<Piece> getDiagonalPiecesBetween(Piece piece, int destRank, int destFile) {
        List<Piece> pieces = new ArrayList<>();
        int i = 1;
        int j = 1;
        // down left
        if (destRank < piece.getRank() && destFile < piece.getFile()) {
            while (piece.getRank() - i != destRank && piece.getFile() - j != destFile) {
                pieces.add(getPieceOnTile(piece.getRank() - i, piece.getFile() - j));
                i++;
                j++;
            }
        }
        // down right
        if (destRank < piece.getRank() && destFile > piece.getFile()) {
            while (piece.getRank() - i != destRank && piece.getFile() + j != destFile) {
                pieces.add(getPieceOnTile(piece.getRank() - i, piece.getFile() + j));
                i++;
                j++;
            }
        }
        // up left
        if (destRank > piece.getRank() && destFile < piece.getFile()) {
            while (piece.getRank() + i != destRank && piece.getFile() - j != destFile) {
                pieces.add(getPieceOnTile(piece.getRank() + i, piece.getFile() - j));
                i++;
                j++;
            }
        }
        // up right
        if (destRank > piece.getRank() && destFile > piece.getFile()) {
            while (piece.getRank() + i != destRank && piece.getFile() + j != destFile) {
                pieces.add(getPieceOnTile(piece.getRank() + i, piece.getFile() + j));
                i++;
                j++;
            }
        }

        return pieces.stream().filter(p -> p != null).collect(Collectors.toList());
    }

    public static Piece getPieceOnTile(int rank, int file) {
        for (Piece piece : Panel.pieces) {
            if ((piece.getRank() == rank) && (piece.getFile() == file)) {
                return piece;
            }
        }

        return null;
    }

    public boolean capturePiece(Piece capture) {
        if (capture.getColour() != getColour()) {
            captures.add(capture);
            Panel.removePiece(capture);

            if (Panel.playerTurn == Board.WHITE) {
                Panel.whiteMaterial = Panel.whiteMaterial + capture.getValue();
            } else {
                Panel.whiteMaterial = Panel.blackMaterial + capture.getValue();
            }

            return true;
        }

        return false;
    }

    public abstract void addImage(Graphics2D g2);

    public boolean canMove(int destRank, int destFile) {
        return true;
    }

    public List<Piece> getCaptures() {
        return captures;
    }

}
