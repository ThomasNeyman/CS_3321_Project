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
    public static void getChanceResult(int card, Player player) throws Exception {
        switch (card) {
            case 1 -> chanceCard_1(player);
            case 2 -> chanceCard_2(player);
            case 3 -> chanceCard_3(player);
            case 4 -> chanceCard_4(player);
            case 5 -> chanceCard_5(player);
            default -> { throw new Exception("Unknown value " + card + " when determining Chance card!");
            }
        }
    }

    private static void chanceCard_1(Player player) {
        // Advance to GO and collect $200
        player.setPosition(0);
        player.setBank(player.getBank() + 200);
    }

    private static void chanceCard_2(Player player) {
        // Advance to Boardwalk (Don't know what position that is yet
        player.setPosition(10);
    }

    private static void chanceCard_3(Player player) {
        // Advance to Illinois Ave (Don't know position yet)
        player.setPosition(5);
    }

    private static void chanceCard_4(Player player) {
        // Go back 3 spaces
        if (player.getPosition() > 3) {
            player.setPosition(player.getPosition() - 3);
        } else {
            // since there are 28 total squares on the board, moving 25 squares forward
            // is the same as moving 3 backward
            player.setPosition(player.getPosition() + 25);
        }
    }

    private static void chanceCard_5(Player player) {
        // Get out of Jail Free card
        player.setHasGOJFC(true);
    }

    // We don't need to ever instantiate this class, so it remains private
    private Chance() {

    }

}
