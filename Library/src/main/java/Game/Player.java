package Game;

import java.lang.reflect.Array;

public class Player {
    private int playerNumber;
    private int Bank;
    private int Position;
    private Property[] playerProperties;
    private boolean inJail;

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
    public Property[] getPlayerProperties() {
        return playerProperties;
    }
    //Setters
    public void setBank(int bank) {
        Bank = bank;
    }

    public void movePosition(int roll){

    }
}
