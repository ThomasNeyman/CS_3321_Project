package Game;

import java.util.ArrayList;


public class State {
    //constants
    private final int CHANCE_DECK_LENGTH = 5;
    //instance variables
    private Player playerOne;
    private Player playerTwo;
    private Property[] propertyList;
    private ArrayList<Integer> chanceCardIndex;
    private int turn;
    private int communityChest;
    private boolean hasWon;
    private boolean turnTaken;
    private Property propertyAvailable;
    private String chanceCardDescription;
    private int diceValue;

    //Constructor
    public State(){
        //create chance card lit, will be filled by the monopoly state whenever it is empty
        chanceCardIndex = new ArrayList<>();
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
        this.playerOne = new Player(0,1500,0);
        this.playerTwo = new Player(1,1500,0);
        //starting dice
        this.diceValue=5;
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
    public ArrayList<Integer> getChanceCardIndex(){return chanceCardIndex;}
    public int getDiceValue() {
        return diceValue;
    }

    //Setters
    public void setPropertyList(Property[] propertyList) {
        this.propertyList = propertyList;
    }
    public void setCommunityChest(int communityChest) {
        this.communityChest = communityChest;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }

    //changeturn method to use whenever a player ends their turn.
    public void changeTurn(){
        turn++;
        turn = turn%2;
        setTurnTaken(false);
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
        chanceCardIndex.add(i);
    }

    // In order to remove a card from the array, we create another array with length
    // one less than the original array. Then, we copy all the contents of the array
    // except the index we are removing. Then, set the original array equal to the
    // newly created array.
    public int removeChanceCard(int randomCard) {
        int temp = chanceCardIndex.get(randomCard);
        chanceCardIndex.remove(randomCard);
        return temp;
    }

    //getters and setters for chance card description


    public String getChanceCardDescription() {
        return chanceCardDescription;
    }

    public void setChanceCardDescription(String chanceCardDescription) {
        this.chanceCardDescription = chanceCardDescription;
    }
    //method to get the player whos current turn it is
    public Player getCurrentPlayer(){
        if (turn == 0){return playerOne;}
        else{return playerTwo;}
    }

    @Override
    public String toString() {
        String s = "State{" +
                "CHANCE_DECK_LENGTH=" + CHANCE_DECK_LENGTH +
                ", playerOne=" + playerOne.getPosition() +","+playerOne.getBank() +
                ", playerTwo=" + playerTwo.getPosition() +","+playerTwo.getBank() +
                ", turn=" + turn +
                ", communityChest=" + communityChest +
                ", hasWon=" + hasWon +
                ", turnTaken=" + turnTaken;
        if (propertyAvailable != null){
            s = s+", propertyAvailable=" + propertyAvailable.getPosition()+"}";
         }else{
            s = s+", propertyAvailable=" + null +"}";
        }
        return s;

    }
}
