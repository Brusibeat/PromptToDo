package org.academiadecodigo.hashtronauts.client;

import org.academiadecodigo.hashtronauts.client.helpers.Client;
import org.academiadecodigo.hashtronauts.client.utils.ClientUtils;

import java.net.InetAddress;

public class ClientMain {


    /**
     * Client Entry Point
     *
     * @param args Arguments passed to PromptToDo
     */
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
