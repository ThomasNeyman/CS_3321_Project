package cs3321.client;


import Game.Property;
import Game.State;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.Gson;

/**
 * Helper class for connecting to the server routes
 */
public class Connection {

    String  add;
    String  port;
    HttpClient client;
    static Connection instance = null;

    /**
     *Declared Calls to be referenced from the client controller and within the methods of Connection
     */

    private static final String DICE_CALL = "http://%s:%s/api/diceRoll";
    private static final String PROPERTY_CALL = "http://%s:%s/api/buyProperty";
    private static final String HOUSE_CALL = "http://%s:%s/api/buyHouse";
    private static final String ENDTURN_CALL = "http://%s:%s/api/endTurn";
    private static final String DENYPROPERTY_CALL = "http://%s:%s/api/denyProperty";
    private static final String UPDATE_CALL = "http://%s:%s/api/update";
    private static final String STATUS_CALL = "http://%s:%s/api/status";
    private static final String PLAYERNUM_CALL = "http://%s:%s/api/playerNum";
    private static final String JAIL_ROLL_CALL = "http://%s:%s/api/jailRoll";
    private static final String JAIL_PAY_CALL = "http://%s:%s/api/jailPay";
    private static final String JAIL_CARD_CALL = "http://%s:%s/api/jailCard";


    /**
     * Private default constructor
     */
    private Connection() {
    }

    private static class ConnectionHelper {
        private static final Connection INSTANCE = new Connection();
    }


    public static Connection instance() {
        return ConnectionHelper.INSTANCE;
    }

    /**
     * initializes the singleton so that the address and port connect to the server
     * @param add
     * @param port
     */
    public void initialize(String add, String port){
        this.add = add;
        this.port = port;
        client = HttpClient.newBuilder().build();

    }

    /**
     * disconnects from the server by setting the initialize vaiables to null
     */
    public void disconect(){
        add = null;
        port = null;
        client = null;
    }

    /**
     *
     * @param call
     * @return
     */
    public HttpRequest createGet(String call){
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(call,add,port))).
                GET().
                build();
    }

    /**
     *
     * @param call
     * @param string
     * @return
     */

    public HttpRequest createPost(String call, String string){
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(call,add,port)))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(string))
                .build();
    }

    /**
     * sends dice integer value and then returns the state after the player has moved
     * @param dice
     * @return an updated game state from the server
     * @throws IOException if there was a problem connecting to the server
     * @throws InterruptedException if the update timed out
     */

    public State sendDice(int dice) throws IOException, InterruptedException{
        String d = ""+dice;
        System.out.println(d);
        Gson gson = new Gson();
        HttpRequest request = createPost(DICE_CALL,d);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * sends integer dice value to see if the player rolled his way out of jail
     * @param roll
     * @return the updated state of whether the player is still in jail or not
     * @throws IOException if there was a problem connecting the server
     * @throws InterruptedException if the update timed out
     */

    public State jailRoll(int roll) throws IOException, InterruptedException {
        String s = "" + roll;
        System.out.println(s);
        Gson gson = new Gson();
        HttpRequest request = createPost(JAIL_ROLL_CALL, s);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * Player has been in jail for extended time and must pay to leave
     * @return the updated game state with cash taken out of the players bank
     * @throws IOException of there was a problem connecting to the server
     * @throws InterruptedException if the update timed out
     */

    public State jailPay() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest request = createPost(JAIL_PAY_CALL, "");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * Player has a get out of jail free card that they use to escape
     * @return the updated game state with the player no longer having that card but being free from jail
     * @throws IOException if there was a problem connecting to the server
     * @throws InterruptedException if the update timed out
     */

    public State jailCard() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest request = createPost(JAIL_CARD_CALL, "");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * Player decided to purchase property
     * @param property the unowned property that the player's current position is
     * @return the updated state with an additional property added to the player and money subtracted from the player bank
     * @throws IOException if there was a problem connecting to the server
     * @throws InterruptedException if the update timed out
     */

    public State buyProperty (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        HttpRequest request = createPost(PROPERTY_CALL,json);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * Player decided to purchase house on a property
     * @param property the property that the player owns and wants to build on
     * @return the updated game state from the server
     * @throws IOException if there was a problem connecting to the server
     * @throws InterruptedException if the update timed out
     */

    public State buyHouse (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        HttpRequest request = createPost(HOUSE_CALL,json);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    /**
     * PLayer decided to end their turn so long as they have preformed the required actions
     * @return the updated game state from the server
     * @throws IOException if there was a problem connecting to the server
     * @throws InterruptedException if the update request timed out
     */

    public State endTurn () throws IOException, InterruptedException{
        Gson gson = new Gson();
        HttpRequest request = createPost(ENDTURN_CALL,"");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;

    }

    /**
     * opposite of the buyProperty method where Player doesn't want to purchase property
     * @param property the unowned property that the player landed on after rolling the dice
     * @return the updated game state
     * @throws IOException
     * @throws InterruptedException
     */
    public State denyProperty (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        HttpRequest request = createPost(DENYPROPERTY_CALL,json);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }

    public State updateGameState() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest request = createGet(UPDATE_CALL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        //Thread.sleep(1000);
        return state;
    }

    public int getPlayerNum() throws IOException, InterruptedException {
        HttpRequest request = createGet(PLAYERNUM_CALL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int playerNum = Integer.parseInt(response.body());
        return playerNum;
    }

    public boolean test() {
        HttpRequest request = createGet(STATUS_CALL);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().equals("OK");

        } catch (IOException | InterruptedException ex) {
            return false;
        }
    }
}
