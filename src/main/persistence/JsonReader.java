package persistence;

import model.Database;
import model.Entity;
import model.Classification;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

// REFERENCE: CPSC 210 example files
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Database read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDatabase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Database parseDatabase(JSONObject jsonObject) {
        int series = jsonObject.getInt("series");
        Database d = new Database(series);
        addEntities(d, jsonObject);
        return d;
    }

    // MODIFIES: d
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addEntities(Database d, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfSCPs");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addEntity(Arrays.asList(jsonArray).indexOf(json), d, nextThingy);
        }
    }

    // MODIFIES: d
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addEntity(int number, Database d, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Classification objectClass = Classification.valueOf(jsonObject.getString("objectClass"));
        boolean contained = jsonObject.getBoolean("contained");
        Entity entity = new Entity(number, name, objectClass, contained);
        d.addSCP(entity);
    }
}
