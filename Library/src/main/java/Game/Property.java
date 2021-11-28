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
    private int houseCost;

    //Constructor

    /**
     * Constructor for the Property Class
     * @param cost the integer value that a player needs to pay in order to purchase that property
     * @param rent the integer value that a player pays when landing on the opposing players property this value can be upgraded if the player who owns the property
     *             decides to purchase houses for it.
     * @param position the property's position on the board
     * @param name the string describing the property name
     */
    public Property(int cost, int rent, int position, String name) {
        this.cost = cost;
        this.rent = rent;
        this.position = position;
        this.name = name;
        this.numberOfHouses = 0;
        this.houseCost = (int) (cost * 0.5);
    }
    public Property(){

    }

    //Getters
    public int getCost() {
        return cost;
    }

    public int getRent() {
        return (rent* (numberOfHouses + 1));
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
    public void setNumberOfHouses(int num) {
        this.numberOfHouses = num;
    }

    // Increase the number of houses on the specific property by 1
    public void incrementHouseNumber() {
        this.numberOfHouses++;
    }
}