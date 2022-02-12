package model;

// A block of text containing a title and a body.
public class TextBlock {

    private String title;
    private String body;

    public TextBlock(String title, String body) {
        this.title = title;
        this.body = body;
    }

    // getters (setters will be added after phase 1)

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }


}
