package app.src.main;

import java.util.ArrayList;
import java.util.List;

import app.src.main.pieces.Piece;

public class boardBeforeMove {
    private List<Piece> pieces;
    private int whiteMaterial;
    private int blackMaterial;
    private Piece currentPiece;
    private int playerTurn;

    public boardBeforeMove(List<Piece> pieces, int whiteMaterial, int blackMaterial, Piece currentPiece,
            int playerTurn) {
        this.pieces = new ArrayList<>(pieces);
        this.whiteMaterial = whiteMaterial;
        this.blackMaterial = blackMaterial;
        this.currentPiece = currentPiece;
        this.playerTurn = playerTurn;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getWhiteMaterial() {
        return whiteMaterial;
    }

    public int getBlackMaterial() {
        return blackMaterial;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

}
