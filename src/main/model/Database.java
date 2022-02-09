package model;

import java.util.List;

public class Database {
    private List<Entity> listOfSCPs;
    private List<Entity> favourites;

    public Database() {

    }

    public void addSCP(Entity entity) {
        int itemNumber = entity.getItemNumber();
        for (int i = 1; i < itemNumber; i++) {
            if (!entity.getHasEntry()) {
                //figure out how to let someone add an entry in a possibly infinite list that must stay in order
            }
        }
    }
}
