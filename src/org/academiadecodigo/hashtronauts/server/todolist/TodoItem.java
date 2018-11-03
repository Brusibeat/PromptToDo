package org.academiadecodigo.hashtronauts.server.todolist;

public class TodoItem {

    private int itemID;
    private String itemValue;
    private boolean locked;
    private User lockedBy;


    /**
     * Constructs an instance of {@code TodoItem} with ID defined
     * @param itemID - The ID of the new item
     */
    public TodoItem(int itemID){
        this.itemID = itemID;
        this.itemValue = "";
        this.locked = false;
    }
    /**
     * Constructs an instance of {@code TodoItem} with both ID and value defined
     * @param itemID the ID of the new item
     * @param itemValue the value of the new item
     */
    public TodoItem(int itemID, String itemValue){
        this.itemID = itemID;
        this.itemValue = itemValue;
        this.locked = false;
    }

    /**
     * Fetches the ID of the item
     * @return an int value
     */
    public int getItemID(){
        return itemID;
    }

    /**
     * Fetches the text of the item
     * @return a String
     */
    public String getItemValue(){
        return itemValue;
    }

    /**
     * Checks if the item is currently locked
     * @return True if it is locked, or False if it is not locked
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Fetches the reference of the {@code User} that locked the item
     * @return a {@code User}, or null if it is not currently locked
     */
    public User getLockedBy(){
        if( isLocked() ){
            return lockedBy;
        }
        return null;
    }

    /**
     * Sets an item as locked or unlocked
     * @param lockState New state for the item (True locks the item, False unlocks the item)
     */
    public void setLocked(boolean lockState){
        locked = lockState;
    }

}
