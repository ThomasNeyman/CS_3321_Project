package cs3321;

import Game.Monopoly;
import io.javalin.Javalin;
import Game.Player;
import Game.Property;
import Game.State;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import java.util.concurrent.ThreadLocalRandom;


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

            /**
             * dice roll post rout it recieves the random dice roll from the client and updates the players position accordingly
             */

            post("/api/diceRoll", ctx ->
            {
                System.out.println("Dice Roll");

                //if the player hasn't rolled that turn yet.
                if (!monopoly.getGameState().getHasRolledDice()) {
                    int rand = ThreadLocalRandom.current().nextInt(1, 7);
                    monopoly.updatePlayerPosition(rand);
                }
                //int diceRoll = ctx.bodyAsClass(Integer.class);
                //monopoly.updatePlayerPosition(diceRoll);
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });
            /**
             * post route for player trying to roll his way out of jail
             */

            post("/api/jailRoll", ctx -> {
                System.out.println("Dice Roll in Jail");

                int rand = ThreadLocalRandom.current().nextInt(1, 7);
                System.out.println("They rolled a " + rand);
                monopoly.getGameState().rollToLeaveJail(rand);
                monopoly.getGameState().setDiceValue(rand);
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });
            /**
             * post route for when player decides to pay the jail fine
             */

            post("/api/jailPay", ctx -> {
               System.out.println("Pay Jail Fine");

               monopoly.getGameState().payToLeaveJail();
               Gson gson = new Gson();
               String state = gson.toJson(monopoly.getGameState());
               ctx.json(state);
            });
            /**
             * post route for using the get out of jail free card
             */

            post("/api/jailCard", ctx -> {
                monopoly.getGameState().useGetOutOfJailFreeCard();
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });
            /**
             * post route for buying a property after landing on unknown property.
             */

            post("/api/buyProperty", ctx ->
            {
                System.out.println("Buy Property");

                Property property = ctx.bodyAsClass(Property.class);
                System.out.println(property.getCost());
                //add the property to the player
                monopoly.updatePlayerProperty(property, monopoly.getGameState().getCurrentPlayer().getPlayerNumber());
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });
            /**
             * post route when a user lands on unowned property but doesn't want to purchase it
             */

            post("/api/denyProperty", ctx ->
            {
                System.out.println("Deny Property");
                Property property = ctx.bodyAsClass(Property.class);
                //player denies the property
                monopoly.denyProperty(property);
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });
            /**
             * post route for player ending turn. The player cannot end turn if they haven't rolled and it also checks to see if there is a winner each turn
             */

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
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });

/**
 * post route for updating the game state after the user decides to build a house on an owned property
 * sends results to client if the property is ineligible for houses for that player
 */
            post("/api/buyHouse", ctx ->
            {
                System.out.println("Buy House");
                Property prop = ctx.bodyAsClass(Property.class);
                State currentState = monopoly.getGameState();

                // This is the only way I could get it to work
                if (currentState.getCurrentPlayer().getBank() >= prop.getHouseCost()) {
                    if (prop.getNumberOfHouses() < 3) {
                        currentState.getCurrentPlayer().setBank(currentState.getCurrentPlayer().getBank() - prop.getHouseCost());
                        for (int i = 0; i < currentState.getCurrentPlayer().getPlayerProperties().size(); i++) {
                            if (Objects.equals(currentState.getCurrentPlayer().getPlayerProperties().get(i).getName(), prop.getName())) {
                                int value = currentState.getCurrentPlayer().getPlayerProperties().get(i).getNumberOfHouses();
                                currentState.getCurrentPlayer().getPlayerProperties().get(i).setNumberOfHouses(value + 1);
                            }
                        }
                    } else {
                        //send message to client that there is already max houses
                        ctx.result("Property has full houses");
                    }
                } else {
                    //send message to client that player doesn't have enough funds.
                    ctx.result("Player doesn't have the money");
                }
                Gson gson = new Gson();
                String state = gson.toJson(monopoly.getGameState());
                ctx.json(state);
            });

            //status handler
            get("api/status", ctx -> {
                ctx.result("OK");
            });

            /**
             * get route so client can determine number of players joined in the game.
             */
            get("api/playerNum",ctx ->{
                if(playerNum > 1){return;}
                int num = Integer.valueOf(playerNum);
                playerNum ++;
                ctx.result(String.valueOf(num));
            });
        });
    }

    public void stopServer() {
        javalin.stop();
    }
}
