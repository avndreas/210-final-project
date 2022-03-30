package model;

import model.observer.Event;
import model.observer.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

// Represents a database containing Entity objects (also known as SCPs),
public class Database extends Observable implements Writable {
    private List<Entity> listOfSCPs;
    public static final int MIN_DIGITS = 3;
    public static final int ENTRIES_PER_SERIES = 1000;
    public static final String DEFAULT_NAME = "[ACCESS DENIED]";
    public static final Classification DEFAULT_CLASS = Classification.UNCLASSIFIED;
    public static final boolean DEFAULT_CONT = true;
    private int series;

    // REQUIRES: series > 0
    // MODIFIES: this
    // EFFECTS: Initializes a database with a list of SCPs.
    public Database(int series) {
        this.series = series;
        listOfSCPs = new ArrayList<>();
        initializeSeries(series);
    }

    public List<Entity> getListOfSCPs() {
        return listOfSCPs;
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
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Entry for SCP-"
                + Entity.formatNumLength(entity.getItemNumber(), MIN_DIGITS) +  " was added to the database."));
        int itemNumber = entity.getItemNumber();
        listOfSCPs.set(itemNumber, entity);
    }

    // REQUIRES: (series * ENTRIES_PER_SERIES) > itemNumber > 0
    // MODIFIES: listOfSCPs
    // EFFECTS: Replaces desired SCP with a blank instance, effectively wiping it.
    public void deleteSCP(int itemNumber) {
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Entry for SCP-"
                + Entity.formatNumLength(itemNumber, MIN_DIGITS) +  " was deleted from the database."));
        Entity blankEntity = new Entity(itemNumber, DEFAULT_NAME, DEFAULT_CLASS, DEFAULT_CONT);
        listOfSCPs.set(itemNumber, blankEntity);
    }

    /* IGNORE FOR PHASE 1 THIS IS NOT GOING TO BE USED YET
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
    */

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

    /* CURRENTLY NOT USED BECAUSE I DON'T NEED FOOLPROOFING YET
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
    */

    // REQUIRES: itemNumber be from 0 to (series * ENTRIES_PER_SERIES) - 1
    // EFFECTS: Returns the Entity belonging to a number
    public Entity getSCP(int itemNumber) {
        return listOfSCPs.get(itemNumber);
    }

    // REFERENCE: CPSC 210 example files
    // EFFECTS: Returns the current database as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("series", series);
        json.put("listOfSCPs", entitiesToJson());
        return json;
    }

    // REFERENCE: CPSC 210 example files
    // EFFECTS: returns entities in this database as a JSON array
    private JSONArray entitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entity e : listOfSCPs) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    public int getSeries() {
        return series;
    }

}
