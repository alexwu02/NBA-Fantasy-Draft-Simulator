package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDraftPool {
    private DraftPool testDraftPool;

    @BeforeEach
    void runBefore() {
        testDraftPool = new DraftPool();
    }

    @Test
    void testConstructor() {
        assertEquals("Kyrie Irving", testDraftPool.getPool().get(0).getName());
        assertEquals("Kevin Durant", testDraftPool.getPool().get(1).getName());
        assertEquals("Nikola Jokic", testDraftPool.getPool().get(2).getName());
        assertEquals("Julius Randle", testDraftPool.getPool().get(3).getName());
        assertEquals("Luka Doncic", testDraftPool.getPool().get(4).getName());
        assertEquals("Joel Embiid", testDraftPool.getPool().get(5).getName());
        assertEquals("Giannis Antetokounmpo", testDraftPool.getPool().get(6).getName());
        assertEquals("Jayson Tatum", testDraftPool.getPool().get(7).getName());
        assertEquals("Stephen Curry", testDraftPool.getPool().get(8).getName());
        assertEquals("Trae Young", testDraftPool.getPool().get(9).getName());
    }
}
