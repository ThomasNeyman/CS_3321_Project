/*
Testing class for the Chance Class
https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
*/

package tests;

import Game.*;
import org.junit.jupiter.api.*;
//import static org.assertj.core.api.Assertions.*;

public class ChanceTests {

    Player p;

    @BeforeEach
    public void setUp() {
        p = new Player(0,0,1500);
        p.setPosition(0);
        p.setBank(1500);
        p.setHasGOJFC(false);
        p.setInJail(false);
    }

    @AfterEach
    public void tearDown() {
        p = null;
    }

    @Test
    @DisplayName("Test for drawing the 1 card")
    public void testDraw_1() throws Exception {
        Chance.getChanceResult(1, p);
        Assertions.assertEquals(0, p.getPosition());
        Assertions.assertEquals(1700, p.getBank());
        // The player should be placed at GO and add $200 to their bank
    }

    @Test
    @DisplayName("Test for drawing the 2 card")
    public void testDraw_2() throws Exception {
        Chance.getChanceResult(2, p);
        Assertions.assertEquals(10, p.getPosition());
        // Player should be on position of Boardwalk
    }

    @Test
    @DisplayName("Test for drawing the 3 card")
    public void testDraw_3() throws Exception {
        Chance.getChanceResult(3, p);
        Assertions.assertEquals(5, p.getPosition());
        // Player should be on position of Illinois Ave
    }

    @Test
    @DisplayName("Test for drawing the 4 card")
    public void testDraw_4a() throws Exception {
        p.setPosition(10);
        Chance.getChanceResult(4, p);
        Assertions.assertEquals(7, p.getPosition());
        // Player should have been moved back 3 spaces from 10 to 7
    }

    @Test
    @DisplayName("Test for Drawing 4 card when Pos < 3")
    public void testDraw_4b() throws Exception {
        int oldPos = p.getPosition();
        Chance.getChanceResult(4, p);
        Assertions.assertEquals(25, p.getPosition());
        // The player should have 25 added to their position
    }

    @Test
    @DisplayName("Test for drawing the 5 card")
    public void testDraw_5() throws Exception {
        Chance.getChanceResult(5, p);
        Assertions.assertTrue(p.isHasGOJFC());
        // The player should have the Get Out Of Jail Free Card true
    }

    @Test
    @DisplayName("Test for incorrect card values")
    public void testDrawNegative() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            Chance.getChanceResult(-1, p);
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
            Chance.getChanceResult(999999, p);
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
        Chance.getChanceResult(1, p);
        Chance.getChanceResult(2, p);
        Chance.getChanceResult(3, p);

        Assertions.assertEquals(5, p.getPosition());
        Assertions.assertEquals(1700, p.getBank());
        // Player should have moved to GO, then Boardwalk, the Illinois Ave
        // with +$200 in their bank
    }

}
