package ui;

import model.Database;

import java.util.Scanner;

public class DatabaseUI {
    private Scanner input;
    private Database database;

    // EFFECTS: Runs the SCP Database application
    public DatabaseUI() {
        runDatabase();
    }

    // Reference: TellerApp from CPSC 210
    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runDatabase() {
        boolean keepRunning = true;
        String command = null;

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        database = new Database();

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }

        }

    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Displays a menu of options
    private void displayMenu() {
        // list commands
        System.out.println("\nWelcome to the SCP Foundation's Database.");
        System.out.println("To see a list of recorded SCPs, type \"list\"."
                + "\n To see a specific range of entries, type \"list [start] [end]\" "
                + "\n Example: list 1 10 will return a list of SCPs 1-10.");

        // specific SCP commands
        System.out.println("\nTo view the entry for a specific SCP, type \"view [number]\".");
        System.out.println("\nTo create a new SCP entry, type \"create\".");
        System.out.println("\nTo delete an SCP entry from the database, type \"delete [number]\".");

        // watchlist commands
        System.out.println("\nTo view your watchlist, type \"watchlist\".");
        System.out.println("\nTo add an SCP to your watchlist, type \"watchlist [number]\".");
        System.out.println("\nTo remove an SCP from your watchlist, type \"watchlist remove [number]\".");

        // misc
        System.out.println("\nTo quit the program, type \"quit\".");

    }

    private void processCommand(String command) {
        String[] processedCommand = command.split(" ", 0);
        String rootCommand = processedCommand[0];

        switch (rootCommand) {
            case "list":
                // list stuff
            case "view":
                System.out.println(database.getSCP(Integer.parseInt(processedCommand[1])).getEntry());
            case "create":
                // create stuff
            case "delete":
                // delete stuff
            case "watchlist":
                // watchlist stuff
            default:
                break;
        }
    }

}
