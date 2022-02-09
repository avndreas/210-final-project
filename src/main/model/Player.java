package model;

import java.util.List;

public class Player {

    private List<Piece> capturedPieces;
    private boolean isWhite;

    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void capture(Piece piece) {
        capturedPieces.add(piece);
    }
}
