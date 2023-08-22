package persistence;

import model.League;
import model.Player;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test // sourced from JsonReaderTest class in JsonSerializationDemo
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            League l = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test // (based off JsonSerializationDemo)
    void testReaderEmptyTeam() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTeam.json");
        try {
            League l = reader.read();
            assertEquals("alex league", l.getLeagueName());
            assertEquals(0, l.getLeague().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderTeam.json");
        try {
            League l = reader.read();
            assertEquals("alex league", l.getLeagueName());
            assertEquals("peter", l.getLeague().get(0).getTeamName());
            List<Player> drafted = l.getLeague().get(0).getRoster();
            assertEquals(2, drafted.size());
            assertEquals("Trae Young", drafted.get(0).getName());
            assertEquals("Luka Doncic", drafted.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
