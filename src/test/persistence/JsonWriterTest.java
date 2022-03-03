package persistence;

import model.Database;
import model.Entity;
import model.TextBlock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static model.Classification.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// REFERENCE: CPSC 210 example files
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Database d = new Database(1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Database d = new Database(1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDatabase.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDatabase.json");
            d = reader.read();
            assertEquals(1, d.getSeries());

            for (int i = 0; i < (d.getSeries() * Database.ENTRIES_PER_SERIES); i++) {
                Entity e = d.getSCP(i);
                String defaultInfo = i + " " + Database.DEFAULT_NAME + " " + Database.DEFAULT_CLASS
                        + " " + Database.DEFAULT_CONT;
                assertEquals(defaultInfo, e.getAllInfo());
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDatabase() {
        try {
            Database d = new Database(1);

            Entity e1 = new Entity(1, "test1", SAFE, true);
            Entity e2 = new Entity(2, "test2", APOLLYON, false);
            e2.addEntry("the", "game");
            d.addSCP(e1);
            d.addSCP(e2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDatabase.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDatabase.json");
            d = reader.read();
            assertEquals(1, d.getSeries());

            for (int i = 0; i < (d.getSeries() * Database.ENTRIES_PER_SERIES); i++) {
                Entity e = d.getSCP(i);

                if (i == 1) {
                    assertEquals(e1.getAllInfo(), d.getSCP(i).getAllInfo());
                } else if (i == 2) {
                    assertEquals(e2.getAllInfo(), d.getSCP(i).getAllInfo());
                } else {
                    String defaultInfo = i + " " + Database.DEFAULT_NAME + " " + Database.DEFAULT_CLASS
                            + " " + Database.DEFAULT_CONT;
                    assertEquals(defaultInfo, e.getAllInfo());
                }


            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}