package model;

public class TextBlock implements EntityInfo {

    private String title;
    private String body;

    public TextBlock() {

    }

    // getters & setters

    @Override
    public void setContainmentProcedure(String text) {
        this.title = "Special Containment Procedures";
        this.body = text;
    }

    @Override
    public String getContainmentProcedure() {
        return body;
    }

    @Override
    public void setDescription(String text) {
        this.title = "Description";
        this.body = text;
    }

    @Override
    public String getDescription() {
        return this.body;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }


}
