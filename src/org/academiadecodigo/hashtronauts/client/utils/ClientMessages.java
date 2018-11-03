package org.academiadecodigo.hashtronauts.client.utils;

/**
 * Messages visible to the user
 */
public enum ClientMessages {
    // Port Select
    SELECT_PORT("Please enter Server Port (default 8080): "),
    INVALID_PORT("Port number must be between 1 and 65535!"),

    //Host Select
    SELECT_HOST("Please enter a host to connect: "),
    INVALID_HOST("Invalid Host."),
    CANT_CONNECT_HOST("Can't connect to the server. Maybe it is down?"),

    //Connection
    WELCOME("Welcome to PromptToDo!"),

    //MainMenu
    MAIN_MENU_MESSAGE("Select an option: "),
    MAIN_MENU_ERROR("Wrong option number!"),
    FAREWELL("Leaving PromptToDo! ByeBye!"),

    //Login
    INVALID_CREDENTIALS("Invalid Credentials. Do you want to try again"),
    LOGIN_SUCCESS("You logged in successfully as "),
    ALREADY_LOGGEDIN("You already logged in as "),

    //Register
    REGISTER_SUCCESS("You registered successfully the username "),
    REGISTER_FAIL("You can't register the username "),

    //Others
    DISCONNECTED("Disconnected from the Server!"),
    ERROR_RECEIVING("An error occurred while receiving data form the server.");

    private final String message;

    ClientMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
