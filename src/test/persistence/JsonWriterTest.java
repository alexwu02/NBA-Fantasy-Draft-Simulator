package persistence;

import model.DraftPool;
import model.League;
import model.Player;
import model.Team;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test // sourced from JsonWriterTest class in JsonSerializationDemo
    void testWriterInvalidFile() {
        try {
            League l = new League("alex league");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test // (based off JsonSerializationDemo)
    void testWriterEmptyTeam() {
        try {
            League l = new League("alex league");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeam.json");
            l = reader.read();
            assertEquals("alex league", l.getLeagueName());
            assertEquals(0, l.getLeague().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralWorkroom() {
        try {
            League l = new League("alex league");
            Team t = new Team("alex");
            l.addTeam(t);
            l.getLeague().get(0).draftPlayer("Kyrie Irving");
            l.getLeague().get(0).draftPlayer("Kevin Durant");
            JsonWriter writer = new JsonWriter("./data/testWriterTeam.json");
            writer.open();
            writer.write(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTeam.json");
            l = reader.read();
            assertEquals("alex league", l.getLeagueName());
            assertEquals("alex", l.getLeague().get(0).getTeamName());
            List<Player> drafted = l.getLeague().get(0).getRoster();
            assertEquals(2, drafted.size());
            assertEquals("Kyrie Irving", drafted.get(0).getName());
            assertEquals("Kevin Durant", drafted.get(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}
