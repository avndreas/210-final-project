package model;

public class Board {
    private Tile[][] chessBoard;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

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
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //will add if statement later to check if there's already a piece placed
                this.chessBoard[x][y] = new Tile(x, y, null);
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Returns the Tile object given x and y positions, or null if the desired tile is not within the board.
    public Tile getSquare(int x, int y) {
        if (x >= WIDTH || y >= HEIGHT) {
            return null;
        } else {
            return chessBoard[x][y];
        }
    }

    // REQUIRES: x and y coordinates be lower than WIDTH and HEIGHT respectively
    // MODIFIES: this
    // EFFECTS: Puts a piece on the given tile, replacing a previous piece if there is one.
    public void setSquare(int x, int y, Piece piece) {
        this.chessBoard[x][y] = new Tile(x, y, piece); // do i make a new tile here ???
    }

}
