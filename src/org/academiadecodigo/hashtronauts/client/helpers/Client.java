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
    /** Connection to the server */
    private ServerListener serverListener;

    /** User Username */
    private String username;

    /**
     * Current T0d0
     */
    private String todoTitle;

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
                case LOGIN:
                    if (username == null) {
                        username = loginUser();
                    }

                    if (username != null) {
                        showUserMenu();
                    }
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case EXIT:
                    System.out.println(ClientMessages.FAREWELL);
                    exitPromptTodo();
                    return;
            }
        }
    }

    /**
     * Shows the User Menu to the logged in user
     */
    private void showUserMenu() {
        while (true) {
            MenuItems selectedMenu = Menus.getUserMenu(prompt);

            switch (selectedMenu) {
                case JOIN_LIST:
                    if (todoTitle == null) {
                        todoTitle = getList();
                    }

                    if (todoTitle != null) {
                        showTodoListMenu();
                    }
                    break;
                case CREATE_LIST:
                    todoTitle = createTodo();

                    if (todoTitle != null) {
                        showTodoListMenu();
                    }
                    break;
                case LOGOUT:
                    logoutUser();
                    return;
            }
        }
    }


    /**
     * Shows the t0d0 list menu
     */
    public void showTodoListMenu() {
        while(true){
            MenuItems selectedMenu = Menus.getTodoListsMenu(prompt);

            switch(selectedMenu){
                case CREATE_ITEM:
                    createItem();
                case EDIT_ITEM:
                    editItem();
                case LOGOUT:
                    logoutUser();
                    break;
            }
        }
    }

    /** Gets list from server */
    private String getList() {
        String title = getListTitleScanner();

        serverListener.sendToServer(Communication.buildMessage(Communication.Command.GET_LIST, new String[]{title}));

        String message;
        try {
            message = serverListener.receiveFromServer();
        } catch (IOException e) {
            return null;
        }

        if (Communication.getMethodFromMessage(message) == Communication.Method.ACK &&
            Communication.getCommandFromMessage(message) == Communication.Command.GET_LIST) {
            return title;
        }

        return null;
    }

    /** Creates a new T0d0 list */
    private String createTodo() {
        String title = getListTitleScanner();

        serverListener.sendToServer(Communication.buildMessage(Communication.Command.CREATE_LIST, new String[]{title}));

        return title;
    }

    /** Edits a item */
    private void editItem() {

    }

    /** Creates a t0d0 item */
    private void createItem() {
        StringInputScanner todoItemScanner = new StringInputScanner();
        todoItemScanner.setMessage("Todo Item description: ");

        String itemValue = prompt.getUserInput(todoItemScanner);

        serverListener.sendToServer(Communication.buildMessage(Communication.Command.CREATE_ITEM, new String[]{itemValue}));
    }

    /** PromptView Scanner to get List ID */
    private String getListTitleScanner() {
        StringInputScanner todoListId = new StringInputScanner();
        todoListId.setMessage("List ID: ");

        String todoId = prompt.getUserInput(todoListId);

        return todoId;
    }


    /** Closes the connection to the server */
    private void exitPromptTodo() {
        serverListener.close();
        System.exit(0);
    }

    /** Handles user login */
    private String loginUser() {
        Credentials credentials = runCredentialsPrompt();

        if (!UserHelper.sendCommand(serverListener, Communication.Command.LOGIN, credentials.username, credentials.password)) {
            if (runConfirmationPrompt(ClientMessages.INVALID_CREDENTIALS.toString())){
                return loginUser();
            } else {
                return null;
            }
        }

        System.out.println(ClientMessages.LOGIN_SUCCESS + credentials.username);

        return credentials.username;
    }

    /** Handles user registration on the server */
    private void registerUser() {
        Credentials credentials = runCredentialsPrompt();

        if (!UserHelper.sendCommand(serverListener, Communication.Command.REGISTER, credentials.username, credentials.password)) {
            System.out.println(ClientMessages.REGISTER_FAIL.toString() + credentials.username);

            if (runConfirmationPrompt(ClientMessages.INVALID_CREDENTIALS.toString())){
                registerUser();
            }
        } else {
            System.out.println(ClientMessages.REGISTER_SUCCESS + credentials.username);
        }
    }

    /**
     * Fake user logout, just set the username to null
     */
    private void logoutUser() {
        username = null;
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
        confirmationScanner.setMessage(message);

        return prompt.getUserInput(confirmationScanner).toLowerCase().equals("y");
    }

    /** Simple credential info (username, password) */
    private class Credentials {
        String username;
        String password;
    }
}
