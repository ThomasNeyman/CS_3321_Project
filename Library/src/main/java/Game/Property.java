package Game;
/**
 * The property class simply holds the information necessary for each
 * property in the game. This includes their cost, rent, and position
 * on the board.
 */
public class Property{
    //instance variables
    private String name;
    private int cost;
    private int rent;
    private int position;
    private int numberOfHouses;
    /*private int firstHouseRent;
    private int secondHouseRent;
    private int thirdHouseRent;*/
    private int houseCost;

    //Constructor
    public Property(int cost, int rent, int position, String name) {
        this.cost = cost;
        this.rent = rent;
        this.position = position;
        this.name = name;
        this.numberOfHouses = 1;
        // Make the extra rent for houses super simple so each one does not need
        // to be assigned its own special values
       /* this.firstHouseRent = (int) (rent * 1.5);
        this.secondHouseRent = rent * 2;
        this.thirdHouseRent = (int) (rent * 2.5);
        // multiply by 0.5 instead of divide by 2 because it's faster
        this.houseCost = (int) (cost * 0.5);*/
    }
    public Property(){

    }

    //Getters
    public int getCost() {
        return cost;
    }

    public int getRent() {
        /*return switch (numberOfHouses) {
            case 1 -> firstHouseRent;
            case 2 -> secondHouseRent;
            case 3 -> thirdHouseRent;
            default -> rent;
        };*/
        return (rent*numberOfHouses);
    }

    public int getPosition() {
        return  position;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public String getName() {
        return name;
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

    // Increase the number of houses on the specific property by 1
    public void incrementHouseNumber() {
        this.numberOfHouses++;
    }
}