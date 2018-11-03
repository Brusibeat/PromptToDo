package org.academiadecodigo.hashtronauts.client.others;

import org.academiadecodigo.hashtronauts.client.utils.ClientMessages;

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

    /**
     * Sends a message to the server
     *
     * @param message message to be sent
     * @return true if successful
     */
    public boolean sendToServer(String message) {
        if (serverConnection.isClosed()) {
            return false;
        }

        outputStream.println(message);
        return true;
    }

    public String receiveFromServer() throws IOException {
        StringBuilder sb = new StringBuilder();
        String message;

        while ((message = inputStream.readLine()) != null && !message.isEmpty()) {
            sb.append(message);
        }

        return sb.toString();
    }

    /**
     * New Thread listenConnections point (Blank for now)
     */
    @Override
    public void run() {
    }

    /**
     * Closes the socket
     */
    public void close() {
        synchronized (this) {
            try {
                if (!serverConnection.isClosed()) {
                    serverConnection.close();
                    System.out.println(ClientMessages.DISCONNECTED);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
