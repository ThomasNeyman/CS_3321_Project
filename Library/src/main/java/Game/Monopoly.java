package Game;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Monopoly {

    // state of the game
    private State gameState;
    //constructor
    public Monopoly() {
    }
    public Monopoly(State gameState){
        this.gameState = gameState;
    }
    //getter for gameState
    public State getGameState() {
        return gameState;
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }

    //for use when the player needs to move position
    public void updatePlayerPosition(int diceRoll, int playerNumber){
        //makes sure dice isn't rolled more than once
        if(diceRoll != 0 && gameState.isTurnTaken()){return;}
        //determines which player is being moved
        Player p = null;
        if (gameState.getTurn() == 0){p = gameState.getPlayerOne();}
        if (gameState.getTurn() == 1){p = gameState.getPlayerTwo();}
        int oldPos = p.getPosition();
        p.movePosition(diceRoll);
        // check if you pass go
        if(oldPos<p.getPosition()){
            p.setBank(p.getBank()+200);
        }
        //switch cases for each of the potential spots to land on
        switch (p.getPosition()){
            case 0:
            case 7:
                return;
            case 1:
            case 8:
            case 15:
            case 22:
                drawRandomCard(p);
                break;
            case 2:
                onProperty(p,0);
                break;
            case 3:
                onProperty(p, 1);
                break;
            case 4:
                tax(p, 40);
                break;
            case 5:
                onProperty(p,3);
                break;
            case 6:
                onProperty(p,4);
                break;
            case 9:
                onProperty(p,5);
                break;
            case 10:
                onProperty(p,6);
                break;
            case 11:
                tax(p, 100);
                break;
            case 12:
                onProperty(p,7);
                break;
            case 13:
                onProperty(p, 8);
                break;
            case 14:
                p.setBank(p.getBank()+gameState.getCommunityChest());
                gameState.setCommunityChest(0);
                break;
            case 16:
                onProperty(p,9);
                break;
            case 17:
                onProperty(p,10);
                break;
            case 18:
                tax(p, 150);
                break;
            case 19:
                onProperty(p,11);
                break;
            case 20:
                onProperty(p,12);
                break;
            case 21:
                if(p.isHasGOJFC()){
                    p.setHasGOJFC(false);
                    return;
                }
                p.setInJail(true);
                p.setPosition(7);
                return;
            case 23:
                onProperty(p,13);
                break;
            case 24:
                onProperty(p,14);
                break;
            case 25:
                tax(p,200);
                break;
            case 26:
                onProperty(p,15);
                break;
            case 27:
                onProperty(p,16);
                break;
        }

    }
    private void onProperty(Player p,int pIndex ){
        Player p2;
        if(p.getPlayerNumber()==0){
            p2 = gameState.getPlayerTwo();
        }else{
            p2 = gameState.getPlayerOne();
        }
        Property prop = gameState.getPropertyList()[pIndex];
        // if the property already belongs to the player
        for(int i = 0; i < p.getPlayerProperties().size(); i++){
            if(prop.getPosition() == p.getPlayerProperties().get(i).getPosition()){
                gameState.changeTurn();
                return;
            }
        }
        // if the property belongs to the opposing player
        for(int i = 0; i < p2.getPlayerProperties().size(); i++){
            Property tempProp = p2.getPlayerProperties().get(i);
            if(prop.getPosition() == tempProp.getPosition()){
                int rent = tempProp.getRent();
                p.setBank(p.getBank()-rent);
                p2.setBank(p2.getBank()+rent);
                gameState.changeTurn();
                return;
            }
        }
        //if no one has the property
        gameState.setPropertyAvailable(prop);
        return;
    }

    private void drawRandomCard(Player p){
        if(gameState.getChanceCardIndex().length == 0){
            for(int i = 0; i<5; i++){
                gameState.addChanceCardIndex(i);
            }
        }
        int randomCard = ThreadLocalRandom.current().nextInt(0,gameState.getChanceCardIndex().length);
        int chanceCard = gameState.getChanceCard()[randCard];
        gameState.removeChanceChard(chanceCard);
        Chance.getChanceResult(chanceCard, p);

    }
    //taxes a player when a tax square is landed on, and adds tax to community chest
    private void tax(Player p, int tax){
        p.setBank(p.getBank()-tax);
        gameState.setCommunityChest(gameState.getCommunityChest()+tax);
        gameState.changeTurn();
        return;
    }

    //What happens if a player buys a propery
    public void updatePlayerProperty(Property prop, int playerNum){
        //which player is buying the property
        Player p = null;
        if (playerNum == 0){Player p = gameState.getPlayerOne();}
        if (playerNum == 1){Player p = gameState.getPlayerTwo();}
        p.addProperty(prop);
        gameState.setPropertyAvailable(null);
        if(gameState.isTurnTaken()){
            gameState.changeTurn();
        }


    }

    //if the player doesn't want to buy the property the client
    //will call this, which should change the turn to the next player
    //and give them the option to buy the property, without ending there turn.

     public void denyProperty(Property prop){
         gameState.changeTurn();
     }

     */
}
