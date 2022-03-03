package persistence;

import model.Classification;
import model.Entity;
import model.TextBlock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// REFERENCE: CPSC 210 example files
// Currently unneeded testing class, may be useful in the future.
public class JsonTest {
    protected void checkEntity(int itemNumber, String name, Classification objectClass, boolean contained,
                               ArrayList<TextBlock> entityInfo, Entity entity) {
        assertEquals(itemNumber, entity.getItemNumber());
        assertEquals(name, entity.getName());
        assertEquals(objectClass, entity.getClassification());
        assertEquals(contained, entity.isContained());
        for (TextBlock t: entityInfo) {
            assertEquals(t.getTitle(), entity.getEntityInfo().get(entityInfo.indexOf(t)).getTitle());
            assertEquals(t.getBody(), entity.getEntityInfo().get(entityInfo.indexOf(t)).getBody());
        }
    }
}
