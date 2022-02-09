package model;

public class Move {

    private Player player;
    private Tile start;
    private Tile end;
    private Piece pieceMoving;
    private Piece pieceCaptured;

    public Move(Player player, Tile start, Tile end, Piece pieceMoving) {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceMoving = pieceMoving;
        //this.pieceCaptured = Tile.getPiece(end); //why doe
    }
    //test
    public static void movePiece(Player player, Tile start, Tile end, Piece pieceMoving) {

    }

}
