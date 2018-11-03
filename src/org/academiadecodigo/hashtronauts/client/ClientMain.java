package org.academiadecodigo.hashtronauts.client;

import java.net.InetAddress;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client();

        int port = ClientUtils.getPort();

        InetAddress host = ClientUtils.getHost();

        if (!client.initClient(host, port)) {
            return;
        }
        client.start();
    }
}
