package model;

public class Entity {

    private int itemNumber;
    private String name;
    private Classification objectClass;
    private boolean contained;
    private String containmentProcedures;
    private String description;
    private String addendum;
    private boolean hasEntry;

    public Entity(int itemNumber) {
        this.itemNumber = itemNumber;
        this.hasEntry = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void classifyEntity(Classification objectClass) {
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

    public void setHasEntry(boolean hasEntry) {
        this.hasEntry = hasEntry;
    }

    public boolean getHasEntry() {
        return this.hasEntry;
    }


}
