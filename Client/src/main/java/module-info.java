module cs3321.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    requires Game;
    requires com.google.gson;

    opens cs3321.client to javafx.fxml;
    exports cs3321.client;
}