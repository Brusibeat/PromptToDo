package org.academiadecodigo.hashtronauts.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerListener implements Runnable {

    private Socket serverConnection;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public ServerListener(Socket socket) {
        try {
            this.serverConnection = socket;
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendToServer(String message) {
        if (serverConnection.isClosed()) {
            return false;
        }

        outputStream.println(message);
        return true;
    }

    @Override
    public void run() {

    }
}
