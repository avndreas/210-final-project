package model;

import java.util.ArrayList;
import java.util.List;

// Represents a database containing Entity objects (also known as SCPs),
public class Database {
    private List<Entity> listOfSCPs;
    public static final int MIN_DIGITS = 3;
    private static final int ENTRIES_PER_SERIES = 1000;
    private static final String DEFAULT_NAME = "[ACCESS DENIED]";
    private static final Classification DEFAULT_CLASS = Classification.UNCLASSIFIED;
    private static final boolean DEFAULT_CONT = true;

    // REQUIRES: series > 0
    // MODIFIES: this
    // EFFECTS: Initializes a database with a list of SCPs.
    public Database(int series) {
        listOfSCPs = new ArrayList<>();
        initializeSeries(series);
    }

    // REQUIRES: int > 0
    // MODIFIES: this.listOfSCPs
    // EFFECTS: Creates a default SCP for every index of the database
    private void initializeSeries(int series) {
        for (int i = 0; i < (ENTRIES_PER_SERIES * series); i++) {
            Entity blankEntity = new Entity(i, DEFAULT_NAME, DEFAULT_CLASS, DEFAULT_CONT);
            listOfSCPs.add(blankEntity);

        }
    }

    // REQUIRES:
    // MODIFIES: listOfSCPs
    // EFFECTS: Sets an entry into the database index of the SCP's item number.
    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        listOfSCPs.set(itemNumber, entity);
    }

    // REQUIRES: (series * ENTRIES_PER_SERIES) > itemNumber > 0
    // MODIFIES: listOfSCPs
    // EFFECTS: Replaces desired SCP with a blank instance, effectively wiping it.
    public void deleteSCP(int itemNumber) {
        Entity blankEntity = new Entity(itemNumber, DEFAULT_NAME, DEFAULT_CLASS, DEFAULT_CONT);
        listOfSCPs.set(itemNumber, blankEntity);
        // favourites.remove(getSCP(itemNumber));
    }

    // REQUIRES: integers start <= end
    // MODIFIES:
    // EFFECTS: Returns everything in listOfSCPs from one index to another with some formatting for readability.
    public String listFromTo(int start, int end) {
        Entity currentSCP;
        String entityList = "Listing all SCPs from " + start + "to " + end + ".";

        for (int i = start; i <= end; i++) {
            currentSCP = getSCP(i);
            entityList = entityList + "\n SCP-"
                    + currentSCP.formatNumLength(currentSCP.getItemNumber(), MIN_DIGITS)
                    + " - "
                    + currentSCP.getName();
        }
        return entityList;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: Returns everything in listOfSCPs in order with some formatting for readability.
    public String listAll() {
        String entityList = "Listing all SCPs";

        for (Entity currentSCP: listOfSCPs) {
            entityList = entityList + "\n SCP-"
                    + currentSCP.formatNumLength(currentSCP.getItemNumber(), MIN_DIGITS)
                    + " - "
                    + currentSCP.getName();
        }
        return entityList;
    }

    // REQUIRES: objectNum >= 0
    // MODIFIES:
    // EFFECTS: Returns true if the object number is within the database's series, false if there's no existing entity.
    public boolean entityExists(int objectNum) {
        try {
            listOfSCPs.get(objectNum);
            return true;
        } catch (IndexOutOfBoundsException err) {
            return false;
        }
    }

    public Entity getSCP(int itemNumber) {

        return listOfSCPs.get(itemNumber);
    }


}
