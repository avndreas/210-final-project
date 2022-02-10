package model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;
    private static final int MIN_DIGITS = 3;
    private static final Entity NULL_ENTITY = new Entity(0, "NULL", Classification.SAFE, true);

    private Entity testEntity = new Entity(173, "The Sculpture", Classification.EUCLID, true);

    public Database() {
        listOfSCPs = new ArrayList<>();
        listOfSCPs.add(NULL_ENTITY);
        addSCP(testEntity);
        testEntity.setEntry("dont blink", "rebar and concrete peanut");
    }

    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        listOfSCPs.add(itemNumber, entity);
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

    public void deleteSCP(int itemNumber) {
        listOfSCPs.remove(itemNumber);
        favourites.remove(favourites.indexOf(getSCP(itemNumber)));
    }

    // REQURES: integers start <= end
    // MODIFIES:
    // EFFECTS: Returns everything in listOfSCPs in order from one index to another.
    public String listFromTo(int start, int end) {
        Entity currentSCP;
        for (int i = start; i <= end; i++) {
            currentSCP = getSCP(i);
            return "\n SCP-"
                    + currentSCP.formatNumLength(currentSCP.getItemNumber(), MIN_DIGITS)
                    + " - "
                    + currentSCP.getName();
        }
        return "Something has gone horribly wrong.";
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
