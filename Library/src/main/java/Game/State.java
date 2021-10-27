package Game;

import java.lang.reflect.Array;


public class State {
    //constants
    private final int CHANCE_DECK_LENGTH = 5;

    //instance variables
    private Player playerOne;
    private Player playerTwo;
    private Property[] propertyList;
    private int[] chanceCardIndex;
    private int turn;
    private int communityChest;
    private boolean hasWon;
    private boolean turnTaken;
    private Property propertyAvailable;

    //Constructor
    public State(){
        this.chanceCardIndex = new int[CHANCE_DECK_LENGTH];
        this.turn = 0;
        this.communityChest=0;
        this.hasWon=false;
    }

    //getter methods
    public Player getPlayerOne() {
        return playerOne;
    }
    public Player getPlayerTwo() {
        return playerTwo;
    }
    public Property[] getPropertyList() {
        return propertyList;
    }
    public int[] getChanceCardIndex() {
        return chanceCardIndex;
    }
    public int getTurn() {
        //if x is 0 then it is Player 1 turn if x is 1 it is player 2s turn
        int x = turn%2;
        return x;
    }
    public int getCommunityChest() {
        return communityChest;
    }
    public int getCHANCE_DECK_LENGTH() {
        return CHANCE_DECK_LENGTH;
    }

    //Setters
    public void setPropertyList(Property[] propertyList) {
        this.propertyList = propertyList;
    }
    public void setCommunityChest(int communityChest) {
        this.communityChest = communityChest;
    }

    //changeturn method to use whenever a player ends their turn.
    public void changeTurn(){
        turn++;
    }
    public int getWinner(){
        return 2;
    }

    //getter and setter for turnTaken value
    public boolean isTurnTaken() {
        return turnTaken;
    }
    public void setTurnTaken(boolean turnTaken) {
        this.turnTaken = turnTaken;
    }

    //getter and setter for available property
    public Property getPropertyAvailable() {
        return propertyAvailable;
    }
    public void setPropertyAvailable(Property propertyAvailable) {
        this.propertyAvailable = propertyAvailable;
    }

    // Chance functions
    public void addChanceCardIndex(int i) {
        chanceCardIndex[i] = i;
    }

    // In order to remove a card from the array, we create another array with length
    // one less than the original array. Then, we copy all the contents of the array
    // except the index we are removing. Then, set the original array equal to the
    // newly created array.
    public void removeChanceCard(int randomCard) {
        int[] arrayCopy = new int[chanceCardIndex.length - 1];

        for (int i = 0, j = 0; i < chanceCardIndex.length - 1; i++) {
            if (i != randomCard) {
                arrayCopy[j++] = chanceCardIndex[i];
            }
        }
        chanceCardIndex = arrayCopy;
    }

}
