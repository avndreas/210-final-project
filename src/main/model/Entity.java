package model;

import java.util.ArrayList;
import java.util.List;

// An Entity object, AKA an SCP, which has a number, name, class, containment status, and a list of text blocks.
public class Entity {

    private final int itemNumber;
    private String name;
    private Classification objectClass;
    private boolean contained;
    private List<TextBlock> entityInfo;

    // REQUIRES: itemNumber > 0
    // MODIFIES:
    // EFFECTS: Instantiates a new SCP entity.
    public Entity(int itemNumber, String name, Classification objectClass, boolean contained) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.objectClass = objectClass;
        this.contained = contained;
        entityInfo = new ArrayList<TextBlock>();
    }

    // REQUIRES: integer > 0
    // MODIFIES: itemNumber into a String
    // EFFECTS: If itemNumber < desiredLength, adds zeroes to front of number in order to make it so. Returns as String.
    public String formatNumLength(int itemNumber, int desiredLength) {
        String formattedNum = "";
        if (Integer.toString(itemNumber).length() >= desiredLength) {
            return Integer.toString(itemNumber);
        } else {
            for (int i = desiredLength - Integer.toString(itemNumber).length(); i > 0; i--) {
                formattedNum = "0" + formattedNum;
            }
            return formattedNum + Integer.toString(itemNumber);
        }
    }

    // REQUIRES: entityInfo not empty
    // MODIFIES:
    // EFFECTS: Returns a string with the SCP's entire entry texts.
    public String getEntry() {
        String entry = "SCP-" + formatNumLength(this.getItemNumber(), Database.MIN_DIGITS)
                + " - " + name + "\n ----- \n";

        for (TextBlock entryText: entityInfo) {
            entry = entry + entryText.getTitle() + "\n" + entryText.getBody() + "\n";
        }


        return entry;
    }

    // REQUIRES: index > 0
    // MODIFIES: entityInfo
    // EFFECTS: Tries to delete an item from the entityInfo list, returns an error message if no object at that index.
    public void deleteEntry(int index) {
        try {
            entityInfo.remove(index);
        } catch (IndexOutOfBoundsException error) {
            System.out.println("IndexOutOfBoundsException error");
        }

    }

    // REQUIRES:
    // MODIFIES: entityInfo
    // EFFECTS: Adds a new TextBlock object (with a title and a body) to the entityInfo list.
    public void addEntry(String title, String body) {
        TextBlock newText = new TextBlock(title, body);
        entityInfo.add(newText);
    }


    // getters and setters

    public void setName(String name) {
        this.name = name;
    }

    public void setObjectClass(Classification objectClass) {
        this.objectClass = objectClass;
    }

    public void setContained(boolean contained) {
        this.contained = contained;
    }

    public int getItemNumber() {
        return this.itemNumber;
    }

    public String getName() {
        return this.name;
    }

    public Classification getClassification() {
        return this.objectClass;
    }

    public boolean isContained() {
        return contained;
    }
}
