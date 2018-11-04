package org.academiadecodigo.hashtronauts.server.clients;

import org.academiadecodigo.hashtronauts.server.Server;
import org.academiadecodigo.hashtronauts.server.users.User;


/**
 * Class to connect a client and a server with minimum interaction
 */
public class ClientConnector {

    private final Server server;
    private final Client client;

    public ClientConnector(Server server, Client client) {
        this.server = server;
        this.client = client;
    }


    /**
     * Disconnects this client
     */
    public void disconnect() {
        client.disconnect();
    }

    public User login(String user, int password) {
        return server.LoginUser(user, password);
    }

    public User register(String username, int password) {
        return server.registerUser(username, password);
    }

    public boolean createList(String name) {
        return server.createList(name);
    }

    public boolean getList(String name) {
        return server.getList(name);
    }

    public boolean createItem(String listName, String itemText) {
        return server.createItem(listName, itemText);
    }
}
