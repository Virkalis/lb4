module example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens example.demo to javafx.fxml;
    exports example.demo;
}
