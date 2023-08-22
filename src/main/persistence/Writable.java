package persistence;

import org.json.JSONObject;

public interface Writable {
    // sourced from Writable implementation in JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
