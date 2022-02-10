package model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;
    private static final int MIN_DIGITS = 3;
    private static final Entity NULL_ENTITY = new Entity(0, "NULL", Classification.SAFE, true);
    private static final int ENTRIES_PER_SERIES = 1000;
    private static final String DEFAULT_NAME = "[ACCESS DENIED]";
    private static final Classification DEFAULT_CLASS = Classification.UNCLASSIFIED;
    private static final boolean DEFAULT_CONT = true;

    private Entity testEntity = new Entity(173, "The Sculpture", Classification.EUCLID, true);

    public Database(int series) {
        listOfSCPs = new ArrayList<>();
        initializeSeries(series);
        listOfSCPs.set(0, NULL_ENTITY);

        addSCP(testEntity);
        testEntity.setEntry("dont blink", "rebar and concrete peanut");
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

    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        listOfSCPs.set(itemNumber, entity);
    }

    public Entity getSCP(int itemNumber) {
        // Entity tempEntity;

        if (entryExists(itemNumber)) {
            return listOfSCPs.get(itemNumber);
        } else {
            return NULL_ENTITY;
        }

        /*
        try {
            tempEntity = listOfSCPs.get(itemNumber);
        } catch (IndexOutOfBoundsException err) {
            tempEntity = NULL_ENTITY;
        }
        */
    }

    // REQUIRES: (series * ENTRIES_PER_SERIES) > itemNumber > 0
    // MODIFIES: listOfSCPs
    // EFFECTS: Replaces desired SCP with a blank instance, effectively wiping it.
    public void deleteSCP(int itemNumber) {
        Entity blankEntity = new Entity(itemNumber, DEFAULT_NAME, DEFAULT_CLASS, DEFAULT_CONT);
        listOfSCPs.set(itemNumber, blankEntity);
        favourites.remove(favourites.indexOf(getSCP(itemNumber)));
    }

    // REQUIRES: integers start <= end
    // MODIFIES:
    // EFFECTS: Returns everything in listOfSCPs in order from one index to another.
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


    public boolean entryExists(int objectNum) {
        try {
            listOfSCPs.get(objectNum);
            return true;
        } catch (IndexOutOfBoundsException err) {
            return false;
        }
    }
}
