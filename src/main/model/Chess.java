package model;

import java.util.List;

public class Chess {

    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Player currentPlayer;

    public Chess() {
        this.whitePlayer = new Player(true);
        this.blackPlayer = new Player(false);
        this.board = new Board();
        this.currentPlayer = whitePlayer;
    }

    public void newTurn(int x, int y, int newX, int newY) { //assuming that everyone moves during their own turn
        Tile currentTile = board.getTile(x, y);
        Tile toTile = board.getTile(newX, newY);
        Piece piece = currentTile.getPiece();

        if (piece.canMoveToTile(board, currentTile, toTile)) {
            toTile.setTilePiece(currentTile.getPiece());
        } else if (piece.canCaptureOnTile(board, currentTile, toTile)) {
            capture(board, currentTile, toTile);
        } else {
            System.out.println("You can't move there.");
        }

        nextPlayer(currentPlayer);
    }

    private void capture(Board board, Tile currentTile, Tile toTile) {
        currentPlayer.capture(toTile.getPiece());
        toTile.setTilePiece(currentTile.getPiece());
        currentTile.setTilePiece(null);
    }

    private void nextPlayer(Player currentPlayer) {
        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

}
