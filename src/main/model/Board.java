package model;

import model.pieces.Pawn;

public class Board {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private Tile[][] chessBoard = new Tile[HEIGHT][WIDTH];

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: Calls on setBoard to make a starting setup.
    public Board() {
        this.setBoard();
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: Initializes a chessboard for the start of a game, currently sets every tile to be empty.
    public void setBoard() {
        chessBoard[4][2] = new Tile(2, 4, new Pawn(true));
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (chessBoard[y][x] == null) {
                    chessBoard[y][x] = new Tile(x, y, null);
                }
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Returns the Tile object given x and y positions, or null if the desired tile is not within the board.
    public Tile getTile(int x, int y) {
        if (x >= WIDTH || y >= HEIGHT) {
            return null;
        } else {
            return chessBoard[y][x];
        }
    }

    /*
    // REQUIRES: x and y coordinates be lower than WIDTH and HEIGHT respectively
    // MODIFIES: this
    // EFFECTS: Puts a piece on the given tile, replacing a previous piece if there is one.
    public void setTile(Piece piece) {
        this.chessBoard[x][y].setTilePiece(piece);
    }
    */
}
