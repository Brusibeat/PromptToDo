package org.academiadecodigo.hashtronauts.server.clients;

import org.academiadecodigo.hashtronauts.comms.Communication;
import org.academiadecodigo.hashtronauts.server.utils.ServerMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents an client itself
 */
public class Client implements Runnable {

    /**
     * Connection to the client
     */
    private final Socket clientSocket;
    /**
     * Client Streams
     */
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    /**
     * Connection to the server
     */
    private ClientConnector serverBridge;

    public Client(Socket socket) {
        this.clientSocket = socket;

        try {
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates if Client connected successfully
     *
     * @return true if connected
     */
    public boolean clientConnected() {
        return !clientSocket.isClosed() && (outputStream != null && inputStream != null);
    }

    public void setServerBridge(ClientConnector serverBridge) {
        this.serverBridge = serverBridge;
    }

    /**
     * Client Thread Entry Point
     */
    @Override
    public void run() {
        while (!clientSocket.isClosed()) {
            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ServerMessages.CLIENT_DISCONNECTED);
    }

    /**
     * Sends a message to the client
     *
     * @param text message
     * @return true if successful
     */
    public boolean sendToClient(String text) {
        if (clientSocket.isClosed()) {
            return false;
        }

        outputStream.println(text);
        return true;
    }

    /**
     * Disconnects this client
     */
    public void disconnect() {
        sendToClient(Communication.buildMessage(Communication.Command.SHUTDOWN, null));
        try {
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}
