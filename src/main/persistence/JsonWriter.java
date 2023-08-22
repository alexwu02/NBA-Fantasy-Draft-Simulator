package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

// Represents a writer that writes JSON representation of team to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // sourced from Json Writer method in JsonSerializationDemo
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // sourced from open method in JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // sourced from write method in JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of team to file
    public void write(League l) {
        JSONObject json = l.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("League saved"));
    }

    // sourced from close method in JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // sourced from saveToFile method in JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

