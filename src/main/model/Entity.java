package model;

// Class for SCPs and all their stats.
public class Entity {

    private int itemNumber;
    private String name;
    private Classification objectClass;
    private boolean contained;
    private String containmentProcedures;
    private String description;
    private String addendum;

    // REQUIRES: itemNumber > 0
    // MODIFIES:
    // EFFECTS: Instantiates a new SCP entity.
    public Entity(int itemNumber, String name, Classification objectClass, boolean contained,
                  String containmentProcedures, String description, String addendum) {
        this.itemNumber = itemNumber;
        this.name = name;
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


    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setObjectClass(Classification objectClass) {
        this.objectClass = objectClass;
    }

    public void setContained(boolean contained) {
        this.contained = contained;
    }

    public void setContainmentProcedures(String procedures) {
        this.containmentProcedures = procedures;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddendum(String addendum) {
        this.addendum = addendum;
    }

    // getters

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

    public String getContainmentProcedures() {
        return containmentProcedures;
    }

    public String getDescription() {
        return description;
    }

    public String getAddendum() {
        return addendum;
    }


}
