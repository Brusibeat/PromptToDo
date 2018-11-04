package org.academiadecodigo.hashtronauts.client.menus;

/**
 * Contains all the Items for the menus
 */
public enum MenuItems {
    //Main Menu
    LOGIN("Login"),
    REGISTER("Register"),
    EXIT("Exit"),


    //User Menu
    JOIN_LIST("Join a List"),
    CREATE_LIST("Create new List"),
    LOGOUT("Logout"),

    //T0d0 Menu
    CREATE_ITEM("Create a Todo Item"),
    EDIT_ITEM("Edit a Todo Item"),
    LIST_ITEMS("List all items"),

    //General
    BACK("Back");

    /**
     * The shown description at the menu
     */
    private final String itemDescription;

    MenuItems(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * Gets all the descriptions of an MenuItems[]
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
