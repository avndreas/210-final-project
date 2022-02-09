package model;

import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;

    public Database() {

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
}
