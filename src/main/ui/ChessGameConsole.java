package ui;

import model.Board;
import model.Chess;

import java.util.Scanner;

// Console player for the chess game
// Reference: TellerApp from CPSC 210
public class ChessGameConsole {

    private Scanner input;
    private Chess chess;

    public ChessGameConsole() {
        startGame();
    }

    private void startGame() {
        boolean gameRunning = true;
        String command = null;

        while (gameRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                gameRunning = false;
            //} else if (command.equals("board")) {
               // Board.displayBoard();
            } else {
                processCommand(command);
            }

        }

    }

    private void displayMenu() {
        System.out.println("\nEnter a tile's location containing a piece. To quit, type \"quit\".");
        // System.out.println("\nTo display the board, type \"board\".");
        System.out.println("\nExample: To select your D2 pawn, type \"4,2\" ");
    }

    private void processCommand(String command) { // NOT BUG-PROOF
        int x = Integer.parseInt(command.substring(0,0));
        int y = Integer.parseInt(command.substring(2,2));
        System.out.println(x);
        System.out.println(y);
        chess.newTurn(x, y, 4, 4); //ADD REAL VALUES HERE LATER
    }

}
