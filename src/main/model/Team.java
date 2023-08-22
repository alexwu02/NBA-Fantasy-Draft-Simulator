package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a user's team with consists of their team name, position in draft, and roster
public class Team extends DraftPool implements Writable {
    private static int nextDraftPosition = 1;          // tracks draft position of next team created
    private final String teamName;                     // team name
    private final int position;                        // position in draft which you'll draft
    private List<Player> roster;                       // drafted team / roster

    // REQUIRES: teamName has a non-zero length
    // EFFECTS: name on account is set to teamName; position is a positive integer not assigned to any other
    //          player; creates an empty list for roster
    public Team(String teamName) {
        position = nextDraftPosition++;
        this.teamName = teamName;
        roster = new ArrayList<Player>();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPosition() {
        return position;
    }

    public List<Player> getRoster() {
        return roster;
    }

    // REQUIRES: name is a non-zero length
    // MODIFIES: nothing
    // EFFECTS: takes in the name of a player and searches through the draft pool for the player if not found
    // return null
    public Player getPlayer(String name) {
        for (Player player : super.pool) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    // REQUIRES: name is a non-zero length
    // MODIFIES: pool and roster
    // EFFECTS: takes in the name of a player, if the player is still available add to the teams roster and remove from
    //          the pool. If not return the Player.
    public String draftPlayer(String name) {
        EventLog.getInstance().logEvent(new Event("Player drafted"));
        Player player = getPlayer(name);
        if (player != null) {
            pool.remove(player);
            roster.add(player);
            return player.getName();

        } else {
            return null;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", teamName);
        json.put("roster", rosterToJson());
        return json;
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray rosterToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : roster) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}




