package model;

public class Tile {
    private Piece piece;
    private int column;
    private int row;

    public Tile(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Tile getTile() {
        return this;
    }

    public void setTile(Piece piece) {
        this.piece = piece;
    }

}
