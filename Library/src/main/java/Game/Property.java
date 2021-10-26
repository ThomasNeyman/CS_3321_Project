package Game;

public class Property{
    //instance variables
    private int cost;
    private int rent;
    private int position;

    //Constructor
    public Property(int cost, int rent, int position) {
        this.cost = cost;
        this.rent = rent;
        this.position = position;
    }

    //Getters
    public int getCost() {
        return cost;
    }
    public int getRent() {
        return rent;
    }
    public int getPosition() {
        return  position;
    }

    //Setters
    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
