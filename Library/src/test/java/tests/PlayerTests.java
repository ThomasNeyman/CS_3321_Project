package tests;

import Game.*;
import org.junit.jupiter.api.*;

/*
 There really isn't a lot to test with the Player class because
 it simply holds information and doesn't provide a lot of functionality
*/
public class PlayerTests {

    Player p;

    @BeforeEach
    public void setUp() {
        p = new Player(0, 1500, 0);
    }

    @AfterEach
    public void tearDown() {
        p = null;
    }

    @Test
    @DisplayName("Test to make sure player is created correctly")
    public void testPlayer_a() {
        Assertions.assertEquals(1500, p.getBank());
        Assertions.assertEquals(0, p.getPosition());
        Assertions.assertFalse(p.isInJail());
        Assertions.assertFalse(p.isHasGOJFC());
    }

    @Test
    @DisplayName("Test to make sure Player properties are set correctly")
    public void testPlayer_b() {
        p.setPosition(15);
        p.setBank(200);
        p.setHasGOJFC(true);

        Assertions.assertEquals(200, p.getBank());
        Assertions.assertEquals(15, p.getPosition());
        Assertions.assertTrue(p.isHasGOJFC());
    }

    @Test
    @DisplayName("Test for placing player in jail")
    public void testSetInJail() {
        p.setInJail(true);
        Assertions.assertTrue(p.isInJail());
    }

    @Test
    @DisplayName("Test for adding properties to a player")
    public void testAddProperty() {
        Property prop1 = new Property(60, 40, 0);
        Property prop2 = new Property(1000, 400, 5);
        p.addProperty(prop1);
        // Player should have property 1 in their list
        Assertions.assertTrue(p.getPlayerProperties().contains(prop1));
        // Player should NOT have property 2 in their list
        Assertions.assertFalse(p.getPlayerProperties().contains(prop2));
    }

}
