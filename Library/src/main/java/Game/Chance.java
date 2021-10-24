/*
The Chance class is used to randomly affect the player's position in the game.
Contained within the class are multiple methods that do just that.
Because java has no top-level static classes, we simulate one by making the class
final and the constructor private so no instantiation can occur. Then all members
are made static to simulate a static class's functionality.
*/

package Game;

public final class Chance {

    ///<summary>
    /// This method uses the new enhanced version of switch statements with Java 13
    /// Instead of needing break statements for each case, only the case with an arrow
    /// needs to be stated.
    ///<\summary>
    public static void getChanceResult(int card, Player player) {
        switch (card) {
            case 1 -> chanceCard_1(player);
            case 2 -> chanceCard_2(player);
            case 3 -> chanceCard_3(player);
            case 4 -> chanceCard_4(player);
            case 5 -> chanceCard_5(player);
            default -> {
            }
        }
    }

    private static void chanceCard_1(Player player) {
        // Advance to GO and collect $200
    }

    private static void chanceCard_2(Player player) {
        // Advance to Boardwalk
    }

    private static void chanceCard_3(Player player) {
        // Advance to Illinois Ave.
    }

    private static void chanceCard_4(Player player) {
        // Go back 3 spaces
    }

    private static void chanceCard_5(Player player) {
        // Get out of Jail Free card
    }

    // We don't need to ever instantiate this class, so it remains private
    private Chance() {

    }

}
