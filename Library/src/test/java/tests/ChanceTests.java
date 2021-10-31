package tests;

import Game.*;
import org.junit.jupiter.api.*;

public class ChanceTests {

    Player p;
    State state;

    @BeforeEach
    public void setUp() {
        p = new Player(1, 1500, 0);
        state = new State();
    }

    @AfterEach
    public void tearDown() {
        p = null;
    }

    @Test
    @DisplayName("Test for drawing the 1 card")
    public void testDraw_1() throws Exception {
        Chance.getChanceResult(1, p, state);
        Assertions.assertEquals(0, p.getPosition());
        Assertions.assertEquals(1700, p.getBank());
        // The player should be placed at GO and add $200 to their bank
    }

    @Test
    @DisplayName("Test for drawing the 2 card not passing GO")
    public void testDraw_2a() throws Exception {
        Chance.getChanceResult(2, p, state);
        Assertions.assertEquals(10, p.getPosition());
        Assertions.assertEquals(1500, p.getBank());
        // Player should be on position of Boardwalk
        // player should not have passed GO and collected 200$
    }

    @Test
    @DisplayName("Test for drawing the 2 card passing GO")
    public void testDraw_2b() throws Exception {
        p.setPosition(26);
        Chance.getChanceResult(2, p, state);
        Assertions.assertEquals(10, p.getPosition());
        Assertions.assertEquals(1700, p.getBank());
        // Player should be on position of Boardwalk
        // player should have passed GO on the way to the property and collected 200$
    }

    @Test
    @DisplayName("Test for drawing the 3 card")
    public void testDraw_3a() throws Exception {
        Chance.getChanceResult(3, p, state);
        Assertions.assertEquals(5, p.getPosition());
        Assertions.assertEquals(1500, p.getBank());
        // Player should be on position of Illinois Ave
        // The player should not have passed go on the way there
    }

    @Test
    @DisplayName("Test for drawing the 3 card")
    public void testDraw_3b() throws Exception {
        p.setPosition(26);
        Chance.getChanceResult(3, p, state);
        Assertions.assertEquals(5, p.getPosition());
        Assertions.assertEquals(1700, p.getBank());
        // Player should be on position of Illinois Ave
        // Player should have passed go and added 200$
    }

    @Test
    @DisplayName("Test for drawing the 4 card")
    public void testDraw_4a() throws Exception {
        p.setPosition(10);
        Chance.getChanceResult(4, p, state);
        Assertions.assertEquals(7, p.getPosition());
        // Player should have been moved back 3 spaces from 10 to 7
    }

    @Test
    @DisplayName("Test for Drawing 4 card when Pos < 3")
    public void testDraw_4b() throws Exception {
        int oldPos = p.getPosition();
        Chance.getChanceResult(4, p, state);
        Assertions.assertEquals(25, p.getPosition());
        // The player should have 25 added to their position
    }

    @Test
    @DisplayName("Test for drawing the 5 card")
    public void testDraw_5() throws Exception {
        Chance.getChanceResult(5, p, state);
        Assertions.assertTrue(p.isHasGOJFC());
        // The player should have the Get Out Of Jail Free Card true
    }

    @Test
    @DisplayName("Test for incorrect card values")
    public void testDrawNegative() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            Chance.getChanceResult(-1, p, state);
        });

        String expectedMessage = "Unknown value -1 when determining Chance card!";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        // This test makes sure that the thrown exception method reads correctly
        // and is actually catching the expected error behavior
    }

    @Test
    @DisplayName("Test for a card value too large")
    public void testDraw999999() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            Chance.getChanceResult(999999, p, state);
        });

        String expectedMessage = "Unknown value 999999 when determining Chance card!";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        // This test makes sure that the thrown exception method reads correctly
        // and is actually catching the expected error behavior
    }

    @Test
    @DisplayName("Test many Chance cards at once")
    public void testMultipleDraw() throws Exception {
        // start at pos 0, Advance to go (0) + 200$
        Chance.getChanceResult(1, p, state);
        // Advance to position 10
        Chance.getChanceResult(2, p, state);
        // Advance to position 5 passing go + 200$
        Chance.getChanceResult(3, p, state);

        Assertions.assertEquals(5, p.getPosition());
        Assertions.assertEquals(1900, p.getBank());
        // Player should have moved to GO, then Boardwalk, then Illinois Ave
        // with +$400 in their bank
    }

}
