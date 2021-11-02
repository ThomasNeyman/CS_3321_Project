package Game;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Monopoly class handles movement in the game and determines what
 * happens when landing on different squares.
 */
public class Monopoly {

    // state of the game
    private State gameState;

    //constructor
    public Monopoly() {
        gameState = new State();
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

    /**
     * Moves the player based on a diceroll value passed in. The function makes
     * sure the diceroll is not 0, which would mean the player has already moved.
     * The function then checks the GameState to see which player needs to be moved.
     * Check to see if the player's old position is higher than their new position. If
     * it is, that means the player moved past GO and needs to add $200 to their bank.
     * Finally, run a switch statement to determine which position the player landed on.
     * @param diceRoll The randomly generated value from 1 to 6
     */
    public void updatePlayerPosition(int diceRoll){
        //makes sure dice isn't rolled more than once
        if(diceRoll != 0 && gameState.isTurnTaken()){return;}
        if(diceRoll != 0){gameState.setDiceValue(diceRoll);}
        //determines which player is being moved
        Player p;
        if (gameState.getTurn() == 0){p = gameState.getPlayerOne();}
        else{p = gameState.getPlayerTwo();}
        int oldPos = Integer.valueOf(p.getPosition());
        p.movePosition(diceRoll);
        // check if you pass go
        if(oldPos>p.getPosition()){
            p.setBank(p.getBank()+200);
        }
        gameState.setTurnTaken(true);
        //switch cases for each of the potential spots to land on
        switch (p.getPosition()){
            case 0:
            case 7:
                return;
            case 1:
            case 8:
            case 15:
            case 22:
                try {
                    drawRandomCard(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                onProperty(p,2);
                break;
            case 6:
                onProperty(p,3);
                break;
            case 9:
                onProperty(p,4);
                break;
            case 10:
                onProperty(p,5);
                break;
            case 11:
                tax(p, 100);
                break;
            case 12:
                onProperty(p,6);
                break;
            case 13:
                onProperty(p, 7);
                break;
            case 14:
                p.setBank(p.getBank()+gameState.getCommunityChest());
                gameState.setCommunityChest(0);
                break;
            case 16:
                onProperty(p,8);
                break;
            case 17:
                onProperty(p,9);
                break;
            case 18:
                tax(p, 150);
                break;
            case 19:
                onProperty(p,10);
                break;
            case 20:
                onProperty(p,11);
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
                onProperty(p,12);
                break;
            case 24:
                onProperty(p,13);
                break;
            case 25:
                tax(p,200);
                break;
            case 26:
                onProperty(p,14);
                break;
            case 27:
                onProperty(p,15);
                break;
        }

    }

    /**
     * This function handles what happens when a player lands on a property. First, check
     * which player has landed on the property, player 1 or 2. Then Check to see if the
     * property already belongs to the player by checking that player's property list.
     * If it does, do nothing for now. Then check to see if the property belongs to the
     * opposing player. If it does, subtract the rent of the property from the player's
     * bank and add it to the opposing player's bank. Finally, if no one owns the property,
     * signal the GameState to set the property available.
     * @param p The player that has landed on a property
     * @param pIndex The index of the property that has been landed on
     */
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
                return;
            }
        }
        // if the property belongs to the opposing player
        for(int i = 0; i < p2.getPlayerProperties().size(); i++){
            Property tempProp = p2.getPlayerProperties().get(i);
            if(prop.getPosition() == tempProp.getPosition()){
                // accounting for number of houses
                int rent = tempProp.getRent();
                p.setBank(p.getBank()-rent);
                p2.setBank(p2.getBank()+rent);
                return;
            }
        }
        //if no one has the property
        gameState.setPropertyAvailable(prop);
        return;
    }

    /**
     * When a player lands on a chance square, this function is called. It first
     * checks to see if the Chance deck in the GameState class has been filled. If
     * it hasn't the function fills it. A random card is then chosen based on the
     * size of the chance card list currently. The card is then removed and the
     * result of the specified chance card index is invoked by the Chance class.
     * The player's position is once again updated with a dice roll of '0'. This
     * ensures the player won't be moved again, but will still trigger any events
     * the Chance card might create, like landing on a property or passing GO.
     * @param p The player that has landed on a chance square
     * @throws Exception Unknown value for a chance card
     */
    private void drawRandomCard(Player p) throws Exception {
        if(gameState.getChanceCardIndex().size() == 0){
            for(int i = 0; i < gameState.getCHANCE_DECK_LENGTH(); i++){
                gameState.addChanceCardIndex(i+1);
            }
        }
        int randomindex = ThreadLocalRandom.current().nextInt(0,gameState.getChanceCardIndex().size());
        // This function simply removes the chance card just drawn from the array. This
        // ensures duplicates won't be drawn
        int card = gameState.removeChanceCard(randomindex);
        // This function executes the chosen chance card on the player
        Chance.getChanceResult(card, p,gameState);
        updatePlayerPosition(0);

    }

    /**
     * taxes a player when a tax square is landed on, and adds tax to community chest
     * @param p The player being texed
     * @param tax the amount that will be taxed from the player
     */
    private void tax(Player p, int tax){
        p.setBank(p.getBank()-tax);
        gameState.setCommunityChest(gameState.getCommunityChest()+tax);
        return;
    }

    /**
     * This function is called when a property is made available for the player to buy.
     * First it checks which player's turn it is then adds the property to their
     * property list. Then the price of the property is subtracted from the player's bank.
     * @param prop the property that is being bought
     * @param playerNum the value attributed to each player
     */
    public void updatePlayerProperty(Property prop, int playerNum){
        //which player is buying the property
        Player p = null;
        if (playerNum == 0){p = gameState.getPlayerOne();}
        if (playerNum == 1){p = gameState.getPlayerTwo();}
        p.addProperty(prop);
        p.setBank(p.getBank()-prop.getCost());
        gameState.setPropertyAvailable(null);
        if(gameState.isTurnTaken()){
            gameState.changeTurn();
        }
    }

    /**
     * This function handles a player buying a house on a property. They can call this
     * function at any time during their turn. This function should be able to be called
     * from the UI at any point during the player's turn.
     * @param prop the property the player wants to add a house to
     * @param playerNum the player whose turn it is
     */
    public void updatePropertyHouseNumber(Property prop, int playerNum) {
        // which player is building a house
        Player p = null;
        if (playerNum == 0){p = gameState.getPlayerOne();}
        if (playerNum == 1){p = gameState.getPlayerTwo();}

        if (prop.getNumberOfHouses() < 3) {
            if (p.getBank() >= prop.getHouseCost()) {
                p.setBank(p.getBank() - prop.getHouseCost());
                prop.incrementHouseNumber();
            } else {
                // Send message to UI saying you can't afford to build a house
            }
        }
        else {
            // send message to UI saying they have the max number of houses
        }
    }

    /**
     * This function be called when the player decides to manually end their turn
     * and the State.changeTurn() function can be called. The player's turn
     * shouldn't end until they hit an 'end turn' button and this function is called.
     */
    public void endTurn() {
        gameState.changeTurn();
        // then call a function that handles the player's turn
    }

    /**
     * if the player doesn't want to buy the property the client
     * will call this, which should change the turn to the next player
     * and give them the option to buy the property, without ending there turn.
     * @param prop The property being denied (Is Never Used)
     */
     public void denyProperty(Property prop){
         gameState.setPropertyAvailable(null);
         gameState.changeTurn();
     }

    @Override
    public String toString() {
        return "Monopoly{" +
                "gameState=" + gameState +
                '}';
    }
}
