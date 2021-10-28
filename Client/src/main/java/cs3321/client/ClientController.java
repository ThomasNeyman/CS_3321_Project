package cs3321.client;

import Game.Monopoly;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import Game.State;
import Game.Monopoly;

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








    public ClientController() {

    }

    // @FXML
    /*protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/
    @FXML
    protected void diceClick() {
        int rand = ThreadLocalRandom.current().nextInt(1, 7);
        Image diceImage = new Image(getClass().getResourceAsStream("images/dice_" + rand + ".png"));
        dicePicture.setImage(diceImage);

    }

    protected void init() {
        Monopoly game = new Monopoly();
        game.updatePlayerPosition(3);
        game.updatePlayerProperty(game.getGameState().getPropertyAvailable(), game.getGameState().getTurn());
        game.updatePlayerPosition(3);
        game.updatePlayerPosition(2);
        game.updatePlayerProperty(game.getGameState().getPropertyAvailable(), game.getGameState().getTurn());

        update(game.getGameState());
    }

    private void update(State gameState){
        //defines color of each rectangle, blue for playerone, red for playertwo, nad grey for the rest
        Rectangle[] s = {s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s21,s21,s22,s23,s24,s25,s26,s27};
        //creates a gradient if both players are on the same space
        if(gameState.getPlayerOne().getPosition()==gameState.getPlayerTwo().getPosition()){
            for(int i=0; i<27;i++){
                if(gameState.getPlayerTwo().getPosition()==i){
                    Stop[] stops = new Stop[]{new Stop(0,Color.BLUE),new Stop(1,Color.RED)};
                    LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE,stops);
                    s[i].setFill(gradient);
                }else{
                    s[i].setFill(Color.LIGHTGREY);
                }
            }
            //if on seperate spaces colors each block
        }else{
            for(int i=0; i<27;i++){
                if(gameState.getPlayerOne().getPosition()==i){
                    s[i].setFill(Color.BLUE);
                }else if(gameState.getPlayerTwo().getPosition()==i){
                    s[i].setFill(Color.RED);
                }
                else{
                    s[i].setFill(Color.LIGHTGREY);
                }
            }
            s[gameState.getPlayerOne().getPosition()].setFill(Color.BLUE);
            s[gameState.getPlayerTwo().getPosition()].setFill(Color.RED);
        }
        //Set color of properties owned by player
        Rectangle[] property = {s2,s3,s5,s6,s9,s10,s12,s13,s16,s17,s19,s20,s23,s24,s26,s27};
        for (int i =0;i< property.length;i++){
            for(int j = 0;j<gameState.getPlayerOne().getPlayerProperties().size();j++){
                if(gameState.getPlayerOne().getPlayerProperties().get(j).getPosition() == i && s[gameState.getPlayerOne().getPosition()] != property[i]  && s[gameState.getPlayerTwo().getPosition()] != property[i] ){
                    property[3].setFill(Color.LIGHTBLUE);
                }
            }
            for(int j = 0;j<gameState.getPlayerTwo().getPlayerProperties().size();j++){
                if(gameState.getPlayerTwo().getPlayerProperties().get(j).getPosition() == i && s[gameState.getPlayerOne().getPosition()] != property[i] && gameState.getPlayerTwo().getPosition() !=i){
                    property[i].setFill(Color.PINK);
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
        p1gojfc.setText(""+gameState.getPropertyAvailable());
        if(gameState.getPlayerOne().getPlayerProperties().get(1) != null){
            p1gojfc.setText(""+gameState.getPlayerOne().getPlayerProperties().get(1).getPosition());
        }
        if(gameState.getPlayerOne().isHasGOJFC()){
            p1gojfc.setText("Get Out of Jail");
        }
        if(gameState.getPlayerTwo().isHasGOJFC()){
            p2gojfc.setText("Get Out of Jail");
        }
        turn.setText("Player "+(gameState.getTurn()+1)+"'s Turn");
        //sets color of dice
        Image diceImage = new Image(getClass().getResourceAsStream("images/dice_" + gameState.getDiceValue() + ".png"));
        dicePicture.setImage(diceImage);

    }


}