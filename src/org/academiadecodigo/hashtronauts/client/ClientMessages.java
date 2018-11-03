package org.academiadecodigo.hashtronauts.client;

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
    FAREWELL("Leaving PromptToDo! ByeBye!");

    private String message;

    ClientMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
