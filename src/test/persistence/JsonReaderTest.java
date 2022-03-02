package persistence;

import model.Entity;
import model.Database;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// REFERENCE: CPSC 210 example files
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Database d = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDatabase.json");
        try {
            Database d = reader.read();
            assertEquals(1, d.getSeries());

            String defaultList = "Listing all SCPs";
            for (int i = 0; i < (Database.ENTRIES_PER_SERIES); i++) {
                defaultList = defaultList + "\n SCP-" + Entity.formatNumLength(i, Database.MIN_DIGITS)
                        + " - " + Database.DEFAULT_NAME;
            }

            assertEquals(defaultList, d.listAll());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
        try {
            Database d = reader.read();
            assertEquals(1, d.getSeries());

            String defaultList = "Listing all SCPs";
            for (int i = 0; i < (Database.ENTRIES_PER_SERIES); i++) {

                if (i == 2) {
                    defaultList = defaultList + "\n SCP-" + Entity.formatNumLength(i, Database.MIN_DIGITS)
                            + " - " + "Test Entity";
                } else {
                    defaultList = defaultList + "\n SCP-" + Entity.formatNumLength(i, Database.MIN_DIGITS)
                            + " - " + Database.DEFAULT_NAME;
                }
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}