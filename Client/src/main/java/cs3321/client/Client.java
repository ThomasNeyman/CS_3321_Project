package cs3321.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Client extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        stage.setTitle("Monopoly");
        ClientController control = fxmlLoader.getController();

        stage.setScene(scene);
        stage.show();


    }
    public void showBoard(){

    }

    public static void main(String[] args) {
        launch();
    }
}