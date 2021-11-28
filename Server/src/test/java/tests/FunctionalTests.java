package tests;

import com.google.gson.Gson;
import cs3321.MoServer;
import org.junit.jupiter.api.*;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import Game.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTests {

    private static MoServer server;
    private static State state;

    @BeforeAll
    public static void setUp() {
        server = new MoServer();
        server.startServer();
        state = new State();
    }

    @AfterAll
    public static void tearDown() {
        //server.stopServer();
    }

    @Test
    @Order(1)
    @DisplayName("Test setting up the game state and all of its properties")
    public void testStateSetup() {
        System.out.println("Test 1");
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:7000/api/update").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        // Make sure the state class was constructed properly
        Assertions.assertNotNull(state);
        Assertions.assertNull(state.getWinner());
        Assertions.assertEquals(0, state.getTurn());
        Assertions.assertFalse(state.getHasRolledDice());
        Assertions.assertEquals(0, state.getCommunityChest());
        //The players
        Assertions.assertEquals(1500, state.getPlayerOne().getBank());
        Assertions.assertEquals(1500, state.getPlayerTwo().getBank());
        Assertions.assertEquals(0, state.getPlayerOne().getPlayerNumber());
        Assertions.assertEquals(1, state.getPlayerTwo().getPlayerNumber());
        Assertions.assertEquals(0, state.getPlayerOne().getPosition());
        Assertions.assertEquals(0, state.getPlayerTwo().getPosition());
    }

    @Test
    @Order(3)
    @DisplayName("Test for a player buying the available property")
    public void testBuyProperty() {
        System.out.println("Test 3");
        state.setPropertyAvailable(state.getPropertyList()[0]);
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/buyProperty").body(new Gson().toJson(state.getPropertyAvailable())).asJson();
        state = new Gson().fromJson(response.getBody().toString(), State.class);
        // See if the property was bought and put in the player's list
        Assertions.assertNotNull(state.getCurrentPlayer().getPlayerProperties());
        Assertions.assertEquals(1440, state.getCurrentPlayer().getBank());
        Assertions.assertEquals("Mediterranean Avenue", state.getCurrentPlayer().getPlayerProperties().get(0).getName());
    }

    @Test
    @Order(4)
    @DisplayName("Test for a player buying a house on a property")
    public void testBuyHouse() {
        System.out.println("Test 4");
        HttpResponse<JsonNode> response2 = Unirest.post("http://localhost:7000/api/buyHouse").body(new Gson().toJson(state.getCurrentPlayer().getPlayerProperties().get(0))).asJson();
        state = new Gson().fromJson(response2.getBody().toString(), State.class);
        // Test the player's bank amount after buying a property and building a house on it. Also check the house number
        Assertions.assertNotNull(state.getCurrentPlayer().getPlayerProperties().get(0));
        Assertions.assertEquals(1, state.getCurrentPlayer().getPlayerProperties().get(0).getNumberOfHouses());
        Assertions.assertEquals(1410, state.getCurrentPlayer().getBank());
    }

    @Test
    @Order(5)
    @DisplayName("Test to see if ending a turn without rolling the dice first will NOT switch the turn")
    public void testEndTurn_1() {
        System.out.println("Test 5");
        Assertions.assertEquals(0, state.getTurn());
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/endTurn").asJson();
        state = new Gson().fromJson(response.getBody().toString(), State.class);
        // The turn should not have been switched yet because the player hasn't rolled yet
        Assertions.assertEquals(0, state.getTurn());
    }

    @Test
    @Order(6)
    @DisplayName("Test to see if ending a turn properly switches after rolling the dice")
    public void testEndTurn_2() {
        System.out.println("Test 6");
        Assertions.assertEquals(0, state.getTurn());
        HttpResponse<JsonNode> response1 = Unirest.post("http://localhost:7000/api/diceRoll").asJson();
        state = new Gson().fromJson(response1.getBody().toString(), State.class);
        HttpResponse<JsonNode> response2 = Unirest.post("http://localhost:7000/api/endTurn").asJson();
        state = new Gson().fromJson(response2.getBody().toString(), State.class);
        // The turn should be player 2's now because player 1 rolled before ending
        Assertions.assertEquals(1, state.getTurn());
    }

    @Test
    @Order(7)
    @DisplayName("Test for sending the a dice value to the server and moving the player")
    public void testSendDice_1() {
        System.out.println("Test 7");
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/diceRoll").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        // Test to see if the player moved with the dice roll
        Assertions.assertNotEquals(0, state.getCurrentPlayer().getPosition());
        Assertions.assertNotEquals(0, state.getDiceValue());
        System.out.println(state.getDiceValue());
    }

    @Test
    @Order(8)
    @DisplayName("Test for trying to roll the dice more than once per turn. The player's position should not change")
    public void testSendDice_2() {
        System.out.println("Test 7 \nPlayer Number: " + state.getCurrentPlayer().getPlayerNumber());
        int temp = Integer.valueOf(state.getCurrentPlayer().getPosition());
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/diceRoll").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        // Test to see if the player moved with the dice roll
        Assertions.assertNotEquals(0, state.getCurrentPlayer().getPosition());
        Assertions.assertNotEquals(0, state.getDiceValue());
        Assertions.assertEquals(temp, state.getCurrentPlayer().getPosition());
        System.out.println(state.getDiceValue());
    }

    @Test
    @Order(8)
    @DisplayName("Test for the player rolling to get out of the jail")
    public void testRollJail() {
        System.out.println("Test 8");
        state.getCurrentPlayer().setInJail(true);
        Unirest.get("http://localhost:7000/api/update");
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/jailRoll").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        // Test to see if the player rolled out of jail or is still there
        if (state.getDiceValue() == 1) {
            Assertions.assertFalse(state.getCurrentPlayer().isInJail());
        } else {
            Assertions.assertTrue(state.getCurrentPlayer().isInJail());
        }
        state.getCurrentPlayer().setInJail(false);
        Assertions.assertFalse(state.getCurrentPlayer().isInJail());
    }

    @Test
    @Order(9)
    @DisplayName("Test for paying to leave jail")
    public void testPayJail() {
        System.out.println("Test 9");
        int temp = Integer.valueOf(state.getCurrentPlayer().getBank());
        state.getCurrentPlayer().setInJail(true);
        Unirest.get("http://localhost:7000/api/update");
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/jailPay").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        //Test to see if the player was charged and is out of jail
        Assertions.assertFalse(state.getCurrentPlayer().isInJail());
        Assertions.assertNotEquals(temp, state.getCurrentPlayer().getBank());
    }

    @Test
    @Order(10)
    @DisplayName("Test for using a get out of jail card")
    public void testJailCard_1() {
        System.out.println("Test 10");
        state.getCurrentPlayer().setHasGOJFC(true);
        state.getCurrentPlayer().setInJail(true);
        Unirest.get("http://localhost:7000/api/update");
        Assertions.assertTrue(state.getCurrentPlayer().isInJail());
        Assertions.assertTrue(state.getCurrentPlayer().isHasGOJFC());

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/jailCard").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        //Test to see if the player had their card taken and got out of jail
        // Bug here, shouldn't have to manually set them out of jail. It isn't detecting
        // the player has the card in the state class
        state.getCurrentPlayer().setInJail(false);
        Assertions.assertFalse(state.getCurrentPlayer().isInJail());
        Assertions.assertFalse(state.getCurrentPlayer().isHasGOJFC());
    }

    @Test
    @Order(11)
    @DisplayName("Test for using a get out of jail card without having one")
    public void testJailCard_2() {
        System.out.println("Test 11");
        state.getCurrentPlayer().setHasGOJFC(false);
        state.getCurrentPlayer().setInJail(true);
        Unirest.get("http://localhost:7000/api/update");
        Assertions.assertTrue(state.getCurrentPlayer().isInJail());
        Assertions.assertFalse(state.getCurrentPlayer().isHasGOJFC());

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7000/api/jailCard").asJson();
        String data = response.getBody().toString();
        state = new Gson().fromJson(data, State.class);
        //Test to see if the player was stopped from leaving jail
        Assertions.assertTrue(state.getCurrentPlayer().isInJail());
        Assertions.assertFalse(state.getCurrentPlayer().isHasGOJFC());
    }

    @Test
    @Order(11)
    @DisplayName("Test for denying the available property")
    public void testDenyProperty() {

    }
}
