package tests;

import Game.*;
import org.junit.jupiter.api.*;

public class StateTests {

    State state;

    @BeforeEach
    public void setUp() {
        state = new State();
    }

    @AfterEach
    public void tearDown() {
        state = null;
    }

    @Test
    @DisplayName("Ensures the State Class is initialized correctly")
    public void testStateSetUp() {
        // There should be 16 entries in the list
        Assertions.assertEquals(16, state.getPropertyList().length);
        // A player should start out with 1500 dollars in their bank
        Assertions.assertEquals(1500, state.getPlayerOne().getBank());
        // It should be turn 0 at the start
        Assertions.assertEquals(0, state.getTurn());
        // Community chest should have 0 value
        Assertions.assertEquals(0, state.getCommunityChest());
    }

    @Test
    @DisplayName("Make sure properties have the correct values set")
    public void testStateProperty() {
        Assertions.assertEquals(60, state.getPropertyList()[0].getCost());
        Assertions.assertEquals(40, state.getPropertyList()[0].getRent());
        Assertions.assertEquals(0, state.getPropertyList()[0].getPosition());
        // Make sure that the property in position 0 was set up correctly
    }

    @Test
    @DisplayName("Test how long the chance deck is. This will need to be changed as deck grows")
    public void testChanceDeckLength() {
        Assertions.assertEquals(5, state.getCHANCE_DECK_LENGTH());
        // The size of the Array list should be 0 because it hasn't been filled yet
        Assertions.assertEquals(0, state.getChanceCardIndex().size());
    }

    @Test
    @DisplayName("Test the use of the Chance deck")
    public void testChanceCardList() {
        // The chance deck should be empty first, but not null
        Assertions.assertNotNull(state.getChanceCardIndex());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            state.getChanceCardIndex().get(0);
        });
        // Now fill the deck
        state.addChanceCardIndex(1);
        state.addChanceCardIndex(2);
        state.addChanceCardIndex(3);
        state.addChanceCardIndex(4);
        state.addChanceCardIndex(5);

        // The arrayList should be length of 5 and the value '3' should be at position 2
        Assertions.assertEquals(5, state.getChanceCardIndex().size());
        Assertions.assertEquals(3, state.getChanceCardIndex().get(2));
    }

    @Test
    @DisplayName("Test for removing a chance card")
    public void testRemoveChanceCard() {
        // fill the deck
        state.addChanceCardIndex(1);
        state.addChanceCardIndex(2);
        state.addChanceCardIndex(3);
        state.addChanceCardIndex(4);
        state.addChanceCardIndex(5);
        // remove a card
        state.removeChanceCard(3);
        // the arrayList should be 1 shorter
        Assertions.assertEquals(4, state.getChanceCardIndex().size());
        // The order of cards in the deck should now be 1, 2, 3, 5
    }

    @Test
    @DisplayName("")
    public void test() {

    }

}
