package ui;

import model.Classification;
import model.Database;
import model.Entity;
import persistence.JsonReader;

import java.io.IOException;
import java.util.Scanner;

// Database console UI application
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

        // specific SCP commands
        System.out.println("\nTo view the entry for a specific SCP, type \"view [number]\".");
        System.out.println("\nTo create a new SCP entry, type \"create\".");
        System.out.println("\nTo edit an SCP entry, type \"edit [number]\". Note: You cannot change an SCP's number.");
        System.out.println("\nTo delete an SCP entry from the database, type \"delete [number]\".");
        System.out.println("\nTo load from a file, type \"load\".");

        // misc
        System.out.println("\nTo quit the program, type \"quit\".\n");

        /* TO BE ADDED AFTER PHASE 1
        System.out.println("To see a list of recorded SCPs, type \"list\"."
                + "\n To see a specific range of entries, type \"list [start] [end]\" "
                + "\n Example: list 1 10 will return a list of SCPs 1-10.");

        // watchlist commands
        System.out.println("\nTo view your watchlist, type \"watchlist\".");
        System.out.println("\nTo add an SCP to your watchlist, type \"watchlist [number]\".");
        System.out.println("\nTo remove an SCP from your watchlist, type \"watchlist remove [number]\".");
        */
    }

    // REQUIRES: Nonempty String command, user followed the input instructions (for now)
    // MODIFIES:
    // EFFECTS: Processes user input to navigate and use the SCP Database
    @SuppressWarnings("methodlength")
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
            case "view":
                System.out.println(database.getSCP(Integer.parseInt(processedCommand[1])).getEntry());
                break;
            case "create":
                createSCP();
                break;
            case "edit":
                editSCP(Integer.parseInt(processedCommand[1]));
                break;
            case "delete":
                deleteSCP(Integer.parseInt(processedCommand[1]));
                break;
            case "load":
                readJson();
                break;
            default:
                break;
        }
    }

    private void readJson() {
        JsonReader saveFile = new JsonReader("data/testOnlyThreeSlots.json"); // change this later !!!
        try {
            database = saveFile.read();
        } catch (IOException e) {
            System.out.println("Error reading the file. Current save has not been altered.");
        }

    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Prints out all entries in the database.
    private void listEntries() {
        System.out.println(database.listAll());
    }

    // REQUIRES: start <= end
    // MODIFIES:
    // EFFECTS: Prints out all entries in the database within a given range.
    private void listEntries(int start, int end) {
        System.out.println("This function has not been added yet.");
    }

    // REQUIRES:
    // MODIFIES: input
    // EFFECTS: Creates a new SCP object by asking the user for each parameter, then adds it to the database.
    @SuppressWarnings("methodlength")
    private void createSCP() {
        System.out.println("-----");
        input.nextLine();
        int objectNumber;
        String name;
        Classification classification;
        boolean contained;
        System.out.println("Enter the number of the new SCP: ");
        objectNumber = Integer.parseInt(input.nextLine());
        System.out.println("Enter the name of the new SCP: ");
        name = input.nextLine();
        System.out.println("Enter the object classification for this SCP (ex. SAFE, EUCLID, KETER): ");
        classification = Classification.valueOf(input.nextLine());
        System.out.println("Is this entity currently contained by the foundation? Type \"yes\" or \"no\"");
        String response = input.nextLine();
        contained = response.equals("yes");
        System.out.println("SCP-" + objectNumber + " - " + name + " \nhas been added to the database.");
        Entity newEntry = new Entity(objectNumber, name, classification, contained);
        database.addSCP(newEntry);
        System.out.println("-----");
        System.out.println("Would you like to edit anything, or add text for this SCP? "
                + "Type \"yes\" or \"no\".");
        response = input.nextLine();
        if (response.equals("yes")) {
            editSCP(objectNumber);
        }
    }

    // REQUIRES: objectNumber >= 0
    // MODIFIES: input
    // EFFECTS: Guides the user through choosing which parts of the SCP to edit.
    private void editSCP(int objectNumber) {
        System.out.println("-----");
        input.nextLine();
        System.out.println("Which information would you like to change? For name, classification, or "
                + "containment status, type \"stats\". For containment procedures, description, or other text "
                + "fields, type \"text\".");
        String response = input.nextLine();
        if (response.equals("stats")) {
            editStats(objectNumber);
        } else {
            editText(objectNumber);
        }
    }

    // REQUIRES: objectNumber >= 0
    // MODIFIES: input, the entity belonging to objectNumber
    // EFFECTS: Guides the user through editing name, object class, or containment status of an Entity.
    private void editStats(int objectNumber) {
        Entity entity = database.getSCP(objectNumber);
        String command;
        System.out.println("Would you like to edit the name, object class, or containment status? \n"
                + "Ex. type \"containment status\".");
        command = input.nextLine();
        switch (command) {
            case "name":
                System.out.println("Enter " + objectNumber + "'s new name: ");
                entity.setName(input.nextLine());
                break;
            case "object class":
                System.out.println("Enter " + objectNumber + "'s new object class: ");
                entity.setObjectClass(Classification.valueOf(input.nextLine()));
                break;
            case "containment status":
                System.out.println("Enter " + objectNumber + "'s new containment status, true or false: ");
                entity.setContained(input.nextLine().equals("true"));
                break;
        }

    }

    // REQUIRES: objectNumber >= 0
    // MODIFIES: input, objectNumber's Entity's entityInfo
    // EFFECTS: Guides the user through adding or deleting a TextBlock object from the objectNumber's Entity.
    private void editText(int objectNumber) {
        Entity entity = database.getSCP(objectNumber);
        String command;
        System.out.println("Would you like to add a text block, or delete an existing one? \n"
                + "Type \"add\" or \"delete\".");
        command = input.nextLine();
        if (command.equals("add")) {
            String title;
            String body;
            System.out.println("What is the title of this new text block?");
            title = input.nextLine();
            System.out.println("What is the body text of this new text block?");
            body = input.nextLine();
            entity.addEntry(title, body);
            System.out.println("Your new text block has been added.");
        } else {
            System.out.println(entity.getEntry());
            System.out.println("What is the index of the block you'd like to delete?");
            entity.deleteEntry(Integer.parseInt(input.nextLine()));


        }
    }

    // REQUIRES: entityNumber >= 0
    // MODIFIES: database
    // EFFECTS: Deletes an SCP from the database, replacing it with a default Entity.
    private void deleteSCP(int entityNumber) {
        database.deleteSCP(entityNumber);
        System.out.println("SCP deleted.");
    }

}
