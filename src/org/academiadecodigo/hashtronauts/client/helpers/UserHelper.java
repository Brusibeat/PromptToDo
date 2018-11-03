package org.academiadecodigo.hashtronauts.client.helpers;

import org.academiadecodigo.hashtronauts.client.others.ServerListener;
import org.academiadecodigo.hashtronauts.client.utils.ClientMessages;
import org.academiadecodigo.hashtronauts.comms.Communication;

import java.io.IOException;

public class UserHelper {

    /**
     * Send user commands to the server (Login, Register)
     * @param server
     * @param command
     * @param username
     * @param password
     * @return
     */
    public static boolean sendCommand(ServerListener server, Communication.Command command, String username, String password) {
        String receivedMessage = "";
        String[] args = {username, password};

        server.sendToServer(Communication.buildMessage(Communication.Command.LOGIN, args));

        try {
            receivedMessage = server.receiveFromServer();
        } catch (IOException e) {
            System.out.println(ClientMessages.ERROR_RECEIVING);
        }

        return Boolean.valueOf(receivedMessage);
    }
}
