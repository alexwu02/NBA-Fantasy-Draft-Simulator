package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an NBA Player average stats which consists of their name position and average FG%, FT%, 3PT, Points,
// Rebounds, Assists, Steals, Blocks, and Turnovers
public class Player implements Writable {
    private String name;                // player name
    private String position;            // position played
    private double fg;                  // average field goals %
    private double ft;                  // average free throw %
    private double threes;              // average 3 pointers made
    private double points;              // average points
    private double rebounds;            // average rebounds
    private double assists;             // average assists
    private double steals;              // average steals
    private double blocks;              // average blocks
    private double turnovers;           // average turnovers
    private String picLink;           // average turnovers

    // REQUIRES: avgFg, avgFt, avgThrees, avgPoints, avgRebounds, avgAssists, avgSteals, avgBlocks, avgTurnovers are
    //           non-negative and playerName and playerPosition has a non-zero length
    //
    // EFFECTS: name on player is set to playerName; position on player is set to playerPosition fg on player is set to
    //          avgFg; ft on player is set to avgFt; threes on player is set to avgThrees; points on player is set to
    //          avgPoints; rebounds on player is set to avgRebounds; assists on player is set to avgAssists; steals on
    //          player is set to avgSteals; blocks on player is set to avgBlocks; turnovers on player is set to
    //          avgTurnovers;

    public Player(String playerName, String playerPosition, double avgFg, double avgFt, double avgThrees, double
            avgPoints, double avgRebounds, double avgAssists, double avgSteals, double avgBlocks, double avgTurnovers,
                  String picture) {
        name = playerName;
        position = playerPosition;
        fg = avgFg;
        ft = avgFt;
        threes = avgThrees;
        points = avgPoints;
        rebounds = avgRebounds;
        assists = avgAssists;
        steals = avgSteals;
        blocks = avgBlocks;
        turnovers = avgTurnovers;
        picLink = picture;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getFg() {
        return fg;
    }

    public double getFt() {
        return ft;
    }

    public double getThrees() {
        return threes;
    }

    public double getPoints() {
        return points;
    }

    public double getRebounds() {
        return rebounds;
    }

    public double getAssists() {
        return assists;
    }

    public double getSteals() {
        return steals;
    }

    public double getBlocks() {
        return blocks;
    }

    public double getTurnovers() {
        return turnovers;
    }

    public String getPicture() {
        return picLink;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerName", name);
        return json;
    }

}