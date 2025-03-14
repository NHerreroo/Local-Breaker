package org.example.localbreaker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestThread implements Runnable {
    private URL url;
    private volatile boolean running = true; // Variable de control

    public RequestThread(URL url) {
        this.url = url;
    }

    public void stopRunning() {
        this.running = false; // Detiene el bucle en `run()`
    }

    public void startRunning(){
        this.running = true;
    }

    public void sendRequest() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String jsonInputString = "{\"data\": \"test\"}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        conn.getResponseCode();
        conn.disconnect();
    }

    @Override
    public void run() {
        int i = 0;
        while (running) { // Verifica si debe continuar ejecutándose
            try {
                sendRequest();
            } catch (IOException e) {
                System.err.println("Error en la solicitud: " + e.getMessage());
            }
            System.out.println("Request " + i + " en hilo " + Thread.currentThread().getId());
            i++;
        }
        System.out.println("Hilo detenido: " + Thread.currentThread().getId());
    }

    public String requestString(String msg){
        return msg;
    }
}
