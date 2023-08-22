package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {

    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Kyrie Irving", "PG", 0.469, 0.915, 3.4,
                27.4, 4.4, 5.8, 1.4, 0.6, 2.5,
                "https://cdn.nba.com/headshots/nba/latest/1040x760/202681.png");
    }

    @Test
    void testConstructor() {
        assertEquals("Kyrie Irving", testPlayer.getName());
        assertEquals("PG", testPlayer.getPosition());
        assertEquals(0.469, testPlayer.getFg());
        assertEquals(0.915, testPlayer.getFt());
        assertEquals(3.4, testPlayer.getThrees());
        assertEquals(27.4, testPlayer.getPoints());
        assertEquals(4.4, testPlayer.getRebounds());
        assertEquals(5.8, testPlayer.getAssists());
        assertEquals(1.4, testPlayer.getSteals());
        assertEquals(0.6, testPlayer.getBlocks());
        assertEquals(2.5, testPlayer.getTurnovers());
        assertEquals("https://cdn.nba.com/headshots/nba/latest/1040x760/202681.png", testPlayer.getPicture());
    }
}
