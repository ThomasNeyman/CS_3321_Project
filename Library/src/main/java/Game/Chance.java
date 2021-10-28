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
    public static void getChanceResult(int card, Player player,State gameState) throws Exception {
        switch (card) {
            case 1 -> chanceCard_1(player,gameState);
            case 2 -> chanceCard_2(player,gameState);
            case 3 -> chanceCard_3(player,gameState);
            case 4 -> chanceCard_4(player,gameState);
            case 5 -> chanceCard_5(player,gameState);
            default -> { throw new Exception("Unknown value " + card + " when determining Chance card!");
            }
        }
    }
    public static void getChanceResult(int card, Player player) throws Exception {
        getChanceResult(card,player,new State());
    }

    private static void chanceCard_1(Player player,State gameState) {
        // Advance to GO and collect $200
        player.setPosition(0);
        player.setBank(player.getBank() + 200);
        gameState.setChanceCardDescription("Advance to GO and collect $200");
    }

    private static void chanceCard_2(Player player,State gameState) {
        // Advance to Boardwalk (Don't know what position that is yet
        int oldPos = player.getPosition();
        player.setPosition(10);
        if(player.getPosition()<oldPos){
            player.setBank(player.getBank()+200);
        }
        gameState.setChanceCardDescription("Advance to position 10");

    }

    private static void chanceCard_3(Player player,State gameState) {
        // Advance to Illinois Ave (Don't know position yet)
        int oldPos = player.getPosition();
        player.setPosition(5);
        if(player.getPosition()<oldPos){
            player.setBank(player.getBank()+200);
        }
        gameState.setChanceCardDescription("Advance to position 5");

    }

    private static void chanceCard_4(Player player,State gameState) {
        // Go back 3 spaces
        if (player.getPosition() > 3) {
            player.setPosition(player.getPosition() - 3);
        } else {
            // since there are 28 total squares on the board, moving 25 squares forward
            // is the same as moving 3 backward
            player.setPosition(player.getPosition() + 25);
        }
        gameState.setChanceCardDescription("Go back 3 spaces");

    }

    private static void chanceCard_5(Player player,State gameState) {
        // Get out of Jail Free card
        player.setHasGOJFC(true);
        gameState.setChanceCardDescription("Get out of Jail Free card");
    }

    // We don't need to ever instantiate this class, so it remains private
    private Chance() {

    }

}
