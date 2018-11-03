package org.academiadecodigo.hashtronauts.client.helpers;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;
import org.academiadecodigo.hashtronauts.client.menus.MenuItems;
import org.academiadecodigo.hashtronauts.client.menus.Menus;
import org.academiadecodigo.hashtronauts.client.others.ServerListener;
import org.academiadecodigo.hashtronauts.client.utils.ClientMessages;
import org.academiadecodigo.hashtronauts.comms.Communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Client {

    /**
     * Client Prompt
     */
    private final Prompt prompt;
    /**
     * Connection to the server
     */
    private ServerListener serverListener;
    /**
     * user logged status
     */
    private String username;

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

        while (true) {
            MenuItems selectedMenu = Menus.getMainMenu(prompt);

            switch (selectedMenu) {
                case EXIT:
                    System.out.println(ClientMessages.FAREWELL);
                    exitPromptTodo();
                    return;
                case LOGIN:
                    if (username == null) {
                       username = loginUser();
                    } else {
                        System.out.println(ClientMessages.ALREADY_LOGGEDIN.toString() + username);
                    }
                    break;
                case LOGOUT:
                    logoutUser();
                    break;
                case REGISTER:
                    registerUser();
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

    /**
     * Handles user login
     */
    private String loginUser() {
        Credentials credentials = runCredentialsPrompt();

        if (!UserHelper.sendCommand(serverListener, Communication.Command.LOGIN, credentials.username, credentials.username)) {
            if (runConfirmationPrompt(ClientMessages.INVALID_CREDENTIALS.toString())){
                return loginUser();
            } else {
                return null;
            }
        }

        System.out.println(ClientMessages.LOGIN_SUCCESS + username);

        return username;
    }

    /**
     * Fake user logout, just set the username to null
     */
    private void logoutUser() {
        username = null;
    }

    /**
     * Handles user registration on the server
     */
    private void registerUser() {
        Credentials credentials = runCredentialsPrompt();

        if (!UserHelper.sendCommand(serverListener, Communication.Command.REGISTER, credentials.username, credentials.username)) {
            System.out.println(ClientMessages.REGISTER_FAIL.toString() + credentials.username);

            if (runConfirmationPrompt(ClientMessages.INVALID_CREDENTIALS.toString())){
                registerUser();
            }
        } else {
            System.out.println(ClientMessages.REGISTER_SUCCESS + credentials.username);
        }
    }


    /**
     * Ask the user for username and password
     * @return credentials with username and password
     */
    private Credentials runCredentialsPrompt() {
        StringInputScanner usernameScanner = new StringInputScanner();
        usernameScanner.setMessage("Username: ");

        PasswordInputScanner passwordScanner = new PasswordInputScanner();
        passwordScanner.setMessage("Password: ");

        Credentials credentials = new Credentials();
        credentials.username = prompt.getUserInput(usernameScanner);
        credentials.password = prompt.getUserInput(passwordScanner);

        return credentials;
    }

    /**
     * Asks yes or no question
     * @param message question to ask the user
     * @return
     */
    private boolean runConfirmationPrompt(String message) {
        Set<String> options = new HashSet<>();
        options.add("Y");
        options.add("y");
        options.add("N");
        options.add("n");

        StringSetInputScanner confirmationScanner = new StringSetInputScanner(options);
        confirmationScanner.setMessage(message + "? (Y/N)");

        return prompt.getUserInput(confirmationScanner).toLowerCase().equals("y");
    }

    /**
     * Simple credential info (username, password)
     */
    private class Credentials {
        String username;
        String password;
    }
}
