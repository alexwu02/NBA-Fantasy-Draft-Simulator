package teamConstructor;

import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Reason for separate constructor test because in previous class new instances of team will be created with
// @BeforeEach thus will mess up the testConstructor() test to see if the draft posistion are being created properly
public class TestTeamConstructor {
    private Team testTeamConstructor;
    private Team testTeamConstructor2;

    @BeforeEach
    void runBefore() {
        testTeamConstructor = new Team("Brooklyn Nets");
        testTeamConstructor2 = new Team("Denver Nuggets");
    }

    @Test
    void testConstructor() {
        assertEquals(1, testTeamConstructor.getPosition());
        assertEquals("Brooklyn Nets", testTeamConstructor.getTeamName());
        assertTrue(testTeamConstructor.getRoster().isEmpty());
        assertEquals(2, testTeamConstructor2.getPosition());
    }

}
