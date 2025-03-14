package org.example.localbreaker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onExecuteButtonClick(ActionEvent actionEvent) throws MalformedURLException {
        startAttack();
    }

    public void startAttack() throws MalformedURLException {
        URL url = new URL("http://localhost:3000");

        int numThreads = 5000;

        for (int i = 0; i < numThreads; i++) {
            RequestThread threadTest = new RequestThread(url);
            Thread thread = new Thread(threadTest);
            thread.start();
        }
    }

    public void onStopButtonClick(ActionEvent actionEvent) {
        Platform.exit();
    }
}