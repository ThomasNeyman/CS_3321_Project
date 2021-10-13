package cs3321.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {

    @FXML
    Button connect, reset;

    @FXML
    TextField url;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connect.setOnAction(event -> {
            if(!url.getText().isEmpty()){
                Connection connection = new Connection();
                connection.initialize(url.getText());
                connection.createGet(url.getText());
                System.out.println(connection.test());
            }
        });
    }}

