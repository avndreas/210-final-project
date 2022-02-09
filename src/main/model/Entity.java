package model;

public class Entity {

    private int itemNumber;
    private String name;
    private Classification objectClass;
    private boolean contained;
    private String containmentProcedures;
    private String description;
    private String addendum;

    public Entity(int itemNumber, String name, Classification objectClass, boolean contained,
                  String containmentProcedures, String description, String addendum) {
        this.itemNumber = itemNumber;
        this.name = name;
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
