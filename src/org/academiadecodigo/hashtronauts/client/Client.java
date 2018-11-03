package org.academiadecodigo.hashtronauts.client;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.hashtronauts.client.menus.MenuItems;
import org.academiadecodigo.hashtronauts.client.menus.Menus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private final Prompt prompt;
    private ServerListener serverListener;

    public Client() {
        this.prompt = new Prompt(System.in, System.out);
    }


    public boolean initClient(InetAddress host, int port) {
        try {
            serverListener = new ServerListener(new Socket(host, port));
        } catch (IOException e) {
            System.err.println(ClientMessages.CANT_CONNECT_HOST);
            return false;
        }

        return true;
    }

    public void start() {
        System.out.println(ClientMessages.WELCOME);

        MenuItems selectedMenu = Menus.getMainMenu(prompt);

        if (selectedMenu == MenuItems.EXIT) {
            System.out.println(ClientMessages.FAREWELL);
        }
    }
}
