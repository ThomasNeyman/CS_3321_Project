package cs3321.client;

import Game.*;
import Game.Monopoly;
import Game.gameC;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import Game.State;
import javafx.stage.Stage;

public class ClientController {

    @FXML private ImageView dicePicture;
    //fxml for each game peice
    @FXML private Rectangle s0;
    @FXML private Rectangle s1;
    @FXML private Rectangle s2;
    @FXML private Rectangle s3;
    @FXML private Rectangle s4;
    @FXML private Rectangle s5;
    @FXML private Rectangle s6;
    @FXML private Rectangle s7;
    @FXML private Rectangle s8;
    @FXML private Rectangle s9;
    @FXML private Rectangle s10;
    @FXML private Rectangle s11;
    @FXML private Rectangle s12;
    @FXML private Rectangle s13;
    @FXML private Rectangle s14;
    @FXML private Rectangle s15;
    @FXML private Rectangle s16;
    @FXML private Rectangle s17;
    @FXML private Rectangle s18;
    @FXML private Rectangle s19;
    @FXML private Rectangle s20;
    @FXML private Rectangle s21;
    @FXML private Rectangle s22;
    @FXML private Rectangle s23;
    @FXML private Rectangle s24;
    @FXML private Rectangle s25;
    @FXML private Rectangle s26;
    @FXML private Rectangle s27;
    //fxml for game piece labels
    @FXML private Label l0;
    @FXML private Label l1;
    @FXML private Label l2;
    @FXML private Label l3;
    @FXML private Label l4;
    @FXML private Label l5;
    @FXML private Label l6;
    @FXML private Label l7;
    @FXML private Label l8;
    @FXML private Label l9;
    @FXML private Label l10;
    @FXML private Label l11;
    @FXML private Label l12;
    @FXML private Label l13;
    @FXML private Label l14;
    @FXML private Label l15;
    @FXML private Label l16;
    @FXML private Label l17;
    @FXML private Label l18;
    @FXML private Label l19;
    @FXML private Label l20;
    @FXML private Label l21;
    @FXML private Label l22;
    @FXML private Label l23;
    @FXML private Label l24;
    @FXML private Label l25;
    @FXML private Label l26;
    @FXML private Label l27;

    //chance card description fx id
    @FXML private Label chanceCard;
    //community chest fxml
    @FXML private Label communityChest;
    //Labels for Player stats
    //Banks
    @FXML private Label p1bank;
    @FXML private Label p2bank;
    //turns till out of jail
    @FXML private Label p1jailTurns;
    @FXML private Label p2jailTurns;
    //get out of jail free card
    @FXML private Label p1gojfc;
    @FXML private Label p2gojfc;

    @FXML private Label turn;

    private ObservableList propertyList;








    public ClientController() {

    }

    // @FXML
    /*protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/
    @FXML
    protected void endTurn() {
        if (!gameC.game.getGameState().getHasRolledDice()) {
            Alert notRolledDice = new Alert(Alert.AlertType.WARNING, "You can't end your turn without rolling the dice!!!", ButtonType.OK);
            notRolledDice.show();
        } else {
            gameC.game.endTurn();
            update(gameC.game.getGameState());
        }
    }

    @FXML
    protected void buildHouse() {
        ListView<String> listView = new ListView<>();
        for (int i = 0; i < gameC.state.getCurrentPlayer().getPlayerProperties().size(); i++) {
            listView.getItems().add(gameC.state.getCurrentPlayer().getPlayerProperties().get(i).getName());
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button button = new Button("Build House");
        button.setOnAction(e -> buildHouseClicked(listView));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(listView, button);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout,300, 300);
        Stage window = new Stage();
        window.setTitle("Select a property");
        window.setScene(scene);
        window.show();
    }

    private void buildHouseClicked(ListView<String> listView) {
        String propertyName = listView.getSelectionModel().getSelectedItem();
        Property prop = null;
        if (propertyName == null) {
            Alert nothingSelected = new Alert(Alert.AlertType.WARNING, "Nothing is Selected!", ButtonType.OK);
            nothingSelected.show();
            return;
        }

        for (int i = 0; i < gameC.state.getCurrentPlayer().getPlayerProperties().size(); i++) {
            if (Objects.equals(gameC.state.getCurrentPlayer().getPlayerProperties().get(i).getName(), propertyName)) {
                prop = gameC.state.getCurrentPlayer().getPlayerProperties().get(i);
            }
        }
        if (prop == null) {
            Alert errorChoosingHouse = new Alert(Alert.AlertType.ERROR, "Unable to choose property " + propertyName, ButtonType.OK);
            errorChoosingHouse.show();
            return;
        }

        if (gameC.state.getCurrentPlayer().getBank() > prop.getHouseCost()) {
            if (prop.getNumberOfHouses() != 3) {
                gameC.state.getCurrentPlayer().setBank(gameC.state.getCurrentPlayer().getBank() - prop.getHouseCost());
                gameC.game.updatePropertyHouseNumber(prop);
            } else {
                Alert tooManyHouses = new Alert(Alert.AlertType.WARNING, "This property already has the maximum number of houses!", ButtonType.OK);
                tooManyHouses.show();
            }
        } else {
            Alert canNotAfford = new Alert(Alert.AlertType.WARNING, "You can't afford to build a house on " + prop.getName() + " (position " + prop.getPosition() + ")", ButtonType.OK);
            canNotAfford.show();
        }
        update(gameC.state);
    }

    @FXML
    protected void diceClick() throws IOException {
        if (!gameC.game.getGameState().getHasRolledDice()) {
            int rand = ThreadLocalRandom.current().nextInt(1, 7);
            Image diceImage = new Image(getClass().getResourceAsStream("images/dice_" + rand + ".png"));
            dicePicture.setImage(diceImage);
            State state = gameC.state;
            gameC.game.updatePlayerPosition(rand);
            update(state);
            if(state.getPropertyAvailable() != null && state.getCurrentPlayer().getBank() > state.getPropertyAvailable().getCost()){
                Alert buyProperty = new Alert(Alert.AlertType.NONE, "Do you want to buy property "+state.getPropertyAvailable().getPosition()+", for $"+state.getPropertyAvailable().getCost()+"",ButtonType.OK, ButtonType.NO);

                buyProperty.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK){
                        gameC.game.updatePlayerProperty(state.getPropertyAvailable(),state.getTurn());
                    }
                    else{
                        gameC.game.denyProperty(state.getPropertyAvailable());
                    }
                });
            }else{
                gameC.game.denyProperty(state.getPropertyAvailable());
                // Just because the property is denied doesn't mean it's because the person can't afford it, error check this
                //Alert canNotAfford = new Alert(Alert.AlertType.NONE, "You are unable to afford this property. It will be auctioned off instead", ButtonType.OK);
                //canNotAfford.show();
            }
            update(gameC.state);
        }
        else {
            Alert alreadyRolledDice = new Alert(Alert.AlertType.WARNING, "YOU HAVE ALREADY ROLLED THE DICE THIS ROUND!!!", ButtonType.OK);
            alreadyRolledDice.show();
        }
    }

    protected void init() {
        Monopoly game = new Monopoly();
        update(game.getGameState());
    }

    private void update(State gameState){
        //defines color of each rectangle, blue for playerone, red for playertwo, nad grey for the rest
        Rectangle[] tile = {s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s21,s21,s22,s23,s24,s25,s26,s27};
        Label[] tileName = {l0,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16,l17,l18,l19,l21,l21,l22,l23,l24,l25,l26,l27};
        //creates a gradient if both players are on the same space
        if(gameState.getPlayerOne().getPosition()==gameState.getPlayerTwo().getPosition()){
            for(int i=0; i<28;i++){
                if(gameState.getPlayerTwo().getPosition()==i){
                    Stop[] stops = new Stop[]{new Stop(0,Color.BLUE),new Stop(1,Color.RED)};
                    LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
                    tile[i].setFill(gradient);
                    tileName[i].setTextFill(Color.WHITE);
                }else{
                    tile[i].setFill(Color.LIGHTGREY);
                    tileName[i].setTextFill(Color.BLACK);
                }
            }
            //if on seperate spaces colors each block
        }else{
            for(int i=0; i<28;i++){
                if(gameState.getPlayerOne().getPosition()==i){
                    tile[i].setFill(Color.BLUE);
                    tileName[i].setTextFill(Color.WHITE);
                }else if(gameState.getPlayerTwo().getPosition()==i){
                    tile[i].setFill(Color.RED);
                    tileName[i].setTextFill(Color.WHITE);
                }
                else{
                    tile[i].setFill(Color.LIGHTGREY);
                    tileName[i].setTextFill(Color.BLACK);
                }
            }
            tile[gameState.getPlayerOne().getPosition()].setFill(Color.BLUE);
            tile[gameState.getPlayerTwo().getPosition()].setFill(Color.RED);
        }
        //Set color of properties owned by player
        Rectangle[] property = {s2,s3,s5,s6,s9,s10,s12,s13,s16,s17,s19,s20,s23,s24,s26,s27};
        Label[] propertyLabel = {l2,l3,l5,l6,l9,l10,l12,l13,l16,l17,l19,l20,l23,l24,l26,l27};
        for (int i =0;i< property.length;i++){
            for(int j = 0;j<gameState.getPlayerOne().getPlayerProperties().size();j++){
                if(gameState.getPlayerOne().getPlayerProperties().get(j).getPosition() == i && tile[gameState.getPlayerOne().getPosition()] != property[i]  && tile[gameState.getPlayerTwo().getPosition()] != property[i] ){
                    property[i].setFill(Color.LIGHTBLUE);
                    propertyLabel[i].setText("P1-$"+gameState.getPropertyList()[i].getRent());
                }else if(gameState.getPlayerOne().getPlayerProperties().get(j).getPosition() == i){
                    propertyLabel[i].setText("P1-$"+gameState.getPropertyList()[i].getRent());
                }

            }
            for(int j = 0;j<gameState.getPlayerTwo().getPlayerProperties().size();j++){
                if(gameState.getPlayerTwo().getPlayerProperties().get(j).getPosition() == i && tile[gameState.getPlayerOne().getPosition()] != property[i] && tile[gameState.getPlayerTwo().getPosition()] != property[i]){
                    property[i].setFill(Color.PINK);
                    propertyLabel[i].setText("P2-$"+gameState.getPropertyList()[i].getRent());
                }else if(gameState.getPlayerTwo().getPlayerProperties().get(j).getPosition() == i){
                    propertyLabel[i].setText("P2-$"+gameState.getPropertyList()[i].getRent());
                }
            }
        }

        //sets chance card description
        chanceCard.setText(gameState.getChanceCardDescription());
        //sets community Chest
        communityChest.setText("$"+gameState.getCommunityChest());
        //set banks
        p1bank.setText("Bank $"+gameState.getPlayerOne().getBank());
        p2bank.setText("Bank $"+gameState.getPlayerTwo().getBank());
        //set turns till out of Jail
        p1jailTurns.setText("");
        p2jailTurns.setText("");
        //set get out of jail free cards
        p1gojfc.setText("");
        p2gojfc.setText("");
        if(gameState.getPlayerOne().isHasGOJFC()){
            p1gojfc.setText("Get Out of Jail");
        }
        if(gameState.getPlayerTwo().isHasGOJFC()){
            p2gojfc.setText("Get Out of Jail");
        }
        turn.setText("Player "+(gameState.getTurn() + 1)+"'s Turn");
        //sets color of dice
        Image diceImage = new Image(getClass().getResourceAsStream("images/dice_" + gameState.getDiceValue() + ".png"));
        dicePicture.setImage(diceImage);

    }


}