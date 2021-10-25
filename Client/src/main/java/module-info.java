module cs3321.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;


    opens cs3321.client to javafx.fxml;
    exports cs3321.client;
}