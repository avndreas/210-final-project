package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Entity
class EntityTest {
    private Entity entity;
    private Entity blankEntity;

    @BeforeEach
    void runBefore() {
        entity = new Entity(173, "The Sculpture", Classification.EUCLID, true);
        blankEntity = new Entity(0, Database.DEFAULT_NAME, Database.DEFAULT_CLASS, Database.DEFAULT_CONT);
    }

    @Test
    void testFormatNumLength() {
        assertEquals("096", entity.formatNumLength(96, 3));
        assertEquals("00096", entity.formatNumLength(96, 5));
        assertEquals("96", entity.formatNumLength(96, 2));
    }

    @Test
    void getEntry() {
        String expected1 = "SCP-173 - The Sculpture\n ----- \nSpecial Containment Procedures\ndon't blink"
                + "\nDescription\nconcrete and rebar peanut\n";
        entity.addEntry("Special Containment Procedures", "don't blink");
        entity.addEntry("Description", "concrete and rebar peanut");
        assertEquals(expected1, entity.getEntry());
    }

    @Test
    void deleteEntry() {

        // trying to delete entries when there are none
        entity.deleteEntry(0);
        assertEquals(blankEntity.getEntityInfo(), entity.getEntityInfo());
        entity.deleteEntry(entity.getEntityInfo().size() + 1);
        assertEquals(blankEntity.getEntityInfo(), entity.getEntityInfo());

        entity.addEntry("Description", "A scary sculpture.");

        // deleting nonexistent entry while a real entry exists.
        entity.deleteEntry(entity.getEntityInfo().size() + 1);
        List<TextBlock> textList = new ArrayList<>();
        textList.add(new TextBlock("Description", "A scary sculpture."));
        assertEquals(textList.get(0).getTitle(), entity.getEntityInfo().get(0).getTitle());
        assertEquals(textList.get(0).getBody(), entity.getEntityInfo().get(0).getBody());

        // deleting that existing entry
        entity.deleteEntry(0);
        assertEquals(blankEntity.getEntityInfo(), entity.getEntityInfo());
    }

    @Test
    void addEntry() {
        TextBlock testEntry1 = new TextBlock("Addendum", "do not hug either");
        entity.addEntry("Addendum", "do not hug either");
        assertEquals(testEntry1.getTitle(), entity.getEntityInfo().get(0).getTitle());
        assertEquals(testEntry1.getBody(), entity.getEntityInfo().get(0).getBody());

        entity.addEntry("Interview with SCP-173:", "Dr. [REDACTED]: Can you hear me? \n"
                + "Dr. [REDACTED] blinks, and SCP-173 immediately breaks his neck. \n"
                + "End of interview");

        TextBlock testEntry2 = new TextBlock("Interview with SCP-173:",
                "Dr. [REDACTED]: Can you hear me? \n"
                        + "Dr. [REDACTED] blinks, and SCP-173 immediately breaks his neck. \n"
                        + "End of interview");
        assertEquals(testEntry2.getTitle(), entity.getEntityInfo().get(1).getTitle());
        assertEquals(testEntry2.getBody(), entity.getEntityInfo().get(1).getBody());
    }

    @Test
    void setName() {
        assertNotEquals("Amogus", entity.getName());
        entity.setName("Amogus");
        assertEquals("Amogus", entity.getName());
    }

    @Test
    void setObjectClass() {
        assertNotEquals(Classification.SAFE, entity.getClassification());
        entity.setObjectClass(Classification.SAFE);
        assertEquals(Classification.SAFE, entity.getClassification());
    }

    @Test
    void setContained() {
        assertTrue(entity.isContained());
        entity.setContained(false);
        assertFalse(entity.isContained());
    }

    @Test
    void testGetAllInfo() {

        entity.addEntry("test title", "test body");
        entity.addEntry("second test title", "second test body");

        assertEquals("173 The Sculpture EUCLID true\ntest title test body\n"
                + "second test title second test body\n", entity.getAllInfo());
    }

    @Test
    void testGetLabel() {
        assertEquals("SCP-173 - The Sculpture", entity.getLabel());
    }
}
