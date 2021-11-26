package tests;

import com.google.gson.Gson;
import cs3321.MoServer;
import org.junit.jupiter.api.*;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import Game.*;

public class FunctionalTests {

    private static MoServer server;

    @BeforeAll
    private static void setUp() {
        server = new MoServer();
        server.startServer();
    }

    @AfterAll
    private static void tearDown() {
        //server.stopServer();
    }

    @Test
    @DisplayName("Test setting up the game state and all of its properties")
    public void testStateSetup() {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:7000/api/update").asJson();
        String data = response.getBody().toString();
        State state = new Gson().fromJson(data, State.class);
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
    @DisplayName("")
    public void test() {

    }
}
