package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a league for that contains all teams
public class League implements Writable {
    protected List<Team> league;                  // all the teams in the league
    private String leagueName;                    // league name
    private static boolean leagueCreatedLogged = false;

    // MODIFIES: this
    // EFFECTS: creates a new league and assigns a name to it
    public League(String name) {
        league = new ArrayList<Team>();
        leagueName = name;
        EventLog.getInstance().logEvent(new Event("League created"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("leagueName", leagueName);
        json.put("teams", teamToJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : league) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public List<Team> getLeague() {
        return league;
    }

    // MODIFIES: this
    // EFFECTS: adds a team into the league
    public void addTeam(Team team) {
        league.add(team);
        EventLog.getInstance().logEvent(new Event("Team added"));
    }

}
