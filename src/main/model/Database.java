package model;

import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;
    private static final int MIN_DIGITS = 3;

    public Database() {
        // nothing happens
    }

    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        listOfSCPs.add(itemNumber, entity);
    }

    public Entity getSCP(int itemNumber) {
        return listOfSCPs.get(itemNumber);
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
