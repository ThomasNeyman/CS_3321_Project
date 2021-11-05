package Game;

import java.util.ArrayList;

/**
 * The state class' main function is to hold all of the information for the currently
 * running game. This includes the players, the list of properties, whose turn is it,
 * how much money is in the community chest, and if a player has won.
 */
public class State {
    //constants
    private final int CHANCE_DECK_LENGTH = 5;
    //instance variables
    private Player playerOne;
    private Player playerTwo;
    private Player winner;
    private Property[] propertyList;
    private ArrayList<Integer> chanceCardIndex;
    private int turn;
    private int communityChest;
    private boolean hasWon;
    // I think we can get rid of the turnTaken variable. it is irrelevant
    // because the player will always manually end their turn
    private boolean turnTaken;
    private Property propertyAvailable;
    private String chanceCardDescription;
    private int diceValue;
    private boolean hasRolledDice;

    //Constructor
    public State(){
        //create chance card lit, will be filled by the monopoly state whenever it is empty
        chanceCardIndex = new ArrayList<>();
        //creation of propertyList
        this.propertyList = new Property[16];
        this.propertyList[0]= new Property(60,40,0, "Mediterranean Avenue");
        this.propertyList[1]= new Property(60,45,1, "Baltic Avenue");
        this.propertyList[2]= new Property(100,60,2, "Oriental Avenue");
        this.propertyList[3]= new Property(100,70,3, "Vermont Avenue");
        this.propertyList[4]= new Property(150,100,4, "Connecticut Avenue");
        this.propertyList[5]= new Property(150,110,5, "St. Charles Place");
        this.propertyList[6]= new Property(175,115,6, "Virginia Avenue");
        this.propertyList[7]= new Property(175,120,7, "New York Avenue");
        this.propertyList[8]= new Property(200,125,8, "Atlantic Avenue");
        this.propertyList[9]= new Property(200,130,9, "Ventnor Avenue");
        this.propertyList[10]= new Property(250,150,10, "Marvin Gardens");
        this.propertyList[11]= new Property(250,160,11, "Pacific Avenue");
        this.propertyList[12]= new Property(300,200,12, "North Carolina Avenue");
        this.propertyList[13]= new Property(300,220,13, "Pennsylvania Avenue");
        this.propertyList[14]= new Property(350,275,14, "Park Place");
        this.propertyList[15]= new Property(400,300,15, "Boardwalk");
        //creation of the Players
        this.playerOne = new Player(0,1500,0);
        this.playerTwo = new Player(1,1500,0);
        this.winner = null;
        //starting dice
        this.diceValue=5;
        this.turn = 0;
        this.communityChest=0;
        this.hasWon=false;
        this.hasRolledDice = false;
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
        return turn;
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
    public boolean getHasRolledDice() {
        return hasRolledDice;
    }
    public Player getWinner() {
        return winner;
    }

    //Setters
    public void setPropertyList(Property[] propertyList) {
        this.propertyList = propertyList;
    }
    public void setCommunityChest(int communityChest) {
        this.communityChest = communityChest;
    }
    public void setHasRolledDice(boolean value) {
        this.hasRolledDice = value;
    }
    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }
    public void setWinner(Player p) {
        this.winner = p;
    }

    /**
     * Function handles switching the player's turns. There are only
     * 2 players right now, so a modulus by 2 works to determine this
     */
    public void changeTurn(){
        turn++;
        turn = turn%2;
        setTurnTaken(false);
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

    /**
     * When a chance card is being invoked, it needs to be removed from the
     * deck. This function is called by Monopoly and will remove the chosen
     * random chance card from the Chance deck stored in this State class.
     * @param randomCard The randomly chosen chance card by the Monopoly class
     * @return the idnex of the removed card
     */
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
