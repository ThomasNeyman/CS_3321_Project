package cs3321.client;


import Game.Property;
import Game.State;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;


public class Connection {

    String  add;
    String  port;
    HttpClient client;
    static Connection instance = null;

    private static final String DICE_CALL = "http://%s:%s/game/diceRoll";
    private static final String PROPERTY_CALL = "http://%s:%s/game/buyProperty";
    private static final String HOUSE_CALL = "http://%s:%s/game/buyHouse";
    private static final String ENDTURN_CALL = "http://%s:%s/game/endTurn";
    private static final String DENYPROPERTY_CALL = "http://%s:%s/game/denyProperty";
    private static final String UPDATE_CALL = "http://%s:%s/game/update";
    private static final String STATUS_CALL = "http://%s:%s/game/update";

    private Connection() {
    }

    private static class ConnectionHelper {
        private static final Connection INSTANCE = new Connection();
    }

    public static Connection instance() {
        return ConnectionHelper.INSTANCE;
    }

    public void initialize(String add, String port){
        this.add = add;
        this.port = port;
        client = HttpClient.newBuilder().build();
    }
    public void disconect(){
        add = null;
        port = null;
        client = null;
    }

    public HttpRequest createGet(String call){
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(call,add,port))).
                GET().
                build();
    }

    public HttpRequest createPost(String call, String json){
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(call,add,port)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    public void sendDice(int dice) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(dice);
        createPost(DICE_CALL,json);
    }

    public void buyProperty (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        createPost(PROPERTY_CALL,json);
    }

    public void buyHouse (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        createPost(HOUSE_CALL,json);
    }

    public void endTurn () throws IOException, InterruptedException{
        createPost(ENDTURN_CALL,"");
    }

    public void denyProperty (Property property) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String json = gson.toJson(property);
        createPost(DENYPROPERTY_CALL,json);
    }



    public State getGameState() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest request = createGet(UPDATE_CALL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        State state = gson.fromJson(response.body(), State.class);
        return state;
    }



    public boolean test() {
        HttpRequest request = createGet(STATUS_CALL);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().equals("OK");
        } catch (IOException | InterruptedException ex) {
            return false;
        }
    }}
