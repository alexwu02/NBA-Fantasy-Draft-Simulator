package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTeam {
    private Team testTeam;
    private Team testTeam2;

    @Test
    void testGetPlayer() {
        testTeam = new Team("Brooklyn Nets");
        testTeam2 = new Team("Dallas Mavericks");
        DraftPool testPool = new DraftPool();
        assertEquals("Kyrie Irving", testTeam.getPlayer("Kyrie Irving").getName());
        assertEquals("Brooklyn Nets", testTeam.getTeamName());
        assertNull(testTeam.getPlayer("N/A Player"));
    }


    @Test
    void testDraftPlayer() {
        testTeam = new Team("Brooklyn Nets");
        testTeam2 = new Team("Dallas Mavericks");
        DraftPool testPool = new DraftPool();
        Player testPlayerKyrie = testPool.getPool().get(0);

        assertEquals("Kyrie Irving", testTeam.draftPlayer("Kyrie Irving"));
        assertEquals("Kevin Durant", testTeam.draftPlayer("Kevin Durant"));
        assertNull(testTeam2.draftPlayer("N/A Player"));
        assertNull(testTeam2.draftPlayer("Kyrie Irving"));
        assertFalse(testPool.getPool().contains(testPlayerKyrie));
        assertTrue(testTeam.getRoster().contains(testPlayerKyrie));
    }

}
