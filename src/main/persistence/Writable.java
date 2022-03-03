package persistence;

import org.json.JSONObject;

// REFERENCE: CPSC 210 example files
// Interface for object that can be written as a JSON object.
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
