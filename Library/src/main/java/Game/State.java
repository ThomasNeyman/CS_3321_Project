package Game;

import java.lang.reflect.Array;


public class State {
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
        this.chanceCardIndex = new int[5];
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
}
