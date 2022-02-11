package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Database
public class DatabaseTest {

    private final int SERIES = 1;
    Database database;
    Entity testEntity;

    @BeforeEach
    void runBefore() {
        database = new Database(SERIES);
        testEntity = new Entity(504, "Critical Tomatoes", Classification.SAFE, true);
    }

    @Test
    void initializeSeries() {
        // Ask the TA how this should be done, since it is automatically called when you initialize a database.
    }

    @Test
    void addSCP() {
        // stuff
    }

    @Test
    void deleteSCP() {
        // stuff
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
        // add more entries and test if there's time later
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
