package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // sourced from JsonReader method in JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // sourced from read method in JsonSerializationDemo
    // EFFECTS: reads team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public League read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("League loaded"));
        return parseLeague(jsonObject);
    }

    // sourced from readFile method in JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it  (based off JsonSerializationDemo)
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // sourced from parseWorkroom method in JsonSerializationDemo
    // EFFECTS: parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        String name = jsonObject.getString("leagueName");
        League league = new League(name);
        addTeams(league, jsonObject);
        return league;
    }

    // MODIFIES: teams
    // EFFECTS: parses teams from JSON object and adds them to a league
    private void addTeams(League teams, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(teams, nextTeam);
        }
    }

    // MODIFIES: teams
    // EFFECTS: parses a team from JSON object and adds them to a league
    private void addTeam(League teams, JSONObject jsonObject) {
        String name = jsonObject.getString("teamName");
        Team team2 = new Team(name);
        addPlayers(team2, jsonObject);
        teams.addTeam(team2);
    }

    // MODIFIES: t
    // EFFECTS: parses players from JSON object and adds them to a team
    public void addPlayers(Team t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("roster");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(t, nextPlayer);
        }
    }

    // MODIFIES: t
    // EFFECTS: parses a player from JSON object and drafts it to team
    private void addPlayer(Team t, JSONObject jsonObject) {
        String name = jsonObject.getString("playerName");
        t.draftPlayer(name);
    }

}
