package model;

import java.util.List;

public interface Piece {

    public void getCaptured();

    public boolean isWhite();

    public boolean isCaptured();

    public boolean canMoveToTile(Board board, Tile currentTile, Tile toTile);

    public boolean canCaptureOnTile(Board board, Tile currentTile, Tile toTile);

}
