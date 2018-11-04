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
                MenuItems.LOGOUT,
                MenuItems.REGISTER,
                MenuItems.EXIT
        };

        MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(menuItems));
        menu.setMessage(ClientMessages.MAIN_MENU_MESSAGE.toString());
        menu.setError(ClientMessages.MAIN_MENU_ERROR.toString());

        int item = prompt.getUserInput(menu);

        return menuItems[item - 1];
    }

    /**
     * Displays Menu after logging in
     * @param prompt a prompt to show the Menu
     * @return The selected item from the menu
     */
    public static MenuItems getTodoListsMenu(Prompt prompt){
        MenuItems[] menuItems = {
                MenuItems.CREATE_TODO,
                MenuItems.CREATE_ITEM,
                MenuItems.EDIT_ITEM,
                MenuItems.LOGOUT
        };

        MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(menuItems));
        menu.setMessage(ClientMessages.MAIN_MENU_MESSAGE.toString());
        menu.setError(ClientMessages.MAIN_MENU_ERROR.toString());

        int item = prompt.getUserInput(menu);

        return menuItems[item - 1];
    }
}
