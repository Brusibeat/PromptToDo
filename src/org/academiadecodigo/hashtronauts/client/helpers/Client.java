package org.academiadecodigo.hashtronauts.client.helpers;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.hashtronauts.client.menus.MenuItems;
import org.academiadecodigo.hashtronauts.client.menus.Menus;
import org.academiadecodigo.hashtronauts.client.others.ServerListener;
import org.academiadecodigo.hashtronauts.client.utils.ClientMessages;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    /**
     * Client Prompt
     */
    private final Prompt prompt;
    /**
     * Connection to the server
     */
    private ServerListener serverListener;

    public Client() {
        this.prompt = new Prompt(System.in, System.out);
    }

    /**
     * Tries to connect to server
     *
     * @param host host to connect
     * @param port port number
     * @return true if successful
     */
    public boolean initClient(InetAddress host, int port) {
        try {
            serverListener = new ServerListener(new Socket(host, port));
            new Thread(serverListener);
        } catch (IOException e) {
            System.err.println(ClientMessages.CANT_CONNECT_HOST);
            return false;
        }

        return true;
    }

    /**
     * Starts PromptToDo
     */
    public void start() {
        System.out.println(ClientMessages.WELCOME);

        MenuItems selectedMenu = Menus.getMainMenu(prompt);

        while (true) {
            switch (selectedMenu) {
                case EXIT:
                    System.out.println(ClientMessages.FAREWELL);
                    exitPromptTodo();
                    return;
                case LOGIN:
                    System.out.println("I want to Login!");
                    break;
                case REGISTER:
                    System.out.println("I want to Register!");
                    break;
            }
        }
    }

    /**
     * Closes the connection to the server
     */
    private void exitPromptTodo() {
        serverListener.close();
        System.exit(0);
    }
}
