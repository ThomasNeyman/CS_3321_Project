/*
Testing class for the Chance Class
https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
*/

package Game;

import Game.*;
import org.junit.jupiter.api.*;
//import static org.assertj.core.api.Assertions.*;

// For now, it is unknown how the functions will operate, so just a
// method call is used as a placeholder
public class ChanceTests {

    Player p = new Player();

    @BeforeAll
    private static void setUp() {

    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    @DisplayName("Test for drawing the 1 card")
    public void testDraw_1() {
        Chance.getChanceResult(1, p);
    }

    @Test
    @DisplayName("Test for drawing the 2 card")
    public void testDraw_2() {
        Chance.getChanceResult(2, p);
    }

    @Test
    @DisplayName("Test for drawing the 3 card")
    public void testDraw_3() {
        Chance.getChanceResult(3, p);
    }

    @Test
    @DisplayName("Test for drawing the 4 card")
    public void testDraw_4() {
        Chance.getChanceResult(4, p);
    }

    @Test
    @DisplayName("Test for drawing the 5 card")
    public void testDraw_5() {
        Chance.getChanceResult(5, p);
    }

    @Test
    @DisplayName("Test for incorrect card values")
    public void testDrawNegative() {
        Chance.getChanceResult(-1, p);
    }

    @Test
    @DisplayName("Test for a card value too large")
    public void testDraw999999() {
        Chance.getChanceResult(999999, p);
    }

    @Test
    @DisplayName("Test for drawing a 0")
    public void testDrawFloat() {
        Chance.getChanceResult(0, p);
    }
}
