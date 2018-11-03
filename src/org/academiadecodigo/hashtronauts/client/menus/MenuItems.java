package org.academiadecodigo.hashtronauts.client.menus;

/**
 * Contains all the Items for the menus
 */
public enum MenuItems {
    //Main Menu
    LOGIN("Login"),
    LOGOUT("Logout"),
    REGISTER("Register"),
    EXIT("Exit");


    /**
     * The shown description at the menu
     */
    private final String itemDescription;

    MenuItems(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Gets all the descriptions of an MenuItems[]
     * Usage ex:
     *  MenuInputScanner menu = new MenuInputScanner(MenuItems.getItemsDescription(someArr[]))
     *
     *
     * @param menuItems the MenuItem[]
     * @return all the descriptions
     */
    public static String[] getItemsDescription(MenuItems[] menuItems) {
        String[] itemsDescription = new String[menuItems.length];

        for (int i = 0; i < menuItems.length; i++) {
            itemsDescription[i] = menuItems[i].itemDescription;
        }

        return itemsDescription;
    }
}
