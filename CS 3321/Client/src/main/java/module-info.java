module cs3321.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens cs3321.client to javafx.fxml;
    exports cs3321.client;
}