module org.example.localbreaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.localbreaker to javafx.fxml;
    exports org.example.localbreaker;
}