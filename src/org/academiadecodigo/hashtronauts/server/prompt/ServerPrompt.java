package org.academiadecodigo.hashtronauts.server.prompt;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.hashtronauts.server.Server;
import org.academiadecodigo.hashtronauts.server.menus.MenuItems;
import org.academiadecodigo.hashtronauts.server.menus.Menus;

/**
 * Server Prompt for local control
 */
public class ServerPrompt implements Runnable {

    private final Server server;

    public ServerPrompt(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        Prompt prompt = new Prompt(System.in, System.out);

        MenuItems menuItem = Menus.getMainMenu(prompt);

        if (menuItem == MenuItems.SHUTDOWN) {
            server.shutdown();
            return;
        }
    }
}
