package cs3321;

import Game.Monopoly;
import io.javalin.Javalin;
import Game.Player;
import Game.Property;
import Game.State;


import java.util.Objects;

public class MoServer {

    private Javalin javalin;

    public static void main(String[] args) {

        new MoServer().startServer();
    }

    public void startServer() {
        //Declare monopoly game instance and javalin server
        Monopoly monopoly = new Monopoly();
        javalin = Javalin.create().start(7000);


        //Connection established handler
        javalin.get("/", ctx -> ctx.html("Connection Worked!"));


        //Client requests update handler
        javalin.get("/api/update", ctx ->
        {
            System.out.println("Update the Game State");
            State currentstate = monopoly.getGameState();
            ctx.json(currentstate);
        });

        javalin.post("/api/diceRoll", ctx ->
        {
            System.out.println("Dice Roll");
            State currentState = monopoly.getGameState();
            if (monopoly.getGameState().getCurrentPlayer().isInJail()) {
                //send message to client that player is in jail
                ctx.result("In Jail");
            }

            //if the player hasn't rolled that turn yet.
            if (!monopoly.getGameState().getHasRolledDice()) {
                int diceRoll = ctx.bodyAsClass(int.class);
                monopoly.updatePlayerPosition(diceRoll);
                //if the player lands on a property they can purchase
                if (currentState.getPropertyAvailable() != null && currentState.getCurrentPlayer().getBank() >= currentState.getPropertyAvailable().getCost()) {
                    //sent the property tp the client to display to user.
                    Property property = currentState.getPropertyAvailable();
                    ctx.json(property);
                }

            }

            monopoly.setGameState(currentState);
        });

        javalin.post("/api/buyProperty", ctx ->
        {
            System.out.println("Buy Property");
            Property property = ctx.bodyAsClass(Property.class);
            //add the property to the player
            monopoly.updatePlayerProperty(property, monopoly.getGameState().getCurrentPlayer().getPlayerNumber());


        });

        javalin.post("/api/denyProperty", ctx ->
        {
            System.out.println("Deny Property");
            Property property = ctx.bodyAsClass(Property.class);
            //player denies the property
            monopoly.denyProperty(property);
        });

        javalin.post("/api/endTurn", ctx ->
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


        javalin.post("/api/buildHouse", ctx ->
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
        javalin.get("api/status", ctx -> {
            ctx.result("OK");
        });

    }
}
