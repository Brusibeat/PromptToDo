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

        MenuInputScanner menu = createMenu(menuItems, ClientMessages.MAIN_MENU_MESSAGE, ClientMessages.MAIN_MENU_ERROR);

        int item = prompt.getUserInput(menu);

        return menuItems[item - 1];
    }

    /**
     * Displays the User Menu
     *
     * @param prompt a prompt to show the Menu
     * @return The menu entry Selected (JOIN_LIST, CREATE_LIST, LOGOUT)
     * @see MenuItems for possible Items
     */
    public static MenuItems getUserMenu(Prompt prompt) {

        MenuItems[] menuItems = {
                MenuItems.JOIN_LIST,
                MenuItems.CREATE_LIST,
                MenuItems.LOGOUT
        };

        MenuInputScanner menu = createMenu(menuItems, ClientMessages.MAIN_MENU_MESSAGE, ClientMessages.MAIN_MENU_ERROR);

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
                MenuItems.CREATE_ITEM,
                MenuItems.EDIT_ITEM,
                MenuItems.BACK
        };

        MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(menuItems));
        menu.setMessage(ClientMessages.MAIN_MENU_MESSAGE.toString());
        menu.setError(ClientMessages.MAIN_MENU_ERROR.toString());

        int item = prompt.getUserInput(menu);

        return menuItems[item - 1];
    }

    private static MenuInputScanner createMenu(MenuItems[] items, ClientMessages menuMessage, ClientMessages errorMessage) {
        MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(items));
        menu.setMessage(menuMessage.toString());
        menu.setError(errorMessage.toString());

        return menu;
    }
}
