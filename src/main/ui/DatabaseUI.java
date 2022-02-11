package ui;

import model.Classification;
import model.Database;
import model.Entity;

import java.util.Scanner;

// Database UI application
public class DatabaseUI {
    private Scanner input;
    private Database database;
    private static final int SERIES = 1;
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
        database = new Database(SERIES);

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
        System.out.println("To see a list of recorded SCPs, type \"list\".");

        /* TO BE ADDED AFTER PHASE 1
        System.out.println("To see a list of recorded SCPs, type \"list\"."
                + "\n To see a specific range of entries, type \"list [start] [end]\" "
                + "\n Example: list 1 10 will return a list of SCPs 1-10.");
        */

        // specific SCP commands
        System.out.println("\nTo view the entry for a specific SCP, type \"view [number]\".");
        System.out.println("\nTo create a new SCP entry, type \"create\".");
        System.out.println("\nTo edit an SCP entry, type \"edit [number]\". Note: You cannot change an SCP's number.");
        System.out.println("\nTo delete an SCP entry from the database, type \"delete [number]\".");

        // watchlist commands
        /* TO BE ADDED AFTER PHASE 1
        System.out.println("\nTo view your watchlist, type \"watchlist\".");
        System.out.println("\nTo add an SCP to your watchlist, type \"watchlist [number]\".");
        System.out.println("\nTo remove an SCP from your watchlist, type \"watchlist remove [number]\".");
        */

        // misc
        System.out.println("\nTo quit the program, type \"quit\".");

    }

    // REQUIRES: Nonempty String command, user followed the input instructions (for now)
    // MODIFIES:
    // EFFECTS: Processes user input to navigate and use the SCP Database
    private void processCommand(String command) {
        String[] processedCommand = command.split(" ", 0);
        String rootCommand = processedCommand[0];

        switch (rootCommand) {
            case "list":

                if (processedCommand.length == 1) {
                    listEntries();
                } else {
                    listEntries(Integer.parseInt(processedCommand[1]),
                            Integer.parseInt(processedCommand[2]));
                }
                break;
                // if processedCommand is 3 long && the second and third entries are numbers, then do list range
                // else if its just list then list em all
                // else do the dougie
                // note: this stuff will be done after Phase 1
            case "view":
                System.out.println(database.getSCP(Integer.parseInt(processedCommand[1])).getEntry());
                break;
            case "create":
                createSCP();
                break;
            case "delete":
                // must decide by now whether or not nonexistent entries will be the 0 entity or fully null
            //case "watchlist":
                // for later
                // break;
            default:
                break;
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Prints out all entries in the database.
    private void listEntries() {
        System.out.println(database.listAll());
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Prints out all entries in the database within a given range.
    private void listEntries(int start, int end) {
        System.out.println("This function has not been added yet.");
    }

    // REQUIRES:
    // MODIFIES: input
    // EFFECTS: Creates a new SCP object by asking the user for each parameter, then adds it to the database.
    private void createSCP() {
        input.nextLine();
        int objectNumber;
        String name;
        Classification classification;
        boolean contained;
        System.out.println("Enter the number of the new SCP: ");
        objectNumber = Integer.parseInt(input.nextLine()); // make this foolproof later
        System.out.println("Enter the name of the new SCP: ");
        name = input.nextLine();
        System.out.println("Enter the object classification for this SCP (SAFE, EUCLID, KETER, THAUMIEL, APOLLYON): ");
        classification = Classification.valueOf(input.nextLine());
        System.out.println("Is this entity currently contained by the foundation? Type \"yes\" or \"no\"");
        String response = input.nextLine();
        if (response == "yes") {
            contained = true;
        } else {
            contained = false;
        }
        System.out.println("SCP-" + objectNumber + " - " + name + " \nhas been added to the database.");
        Entity newEntry = new Entity(objectNumber, name, classification, contained);
        database.addSCP(newEntry);
    }
}
