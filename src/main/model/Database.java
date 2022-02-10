package model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;
    private static final int MIN_DIGITS = 3;
    private static final Entity NULL_ENTITY = new Entity(0, "NULL", Classification.SAFE, true);

    public Database() {
        listOfSCPs = new ArrayList<>();
        listOfSCPs.add(NULL_ENTITY);
    }

    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        listOfSCPs.add(itemNumber, entity);
    }

    public Entity getSCP(int itemNumber) {
        Entity tempEntity;
        try {
            tempEntity = listOfSCPs.get(itemNumber);
        } catch (IndexOutOfBoundsException err) {
            tempEntity = NULL_ENTITY;
        }
        return tempEntity;
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
}
