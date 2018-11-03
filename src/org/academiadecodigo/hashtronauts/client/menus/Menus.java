package org.academiadecodigo.hashtronauts.client.menus;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.hashtronauts.client.utils.ClientMessages;

public class Menus {

    /**
     * Displays the Main menu
     *
     * @param prompt a prompt to show the Menu
     * @return The menu entry Selected (LOGIN/REGISTER/EXIT)
     * @see MenuItems for possible Items
     */
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
