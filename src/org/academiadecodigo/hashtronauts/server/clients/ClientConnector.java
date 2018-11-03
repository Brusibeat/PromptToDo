package org.academiadecodigo.hashtronauts.server.clients;

import org.academiadecodigo.hashtronauts.server.Server;


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
}
