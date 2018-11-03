package org.academiadecodigo.hashtronauts.client.menus;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.hashtronauts.client.ClientMessages;

public class Menus {

    public static MenuItems getMainMenu(Prompt prompt) {

        MenuItems[] menuItems = {
                MenuItems.LOGIN,
                MenuItems.REGISTER,
                MenuItems.EXIT
        };

        MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(menuItems));
        menu.setMessage(ClientMessages.MAIN_MENU_MESSAGE.toString());
        menu.setError(ClientMessages.MAIN_MENU_ERROR.toString());

        int item = prompt.getUserInput(menu);

        return menuItems[item - 1];
    }
}
