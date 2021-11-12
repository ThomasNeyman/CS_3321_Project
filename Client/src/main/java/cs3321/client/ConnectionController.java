package cs3321.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {



    private Client app;


    @FXML
    Button connect, reset;

    @FXML
    TextField address, port;


    @FXML Scene scene;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connect.setOnAction(event -> {
            if(!address.getText().isEmpty() && !port.getText().isEmpty()){
                    Connection connection = Connection.instance();
                    connection.initialize(address.getText(), port.getText());
                    //connection.createGet(url.getText());
                    System.out.println(connection.test());
                    if (!connection.test()){
                        Alert badInput = new Alert(Alert.AlertType.WARNING, "Unable to connect, make sure your port and address are correct", ButtonType.OK);
                        badInput.show();
                    }/*else{
                        try {
                            app.showBoard();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }*/
            }else{
                Alert emptyInput = new Alert(Alert.AlertType.WARNING, "One or more fields is empty", ButtonType.OK);
                emptyInput.show();
            }
        });
    }}

