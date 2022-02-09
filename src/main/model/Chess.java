package model;

import java.util.List;

public class Chess {

    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Player currentTurn;
    private List<Move> moves;

    public Chess() {
        this.board = new Board();
        this.currentTurn = whitePlayer;
    }

    public void newTurn(int x, int y, int newX, int newY) { //assuming that everyone moves during their own turn
        Tile currentTile = board.getTile(x, y);
        Tile toTile = board.getTile(newX, newY);
        Piece piece = currentTile.getPiece();

        if (piece.canMoveToTile(board, currentTile, toTile)) {
            board.setTile()
        } else if (piece.canCaptureOnTile(board, currentTile, toTile)) {

        }
    }

}
