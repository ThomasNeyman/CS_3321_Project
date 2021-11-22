package cs3321.client;

import Game.*;
import Game.gameC;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import Game.State;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ClientController {

    @FXML private ImageView dicePicture;

    @FXML private Pane main;
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

//FXML for the connection board

    @FXML private Label addressLabel, portLabel;
    @FXML private TextField address, port;
    @FXML private Button connect;

    private boolean initialized = false;


    Connection connection = Connection.instance();
    private int playerNum = -1;
    private State gameState;
    private HBox buyHouseBox;

    public ClientController() {

    }

    public void updateUI() throws IOException, InterruptedException {
        gameState = connection.updateGameState();
        update(gameState);
    }

    @FXML
    protected void connect() throws IOException, InterruptedException {
        if(!address.getText().isEmpty() && !port.getText().isEmpty()){

            connection.initialize(address.getText(), port.getText());


            System.out.println(connection.test());
            if (!connection.test()){
                Alert badInput = new Alert(Alert.AlertType.WARNING, "Unable to connect, make sure your port and address are correct", ButtonType.OK);
                badInput.show();
            }else{
                playerNum = connection.getPlayerNum();

                address.setVisible(false);
                port.setVisible(false);
                addressLabel.setText("Connected!");
                int num = playerNum+1;
                portLabel.setText("Player # "+num+"");
                connect.setVisible(false);

                gameState = connection.updateGameState();

                update(gameState);

                    }
        }else{
            Alert emptyInput = new Alert(Alert.AlertType.WARNING, "One or more fields is empty", ButtonType.OK);
            emptyInput.show();
        }
    }

    @FXML
    protected void endTurn() throws IOException, InterruptedException {
        if (playerNum != gameState.getTurn()){
            Alert notYourTurn = new Alert(Alert.AlertType.WARNING,"Its not your turn", ButtonType.OK);
            notYourTurn.show();
            return;
        }

        gameState = connection.updateGameState();
        if (!gameState.getHasRolledDice()) {
            Alert notRolledDice = new Alert(Alert.AlertType.WARNING, "You can't end your turn without rolling the dice!!!", ButtonType.OK);
            notRolledDice.show();
        } else {
            connection.endTurn();
            gameState = connection.updateGameState();
            update(gameState);
            if (gameState.getCurrentPlayer().isInJail()) {
                gameState = connection.updateGameState();
                inJailHandler();
            }
        }
        if (gameState.getWinner() != null) {
            Alert gameEnds = new Alert(Alert.AlertType.NONE, "Player " + (gameState.getWinner().getPlayerNumber() + 1) + " has won!", ButtonType.OK);
            gameEnds.showAndWait().ifPresent(confirm -> {
                if (confirm == ButtonType.OK) {
                    Platform.exit();
                }
            });
        }
    }

    protected void inJailHandler() throws IOException, InterruptedException {
        gameState = connection.updateGameState();
        Stage window = new Stage();

        Text text = new Text("You are in Jail! You may either pay the fine of $50 to leave or attempt to roll a 1 to be let out for free.");
        text.setWrappingWidth(250);

        Button rollDice = new Button("Roll Dice");
        rollDice.setStyle("-fx-font-size:28");
        rollDice.setPrefSize(200, 100);
        rollDice.setOnAction(e -> {
            try {
                jailRollDiceClicked(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        Button payFine = new Button("Pay Fine");
        payFine.setStyle("-fx-font-size:28");
        payFine.setPrefSize(200, 100);
        payFine.setOnAction(e -> {
            try {
                jailPayFineClicked(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        Button getOutFree = new Button("Use \"Get out of jail FREE\" card");
        getOutFree.setStyle("-fx-font-size:12");
        getOutFree.setPrefSize(200, 100);
        getOutFree.setOnAction(e -> {
            try {
                jailGetOutFree(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(text, rollDice, payFine, getOutFree);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,300, 300);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("You are in Jail");
        window.setScene(scene);
        window.showAndWait();

        gameState = connection.updateGameState();
        update(gameState);
    }

    private void jailRollDiceClicked(Stage window) throws IOException, InterruptedException {
        int rand = ThreadLocalRandom.current().nextInt(1, 7);
        connection.jailRoll(rand);
        gameState = connection.updateGameState();
        update(gameState);

        if (rand == 1) {
            Alert alert = new Alert(Alert.AlertType.NONE, "You rolled a 1. You are now free from Jail!", ButtonType.OK);
            alert.showAndWait().ifPresent(confirm -> {
                if (confirm == ButtonType.OK) window.close();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "You rolled a " + rand + ". You will remain in Jail this turn!", ButtonType.OK);
            alert.showAndWait().ifPresent(confirm -> {
                if (confirm == ButtonType.OK) window.close();
            });
        }
        //gameState = connection.updateGameState();
    }

    private void jailPayFineClicked(Stage window) throws IOException, InterruptedException {
        connection.jailPay();
        gameState = connection.updateGameState();
        update(gameState);

        if (!gameState.getCurrentPlayer().isInJail()) {
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have enough money to pay the fine! You will need to roll instead.", ButtonType.OK);
            alert.show();
        }
        gameState = connection.updateGameState();
    }

    private void jailGetOutFree(Stage window) throws IOException, InterruptedException {
        gameState = connection.updateGameState();
        connection.jailCard();
        update(gameState);
        if (gameState.getCurrentPlayer().isInJail()) {
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have a \"Get out of Jail FREE\" card...", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    protected void buildHouse() throws IOException, InterruptedException {
        if (playerNum != gameState.getTurn()){
            Alert notYourTurn = new Alert(Alert.AlertType.WARNING,"Its not your turn", ButtonType.OK);
            notYourTurn.show();
            return;
        }

        gameState = connection.updateGameState();
        ListView<String> listView = new ListView<>();
        ListView<String> prices = new ListView<>();
        ListView<String> numHouses = new ListView<>();
        listView.setPrefSize(150, 300);
        prices.setPrefSize(40, 300);
        numHouses.setPrefSize(40, 300);
        for (int i = 0; i < gameState.getCurrentPlayer().getPlayerProperties().size(); i++) {
            listView.getItems().add(gameState.getCurrentPlayer().getPlayerProperties().get(i).getName());
            prices.getItems().add("$" + String.valueOf(gameState.getCurrentPlayer().getPlayerProperties().get(i).getHouseCost()));
            numHouses.getItems().add(String.valueOf(gameState.getCurrentPlayer().getPlayerProperties().get(i).getNumberOfHouses()));
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prices.setMouseTransparent(true);
        prices.setFocusTraversable(false);
        numHouses.setMouseTransparent(true);
        numHouses.setFocusTraversable(false);

        Button button = new Button("Build House");
        button.setPrefSize(100, 100);
        button.setOnAction(e -> {
            try {
                buildHouseClicked(listView);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        buyHouseBox = new HBox(5, prices, listView, numHouses, button);
        buyHouseBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(buyHouseBox,350, 300);
        Stage window = new Stage();
        window.setTitle("Select a property");
        window.setScene(scene);
        window.show();
    }

    private void buyHouseBoxUpdate() {
        ListView<String> temp = new ListView<>();
        temp.setPrefSize(40, 300);
        for (int i = 0; i < gameState.getCurrentPlayer().getPlayerProperties().size(); i++) {
            temp.getItems().add(String.valueOf(gameState.getCurrentPlayer().getPlayerProperties().get(i).getNumberOfHouses()));
        }
        buyHouseBox.getChildren().set(2, temp);
    }

    private void buildHouseClicked(ListView<String> listView) throws IOException, InterruptedException {
        gameState = connection.updateGameState();
        String propertyName = listView.getSelectionModel().getSelectedItem();

        Property prop = null;
        if (propertyName == null) {
            Alert nothingSelected = new Alert(Alert.AlertType.WARNING, "Nothing is Selected!", ButtonType.OK);
            nothingSelected.show();
            return;
        }

        for (int i = 0; i < gameState.getCurrentPlayer().getPlayerProperties().size(); i++) {
            if (Objects.equals(gameState.getCurrentPlayer().getPlayerProperties().get(i).getName(), propertyName)) {
                prop = gameState.getCurrentPlayer().getPlayerProperties().get(i);
            }
        }
        if (prop == null) {
            Alert errorChoosingHouse = new Alert(Alert.AlertType.ERROR, "Unable to choose property " + propertyName, ButtonType.OK);
            errorChoosingHouse.show();
            return;
        }

        if (prop.getNumberOfHouses() == 3) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This property has the Max number of houses built!", ButtonType.OK);
            alert.show();
            return;
        }

        connection.buyHouse(prop);
        update(gameState);
        gameState = connection.updateGameState();

        buyHouseBoxUpdate();
    }

    @FXML
    protected void trade() {
        Stage window = new Stage();

        ListView<String> currPlayerProps = new ListView<>();
        for (int i = 0; i < gameC.state.getCurrentPlayer().getPlayerProperties().size(); i++) {
            currPlayerProps.getItems().add(gameC.state.getCurrentPlayer().getPlayerProperties().get(i).getName());
        }
        currPlayerProps.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Player p;
        if (gameC.state.getCurrentPlayer().getPlayerNumber() == 0) {
            p = gameC.state.getPlayerTwo();
        } else {
            p = gameC.state.getPlayerOne();
        }

        ListView<String> otherPlayerProps = new ListView<>();
        for (int i = 0; i < p.getPlayerProperties().size(); i++) {
            otherPlayerProps.getItems().add(p.getPlayerProperties().get(i).getName());
        }
        otherPlayerProps.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button tradeButton = new Button("Offer trade");
        tradeButton.setOnAction(e -> tradeClicked(window, currPlayerProps, otherPlayerProps, p));
        tradeButton.setPrefSize(100, 50);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(currPlayerProps, otherPlayerProps, tradeButton);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,600, 400);
        window.setTitle("Choose a property to trade");
        window.setScene(scene);
        window.show();
    }

    private void tradeClicked(Stage window, ListView<String> currPlayerProps, ListView<String> otherPlayerProps, Player p) {
        String prop1, prop2;
        prop1 = currPlayerProps.getSelectionModel().getSelectedItem();
        prop2 = otherPlayerProps.getSelectionModel().getSelectedItem();
        if (prop1 == null || prop2 == null) {
            Alert nothingSelected = new Alert(Alert.AlertType.WARNING, "You must select a property from each list!", ButtonType.OK);
            nothingSelected.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.NONE, "Do you confirm the trade " + prop1 + " for " + prop2 + "?", ButtonType.NO, ButtonType.YES);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Property currPlayerProp = null, otherPlayerProp = null, temp = null;

                for (int i = 0; i < gameC.state.getCurrentPlayer().getPlayerProperties().size(); i++) {
                    if (Objects.equals(gameC.state.getCurrentPlayer().getPlayerProperties().get(i).getName(), prop1)) {
                        currPlayerProp = gameC.state.getCurrentPlayer().getPlayerProperties().get(i);
                    }
                }

                for (int i = 0; i < p.getPlayerProperties().size(); i++) {
                    if (Objects.equals(p.getPlayerProperties().get(i).getName(), prop2)) {
                        otherPlayerProp = p.getPlayerProperties().get(i);
                    }
                }

                // Now switch the properties around
                temp = currPlayerProp;
                currPlayerProp = otherPlayerProp;
                otherPlayerProp = temp;

                update(gameC.state);
            }
            window.close();
        });
    }

    @FXML
    protected void diceClick() throws IOException, InterruptedException {
        gameState = connection.updateGameState();
        if (playerNum != gameState.getTurn()){
            Alert notYourTurn = new Alert(Alert.AlertType.WARNING,"Its not your turn", ButtonType.OK);
            notYourTurn.show();
            return;
        }

        System.out.println(gameState.getPlayerOne().getBank());
        if (gameState.getCurrentPlayer().isInJail()) {
            Alert inJail = new Alert(Alert.AlertType.WARNING, "You're in jail, you can't roll the dice to move!", ButtonType.OK);
            inJail.show();
            return;
        }
        if (!gameState.getHasRolledDice()) {
            int rand = ThreadLocalRandom.current().nextInt(1, 7);
            Image diceImage = new Image(getClass().getResourceAsStream("images/dice_" + rand + ".png"));
            dicePicture.setImage(diceImage);
            connection.sendDice(rand);
            gameState=connection.updateGameState();
            update(gameState);
            if(gameState.getPropertyAvailable() != null && gameState.getCurrentPlayer().getBank() > gameState.getPropertyAvailable().getCost()){
                Alert buyProperty = new Alert(Alert.AlertType.NONE, "Do you want to buy property "+gameState.getPropertyAvailable().getPosition()+", for $"+gameState.getPropertyAvailable().getCost()+"",ButtonType.OK, ButtonType.NO);

                buyProperty.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK){
                        try {
                            connection.buyProperty(gameState.getPropertyAvailable());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        try {
                            connection.denyProperty(gameState.getPropertyAvailable());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{
                try {
                    connection.denyProperty(gameState.getPropertyAvailable());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Just because the property is denied doesn't mean it's because the person can't afford it, error check this
                //Alert canNotAfford = new Alert(Alert.AlertType.NONE, "You are unable to afford this property. It will be auctioned off instead", ButtonType.OK);
                //canNotAfford.show();
            }

            update(gameState);
        }
        else {
            Alert alreadyRolledDice = new Alert(Alert.AlertType.WARNING, "YOU HAVE ALREADY ROLLED THE DICE THIS ROUND!!!", ButtonType.OK);
            alreadyRolledDice.show();
        }
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

    public boolean isInitialized() {
        return initialized;
    }
}