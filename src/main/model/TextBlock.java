package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// A block of text containing a title and a body.
public class TextBlock implements Writable {

    private String title;
    private String body;

    public TextBlock(String title, String body) {
        this.title = title;
        this.body = body;
    }

    // REFERENCE: CPSC 210 example files
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("body", body);

        return json;
    }

    // getters (setters will be added after phase 1)

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }


}
