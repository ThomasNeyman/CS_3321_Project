package Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The player class contains all the data that belongs to the player. This includes
 * their position, bank currency, Number (refers to order), property list, and other
 * info like if they contain the Get Out Of Jail Free card or if they're in Jail.
 */
public class Player {
    private int playerNumber;
    private int Bank;
    private int Position;
    private ArrayList<Property> playerProperties = new ArrayList<>();
    private boolean inJail;
    private boolean hasGOJFC;
    private int jailCount;

    /**
     * Player class constructor
     * @param playerNumber so we can refer to the two seperate players
     * @param bank current money that player can spend without selling any of their assets
     * @param position the players current position on the board.
     */

    public Player(int playerNumber, int bank, int position){
        this.playerNumber = playerNumber;
        Bank = bank;
        Position = position;
        this.inJail = false;
        this.hasGOJFC = false;

    }

    //Getters
    public int getPlayerNumber() {
        return playerNumber;
    }
    public int getBank() {
        return Bank;
    }
    public int getPosition() {
        return Position;
    }
    public ArrayList<Property> getPlayerProperties() {
        return playerProperties;
    }
    //Setters
    public void setBank(int bank) {
        Bank = bank;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public void movePosition(int roll){
        setPosition((getPosition()+roll)%28);
    }
    //getters and setter for inJail

    public boolean isInJail() {
        return inJail;
    }
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    //getters and setters for GOJFC

    public boolean isHasGOJFC() {
        return hasGOJFC;
    }
    public void setHasGOJFC(boolean hasGOJFC) {
        this.hasGOJFC = hasGOJFC;
    }
    // method to add property
    public void addProperty(Property prop){
        playerProperties.add(prop);
    }
    //getters and setter for jail count

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int jailCount) {
        this.jailCount = jailCount;
    }
}
