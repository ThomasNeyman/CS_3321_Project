package cs3321.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class ClientController {

    @FXML
    private ImageView dicePicture;

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
}