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
        chess = new Chess();
        boolean gameRunning = true;
        String command = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

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
        System.out.println("\nEnter a tile's location containing a piece, then the location you'd like to move it to.");
        System.out.println("\nOr, type \"quit\" to quit.");
        // System.out.println("\nTo display the board, type \"board\".");
        System.out.println("\nExample: To move your d2 pawn up by two, type \"4,2 4,4\" ");
    }

    private void processCommand(String command) { // NOT BUG-PROOF
        System.out.println(command);
        int x = Character.getNumericValue(command.charAt(0));
        int y = Character.getNumericValue(command.charAt(2));
        int newX = Character.getNumericValue(command.charAt(4));
        int newY = Character.getNumericValue(command.charAt(6));

        chess.newTurn(x, y, newX, newY);
    }

}
