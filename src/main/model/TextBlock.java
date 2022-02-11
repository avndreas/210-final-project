package model;

// A block of text containing a title and a body.
public class TextBlock {

    private String title;
    private String body;

    public TextBlock(String title, String body) {
        this.title = title;
        this.body = body;
    }

    // getters & setters
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
