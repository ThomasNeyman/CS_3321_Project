package Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private int playerNumber;
    private int Bank;
    private int Position;
    private ArrayList<Property> playerProperties = new ArrayList<>();
    private boolean inJail;
    private boolean hasGOJFC;

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
        setPosition(getPosition()+roll);
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
}
