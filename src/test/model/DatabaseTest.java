package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Database
public class DatabaseTest {

    private final int SERIES = 1;
    Database database;
    Entity testEntity;
    Entity blankEntity;

    @BeforeEach
    void runBefore() {
        database = new Database(SERIES);
        testEntity = new Entity(504, "Critical Tomatoes", Classification.SAFE, true);
    }

    @Test
    void addSCP() {
        assertNotEquals(testEntity, database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()));
        database.addSCP(testEntity);
        assertEquals(testEntity, database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()));
    }

    @Test
    void deleteSCP() {
        database.addSCP(testEntity);
        assertEquals(testEntity, database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()));
        database.deleteSCP(testEntity.getItemNumber());
        assertEquals(Database.DEFAULT_NAME,
                database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()).getName());
        assertEquals(Database.DEFAULT_CLASS,
                database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()).getClassification());
        assertEquals(Database.DEFAULT_CONT,
                database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()).isContained());
        assertEquals(0 ,
                database.getSCP(database.getSCP(testEntity.getItemNumber()).getItemNumber()).getRawInfo().size());
    }

    @Test
    void listAll() {
        // Testing on fresh list
        String defaultList = "Listing all SCPs";
        for (int i = 0; i < (SERIES * Database.ENTRIES_PER_SERIES); i++) {
            defaultList = defaultList + "\n SCP-" + Entity.formatNumLength(i, Database.MIN_DIGITS)
                    + " - " + Database.DEFAULT_NAME;
        }
        assertEquals(defaultList, database.listAll());

        // Testing when an SCP has been added to the list
        database.addSCP(testEntity);
        defaultList = "Listing all SCPs";
        for (int i = 0; i < (SERIES * Database.ENTRIES_PER_SERIES); i++) {
            defaultList = defaultList + "\n SCP-" + Entity.formatNumLength(i, Database.MIN_DIGITS)
                    + " - " + database.getSCP(i).getName();
        }
        assertEquals(defaultList, database.listAll());
    }

    /* May be used at a later date
    @Test
    void entityExists() {
        assertFalse(database.entityExists(1));
        database.addSCP(testEntity);
        assertFalse(database.entityExists(1));
        assertTrue(database.entityExists(504));
    }
    */
}
