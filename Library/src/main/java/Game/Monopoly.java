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
    //getter for gameState
    public State getGameState() {
        return gameState;
    }
    public void updatePlayerPosition(int diceRoll, int playerNumber){
        //determines which player is being moved
        if (gameState.getTurn() == 0){Player p = gameState.getPlayerOne();}
        if (gameState.getTurn() == 1){Player p = gameState.getPlayerTwo();}
        p.movePosition(diceRoll);
        //cases for each of the potential spots to land on
        switch (p.getPosition()){
            case 0:
                p.setBank(p.getBank+200);
                gameState.changeTurn();
                return;
                break;
            case 1:

                break;
            case 2:
                onProperty(p,0);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                break;
            case 22:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
        }

    }
    private void onProperty(Player p,int pIndex ){
        Player p2;
        if(p.getPlayerNumber()==0){
            p2 = gameState.getPlayer2;
        }else{
            p2 = gameState.getPlayer1;
        }
        Property prop = gameState.getPropertyList()[pIndex];
        // if the property already belongs to the player
        for(int i = 0; i < p.getPlayerProperties.length; i++){
            if(prop.getPosition == p.getPlayerProperties[i].getPosition){
                gameState.changeTurn();
                return;
            }
        }
        // if the property belongs to the opposing player
        for(int i = 0; i < p2.getPlayerProperties.length; i++){
            Prperty tempProp = p2.getPlayerProperties[i];
            if(prop.getPosition == tempProp.getPosition){
                int rent = tempProp.getRent();
                p.setBank(p.getBank-rent);
                p2.setBank(p2.getBank+rent);
                gameState.changeTurn();
                return;
            }
        }
        //if no one has the property
        gameState.setPropertyAvailable(prop);
        return;
    }
    private void drawRandomCard(p Player){
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
}
