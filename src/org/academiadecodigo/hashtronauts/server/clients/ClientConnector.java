package org.academiadecodigo.hashtronauts.server.clients;

import org.academiadecodigo.hashtronauts.server.Server;

public class ClientConnector {

    private final Server server;
    private final Client client;

    public ClientConnector(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    public void disconnect() {
        client.disconnect();
    }
}
