package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        // test on a blank one still !!!
    }

    @Test
    void deleteEntry() {

        // trying to delete entries when there are none
        entity.deleteEntry(0);
        assertEquals(blankEntity.getRawInfo(), entity.getRawInfo());
        entity.deleteEntry(entity.getRawInfo().size() + 1);
        assertEquals(blankEntity.getRawInfo(), entity.getRawInfo());

        entity.addEntry("Description", "A scary sculpture.");

        // deleting nonexistent entry while a real entry exists.
        entity.deleteEntry(entity.getRawInfo().size() + 1);
        List<TextBlock> textList = new ArrayList<TextBlock>();
        textList.add(new TextBlock("Description", "A scary sculpture."));
        assertEquals(textList.get(0).getTitle(), entity.getRawInfo().get(0).getTitle());
        assertEquals(textList.get(0).getBody(), entity.getRawInfo().get(0).getBody());

        // deleting that existing entry
        entity.deleteEntry(0);
        assertEquals(blankEntity.getRawInfo(), entity.getRawInfo());
    }

    @Test
    void addEntry() {
        TextBlock testEntry1 = new TextBlock("Addendum", "do not hug either");
        entity.addEntry("Addendum", "do not hug either");
        assertEquals(testEntry1.getTitle(), entity.getRawInfo().get(0).getTitle());
        assertEquals(testEntry1.getBody(), entity.getRawInfo().get(0).getBody());

        entity.addEntry("Interview with SCP-173:", "Dr. [REDACTED]: Can you hear me? \n"
                + "Dr. [REDACTED] blinks, and SCP-173 immediately breaks his neck. \n"
                + "End of interview");

        TextBlock testEntry2 = new TextBlock("Interview with SCP-173:",
                "Dr. [REDACTED]: Can you hear me? \n"
                        + "Dr. [REDACTED] blinks, and SCP-173 immediately breaks his neck. \n"
                        + "End of interview");
        assertEquals(testEntry2.getTitle(), entity.getRawInfo().get(1).getTitle());
        assertEquals(testEntry2.getBody(), entity.getRawInfo().get(1).getBody());
    }
}
