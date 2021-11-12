package cs3321;

import Game.Monopoly;
import io.javalin.Javalin;
import Game.Player;
import Game.Property;
import Game.State;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;


import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class MoServer {

    private int playerNum = 0;
    private Javalin javalin;

    public static void main(String[] args) {

        new MoServer().startServer();
    }

    public void startServer() {
        //Declare monopoly game instance and javalin server
        Monopoly monopoly = new Monopoly();

        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(200, 8, 60000);

        Javalin app = Javalin.create(config ->
                config.server(() ->
                        new Server(queuedThreadPool))).start(7000);

        app.routes(()->{
            //Connection established handler
            get("/", ctx -> ctx.html("Connection Worked!"));


            //Client requests update handler
            get("/api/update", ctx ->
            {
                Gson gson = new Gson();
                System.out.println("Update the Game State");
                State currentstate = monopoly.getGameState();
                String state = gson.toJson(currentstate);
                ctx.json(state);
            });

            post("/api/diceRoll", ctx ->
            {
                System.out.println("Dice Roll");
                State currentState = monopoly.getGameState();

                //if the player hasn't rolled that turn yet.
                if (!monopoly.getGameState().getHasRolledDice()) {
                    int diceRoll = ctx.bodyAsClass(int.class);
                    monopoly.updatePlayerPosition(diceRoll);
                }

                monopoly.setGameState(currentState);
                /*System.out.println("dice");
                int diceRoll = ctx.bodyAsClass(Integer.class);
                monopoly.updatePlayerPosition(diceRoll);*/
            });

            post("/api/buyProperty", ctx ->
            {
                System.out.println("Buy Property");
                Property property = ctx.bodyAsClass(Property.class);
                //add the property to the player
                monopoly.updatePlayerProperty(property, monopoly.getGameState().getCurrentPlayer().getPlayerNumber());


            });

            post("/api/denyProperty", ctx ->
            {
                System.out.println("Deny Property");
                Property property = ctx.bodyAsClass(Property.class);
                //player denies the property
                monopoly.denyProperty(property);
            });

            post("/api/endTurn", ctx ->
            {
                System.out.println("End Turn");

                if (!monopoly.getGameState().getHasRolledDice()) {
                    //send message to client that the player hasn't rolled yet.
                    ctx.result("Player hasn't rolled");
                } else {
                    monopoly.endTurn();
                    if (monopoly.getGameState().getCurrentPlayer().isInJail()) {
                        ctx.result("A turn in Jail has passed");
                    }
                }
                if (monopoly.getGameState().getWinner() != null) {
                    //send message to client that game is over and there is a winner
                    ctx.result("Game Over");
                }
            });


            post("/api/buildHouse", ctx ->
            {
                System.out.println("Build House");
                String propertyName = ctx.bodyAsClass(String.class);
                Property prop = null;
                State currentState = monopoly.getGameState();
                //selects property that player wants to build house on
                for (int i = 0; i < currentState.getCurrentPlayer().getPlayerProperties().size(); i++) {
                    if (Objects.equals(currentState.getCurrentPlayer().getPlayerProperties().get(i).getName(), propertyName)) {
                        prop = currentState.getCurrentPlayer().getPlayerProperties().get(i);
                    }
                }

                if (prop == null) {
                    //send message to client that they cannot select that property
                    ctx.result("Player doesn't own property");
                }

                if (currentState.getCurrentPlayer().getBank() >= prop.getHouseCost()) {
                    if (prop.getNumberOfHouses() != 3) {
                        currentState.getCurrentPlayer().setBank(currentState.getCurrentPlayer().getBank() - prop.getHouseCost());
                        monopoly.updatePropertyHouseNumber(prop);
                    } else {
                        //send messaage to client that there is already max houses
                        ctx.result("Property has full houses");
                    }
                } else {
                    //send message to client that player doesn't have enough funds.
                    ctx.result("Player doesn't have the money");
                }
                monopoly.setGameState(currentState);

            });


            //status handler
            get("api/status", ctx -> {
                ctx.result("OK");
            });

            get("api/playerNum",ctx ->{
                if(playerNum > 1){return;}
                int num = Integer.valueOf(playerNum);
                playerNum ++;
                ctx.result(String.valueOf(num));
            });
        });



    }
}
