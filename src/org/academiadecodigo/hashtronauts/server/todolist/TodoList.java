package org.academiadecodigo.hashtronauts.server.todolist;

import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.utils.Utils;

import java.util.Date;
import java.util.HashMap;

public class TodoList {

    private String id;
    private HashMap<Integer, TodoItem> items;
    private User owner;

    /**
     * Constructs a new instance of {@code TodoList}, with a given ID and reference of the owner
     * @param id - The ID of the list
     */
    public TodoList(String id){
        this.id = id;
        this.owner = null;

        initList();
    }

    /**
     * Constructs a new instance of {@code TodoList}, with a given ID and reference of the owner
     * @param id - The ID of the list
     * @param owner - A reference to the {@code User} that owns the list
     */
    public TodoList(String id, User owner){
        this.id = id;
        this.owner = owner;

        initList();
    }

    /**
     * Initializes the HashMap property of the list to be ready for use. This method is called by the constructor
     * whenever an object of this class is initialized
     */
    private void initList(){
        items = new HashMap<>();
    }

    /**
     * Lock an item on the list that is being edited
     * @param id - the id of the item
     */
    public void lockItem(int id){
        items.get(id).setLocked(true);
    }

    /**
     * Unlock an item on the list that is not being edited
     * @param id - the id of the item
     */
    public void unlockItem(int id){
        items.get(id).setLocked(false);
    }

    /**
     * Checks if an item is currently being edited
     * @param id - the id of the item
     * @return True if the item is being edited, false if it's not being edited.
     */
    public boolean canEdit(int id){
        if( items.get(id).isLocked() ){
            return false;
        }
        return true;
    }

    /**
     * Fetches the ID of the list
     * @return an int value
     */
    public String getId(){
        return id;
    }

    /**
     * Fetches all the items in the list
     * @return a HashMap containing all the items' ID and value
     */
    public HashMap<Integer, TodoItem> getItems(){
        return items;
    }

    /**
     * Fetches a reference of the list's owner
     * @return {@code owner}
     */
    public User getOwner(){
        return owner;
    }

    /**
     * Fetches an item in the list, by ID
     * @param itemId - The ID of the requested item
     * @return a {@code TodoItem} with the requested ID
     */
    public TodoItem getItem(int itemId){
        return items.get(itemId);
    }

    /**
     * Create a new T0D0 item, with the received parameters
     * @param id - ID of the item
     * @param value - the value of the item
     * @param user - the user who created the item
     * @param date - the date it was created
     * @return the created item
     */
    public TodoItem createItem(int id, String value, User user, Date date, boolean todoDone){
        TodoItem newItem = new TodoItem(id, value, user, date, todoDone);

        items.put(id, newItem);

        return newItem;
    }

    /**
     * Fetches the values of all items contained on the list
     * @return a String array containing all items' values
     */
    public String[] getAllItems(){
        String[] itemList = new String[items.size()];
        int i = 0;
        for( TodoItem item : items.values()){
            itemList[i] = item.toString();
            i++;
        }
        return itemList;
    }

    public void updateItem(int id, String value, User user, Date date) {
        items.get(id).setItemValue(value);
        items.get(id).setEdited(user, date);
    }

}
