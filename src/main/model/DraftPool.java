package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.stream.Stream;

import java.util.List;

// represents a draft pool which consists of players that teams can draft from
public class DraftPool {
    protected static ArrayList<Player> pool;      // draftpool containing all available players

    // MODIFIES: this
    // EFFECTS: creates a list of players and adds all current NBA players
    @SuppressWarnings("methodlength") // adding test players as variables
    public DraftPool() {
        pool = new ArrayList<Player>();

        pool.add(new Player("Kyrie Irving", "PG", 0.469, 0.915, 3.4,
                27.4, 4.4, 5.8, 1.4, 0.6, 2.5,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/202681.png"));

        pool.add(new Player("Kevin Durant", "SF", 0.518, 0.910, 2.1,
                29.9, 7.4, 6.4, 0.9, 0.9, 3.5,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/201142.png"));

        pool.add(new Player("Nikola Jokic", "C", 0.583, 0.810, 1.3,
                27.1, 13.8, 7.9, 1.5, 0.9, 3.8,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/203999.png"));

        pool.add(new Player("Julius Randle", "PF", 0.411, 0.756, 1.7,
                20.1, 9.9, 5.1, 0.7, 0.5, 3.4,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/203944.png"));

        pool.add(new Player("Luka Doncic", "SF", 0.457, 0.744, 3.1,
                28.4, 9.1, 8.7, 1.2, 0.6, 4.5,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/1629029.png"));

        pool.add(new Player("Joel Embiid", "C", 0.499, 0.814, 1.4,
                30.6, 11.7, 4.2, 1.1, 1.5, 3.1,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/203954.png"));

        pool.add(new Player("Giannis Antetokounmpo", "PF", 0.553, 0.722,
                1.1, 29.9, 11.6, 5.8, 1.1, 1.4,
                3.3, "https://cdn.nba.com/headshots/nba/latest/1040x760/203507.png"));

        pool.add(new Player("Jayson Tatum", "SG", 0.453, 0.853, 3.0,
                26.9, 8.0, 4.4, 1.0, 0.6, 2.9,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/1628369.png"));

        pool.add(new Player("Stephen Curry", "PG", 0.437, 0.923, 4.5,
                25.5, 5.2, 6.3, 1.3, 0.4, 3.2,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/201939.png"));

        pool.add(new Player("Trae Young", "PG", 0.460, 0.904, 3.1,
                28.4, 3.7, 9.7, 0.9, 0.1, 4.0,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/1629027.png"));
    }

    public ArrayList<Player> getPool() {
        return pool;
    }

}
