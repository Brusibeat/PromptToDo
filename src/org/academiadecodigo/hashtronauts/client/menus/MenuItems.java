package org.academiadecodigo.hashtronauts.client.menus;

public enum MenuItems {
    LOGIN("Login"),
    REGISTER("Register"),
    LOGOUT("Logout"),
    EXIT("Exit");

    private String itemDescription;

    MenuItems(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public static String[] getItemsDescription(MenuItems[] menuItems) {
        String[] itemsDescription = new String[menuItems.length];

        for (int i = 0; i < menuItems.length; i++) {
            itemsDescription[i] = menuItems[i].itemDescription;
        }

        return itemsDescription;
    }
}
