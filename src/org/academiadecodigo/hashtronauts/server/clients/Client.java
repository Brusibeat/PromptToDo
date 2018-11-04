package org.academiadecodigo.hashtronauts.server.clients;

import org.academiadecodigo.hashtronauts.comms.Communication;
import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.utils.ServerMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import static org.academiadecodigo.hashtronauts.comms.Communication.*;

/**
 * Represents an client itself
 */
public class Client implements Runnable {

    /** Connection to the client */
    private final Socket clientSocket;

    /** Client Streams */
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    /** Connection to the server */
    private ClientConnector serverBridge;

    /** Associated user to this client */
    private User user;

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
                String message = receiveFromClient();

                if (message == null) {
                    break;
                }

                handleInput(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ServerMessages.CLIENT_DISCONNECTED);

    }


    /**
     * Handles messages received from client
     *
     * @param message the message received
     */
    private void handleInput(String message) {

        if (!isValid(message)) {
            return;
        }

        Method method = Communication.getMethodFromMessage(message);

        Command command = Communication.getCommandFromMessage(message);

        String[] args = message.split(" ")[2].split(",");

        String response;
        if (method == Method.POST) {
            switch (command) {
                case LOGIN:
                    user = serverBridge.login(args[0], args[1].hashCode());
                    response = "true";
                    if (user == null ){
                        response = "false";
                    }
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[]{response}));

                    break;
                case REGISTER:
                    user = serverBridge.register(args[0], args[1].hashCode());
                    response = "true";
                    if (user == null) {
                        response = "false";
                    }
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[]{response}));
                    break;
                case CREATE_LIST:
                    Boolean createResult = serverBridge.createList(args[0]);
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[] {createResult.toString()}));
                    break;
                case CREATE_ITEM:
                    Boolean createItemResult = serverBridge.createItem(args[0], args[1], user, new Date());
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[]{createItemResult.toString()}));
                    break;
                case EDIT_ITEM:
                    String updatedItemValue = serverBridge.updateItem(args[0], Integer.valueOf(args[1]), args[2],
                            Boolean.parseBoolean(args[3]), user, new Date());
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[]{updatedItemValue}));
                    break;
            }
        }

        if (method == Method.GET) {
            switch (command) {
                case GET_LIST:
                    Boolean getResult = serverBridge.getList(args[0]);
                    sendToClient(Communication.buildMessage(Command.RESPONSE, new String[] {getResult.toString()}));
                    break;
                case LIST_ITEMS:
                    String[] items = serverBridge.getTodoList(args[0]).getAllItems();
                    sendToClient(Communication.buildMessage(Command.RESPONSE, items));
                    break;
            }
        }
    }

    /**
     * Gets a message from the client
     *
     * @return client message
     */
    public String receiveFromClient() throws IOException {
        StringBuilder sb = new StringBuilder();
        String message;

        try {
            while ((message = inputStream.readLine()) != null && !message.isEmpty()) {
                sb.append(message);
            }
        } catch (SocketException ex) {
            return null;
        }

        if (message == null) {
            return null;
        }

        return sb.toString();
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
        sendToClient(buildMessage(Command.SHUTDOWN, null));
        try {
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}
