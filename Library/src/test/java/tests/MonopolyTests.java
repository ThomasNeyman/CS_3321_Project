package tests;

import Game.*;
import org.junit.jupiter.api.*;

public class MonopolyTests {

    Monopoly m;

    @BeforeEach
    public void setUp() {
        m = new Monopoly();
    }

    @AfterEach
    public void tearDown() {
        m = null;
    }

    @Test
    @DisplayName("Test for player landing on chance space and drawing a card")
    public void testChanceCardDraw() {
        // The Chance deck hasn't been filled yet, so it should be empty
        Assertions.assertEquals(0, m.getGameState().getChanceCardIndex().size());
        m.getGameState().getPlayerOne().setPosition(20);

        // The player increases position by 2 to land on Chance
        m.updatePlayerPosition(2);
        // The deck should have been created with 5 cards, then one was removed
        Assertions.assertEquals(4, m.getGameState().getChanceCardIndex().size());
        // We can't test where the player's position is now because it is random
        // SomeFunctionHere()
        // There needs to be a way for the player to end their turn after moving if they choose
        // or not. As of now, after the player moves positions, their turn is over, but they should
        // be able to make other actions until they choose to end.
        // Until implemented, this test fails sometimes
        Assertions.assertTrue(m.getGameState().isTurnTaken());
    }

    @Test
    @DisplayName("Make sure the Game State was created")
    public void testEnsureStateCreated() {
        Assertions.assertNotNull(m.getGameState());
    }

    @Test
    @DisplayName("Test to see if the State can be properly set")
    public void testSetGameState() {
        // Create a new state with different values
        State s = new State();
        s.getPlayerOne().setBank(1234);
        s.getPlayerTwo().setPosition(15);
        s.getPlayerOne().setInJail(true);

        // set the state
        m.setGameState(s);

        // See if the state was properly set
        Assertions.assertEquals(1234, m.getGameState().getPlayerOne().getBank());
        Assertions.assertEquals(15, m.getGameState().getPlayerTwo().getPosition());
        Assertions.assertTrue(m.getGameState().getPlayerOne().isInJail());
    }

    @Test
    @DisplayName("Test for a dice roll of 0")
    public void testUpdatePlayerPosition_a() {
        // player should not move after a dice roll of 0
        m.updatePlayerPosition(0);
        Assertions.assertEquals(0, m.getGameState().getPlayerOne().getPosition());
    }

    @Test
    @DisplayName("Test for rolling while turn is already taken")
    public void testUpdatePlayerPosition_b() {
        // player shouldn't move if their turn is already taken
        m.getGameState().setTurnTaken(true);
        Assertions.assertEquals(0, m.getGameState().getPlayerOne().getPosition());
    }

    @Test
    @DisplayName("Test to make sure the dice roll value is updated properly")
    public void testUpdatePlayerPosition_c() {
        // dice roll should be stored as '5'
        m.updatePlayerPosition(5);
        Assertions.assertEquals(5, m.getGameState().getDiceValue());
    }

    @Test
    @DisplayName("Test for landing on an unowned property")
    public void testOnProperty_a() {
        m.updatePlayerPosition(2);
        // The property is property '0' in the list and should be available
        Assertions.assertEquals(0, m.getGameState().getPropertyAvailable().getPosition());

        m.getGameState().changeTurn();
        m.updatePlayerPosition(5);

        // Now player 2 lands on a property and it should be switched as the available one
        Assertions.assertEquals(2, m.getGameState().getPropertyAvailable().getPosition());
    }

    @Test
    @DisplayName("Test for landing on the other player's property")
    public void testOnProperty_b() {
        // give player 2 property '0'
        m.updatePlayerProperty(m.getGameState().getPropertyList()[0], 1);
        // PLAYER 2'S BANK IS NOW $60 LESS

        // Make sure the property is in player 2's list
        Assertions.assertTrue(m.getGameState().getPlayerTwo().getPlayerProperties().contains(m.getGameState().getPropertyList()[0]));

        m.updatePlayerPosition(2);
        // Player 1 should have landed on the owned property and now pays player 2
        // the rent for property at index '0' is $40
        Assertions.assertEquals(1460, m.getGameState().getPlayerOne().getBank());
        // Player 2 should now have $40 more in their bank
        Assertions.assertEquals(1480, m.getGameState().getPlayerTwo().getBank());
    }

    @Test
    @DisplayName("Test for landing on the other player's property but you can't afford rent")
    public void testOnProperty_c() {
        // give player 2 property '0'
        m.updatePlayerProperty(m.getGameState().getPropertyList()[0], 1);

        m.getGameState().getPlayerOne().setBank(10);
        m.updatePlayerPosition(2);
        // The player should not be allowed to go negative with their bank
        Assertions.assertEquals(0, m.getGameState().getPlayerOne().getBank());
    }

    @Test
    @DisplayName("Test for landing on your own property")
    public void testOnProperty_d() {
        // give player 1 property '0'
        m.updatePlayerProperty(m.getGameState().getPropertyList()[0], 0);
        m.updatePlayerPosition(2);
        // It should now be player 2's turn
        Assertions.assertEquals(1, m.getGameState().getTurn());
        Assertions.assertFalse(m.getGameState().isTurnTaken());
    }

    @Test
    @DisplayName("Test for buying property without enough money")
    public void testUpdatePlayerProperty() {
        // set player's bank to only 5
        m.getGameState().getPlayerOne().setBank(5);
        m.updatePlayerProperty(m.getGameState().getPropertyList()[0], 0);
        // The player should not have been allowed to buy the property and their
        // bank value should not have changed
        Assertions.assertEquals(5, m.getGameState().getPlayerOne().getBank());
    }

    @Test
    @DisplayName("Test for player landing on Tax space")
    public void testTax_a() {
        // The player lands on Tax
        m.updatePlayerPosition(4);
        Assertions.assertEquals(1460, m.getGameState().getPlayerOne().getBank());
        Assertions.assertEquals(40, m.getGameState().getCommunityChest());
    }

    @Test
    @DisplayName("Test for player landing on Tax space without enough money")
    public void testTax_b() {
        // The player lands on Tax with only $5
        m.getGameState().getPlayerOne().setBank(5);
        m.updatePlayerPosition(4);
        Assertions.assertEquals(0, m.getGameState().getPlayerOne().getBank());
        Assertions.assertEquals(5, m.getGameState().getCommunityChest());
    }

    // TODO
    // Make tests for Monopoly.updatePropertyHouseNumber() function
    // Make test for checking NullPointerException on Monopoly.updatePropertyHouseNumber()
    // Test for what happens when player tries to buy a house without enough money
    // Test for what happens when player tries to buy more than 3 houses
    // Make tests for Monopoly.endTurn() function


}
