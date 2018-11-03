package org.academiadecodigo.hashtronauts.server.utils;

/**
 * Messages visible to the server admin
 */
public enum ServerMessages {
    // Server Start
    SELECT_PORT("Please enter Server Port (default 8080): "),
    INVALID_PORT("Port number must be between 1 and 65535!"),
    CANT_START("Could not start the server, exiting..."),

    // Server Listening
    START_LISTEN("Listening for clients!"),
    ERROR_ACCEPTING_CLIENT("Could not accept a client!"),
    CLIENT_CONNECTED("New Client Connected!"),

    //Clients
    CLIENT_DISCONNECTED("Client Disconnected!"),

    //Server Prompt
    MAIN_MENU_MESSAGE("Select an option: "),
    MAIN_MENU_ERROR("Wrong option number!"),

    //Server
    SERVER_CLOSING("Server, shutting down...");

    private final String message;

    ServerMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
