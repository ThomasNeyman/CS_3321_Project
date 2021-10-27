package Game;

import java.lang.reflect.Array;
import java.util.Arrays;


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
        //creation of propertyList
        this.propertyList = new Property[16];
        this.propertyList[0]= new Property(60,40,0);
        this.propertyList[1]= new Property(60,45,1);
        this.propertyList[2]= new Property(100,60,2);
        this.propertyList[3]= new Property(100,70,3);
        this.propertyList[4]= new Property(150,100,4);
        this.propertyList[5]= new Property(150,110,5);
        this.propertyList[6]= new Property(175,115,6);
        this.propertyList[7]= new Property(175,120,7);
        this.propertyList[8]= new Property(200,125,8);
        this.propertyList[9]= new Property(200,130,9);
        this.propertyList[10]= new Property(250,150,10);
        this.propertyList[11]= new Property(250,160,11);
        this.propertyList[12]= new Property(300,200,12);
        this.propertyList[13]= new Property(300,220,13);
        this.propertyList[14]= new Property(350,275,14);
        this.propertyList[15]= new Property(400,300,15);
        //creation of the Players
        this.playerOne = new Player(0,200,0);
        this.playerTwo = new Player(1,200,0);

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

    @Override
    public String toString() {
        return "State{" +
                "CHANCE_DECK_LENGTH=" + CHANCE_DECK_LENGTH +
                ", playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                ", propertyList=" + Arrays.toString(propertyList) +
                ", chanceCardIndex=" + Arrays.toString(chanceCardIndex) +
                ", turn=" + turn +
                ", communityChest=" + communityChest +
                ", hasWon=" + hasWon +
                ", turnTaken=" + turnTaken +
                ", propertyAvailable=" + propertyAvailable +
                '}';
    }
}
