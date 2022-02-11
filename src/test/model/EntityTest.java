package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    private Entity entity;

    @BeforeEach
    void runBefore() {
        entity = new Entity(173, "The Sculpture", Classification.EUCLID, true);
    }

    @Test
    void testFormatNumLength() {
        assertEquals("096", entity.formatNumLength(96, 3));
        assertEquals("00096", entity.formatNumLength(96, 5));
        assertEquals("96", entity.formatNumLength(96, 2));
    }

    @Test
    void getEntry() {
        String expected1 = "SCP-173 - The Sculpture \n ----- \nSpecial Containment Procedures\ndon't blink"
                + "\nDescription\nconcrete and rebar peanut";
        assertEquals(expected1, entity.getEntry()); // not done yet
    }

    @Test
    void deleteEntry() {
        // stuff
    }

    @Test
    void addEntry() {
        // stuff
    }
}
