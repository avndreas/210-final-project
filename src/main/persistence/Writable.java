package persistence;

import org.json.JSONObject;

// REFERENCE: CPSC 210 example files
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
