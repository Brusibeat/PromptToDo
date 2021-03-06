package org.academiadecodigo.hashtronauts.server.todolist;

import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.utils.Utils;

import java.util.Date;

public class TodoItem {

    private int itemID;
    private String itemValue;
    private boolean locked;
    private User editedBy;
    private Date editedDate;
    private boolean todoDone;

    /**
     * Constructs an instance of {@code TodoItem} with all parameters received
     * @param itemID - Id of the new Item
     * @param itemValue - Value of the new item
     * @param user - A reference of the user that created the item
     * @param date - The date the item was created
     */
    public TodoItem(int itemID, String itemValue, User user, Date date, boolean todoDone){
        this.itemID = itemID;
        this.itemValue = itemValue;
        this.locked = false;
        this.editedBy = user;
        this.editedDate = date;
        this.todoDone = todoDone;
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
    public synchronized String getItemValue(){
        return itemValue;
    }

    /**
     * Fetches the locked state of the item
     * @return True if it is locked, or False if it is not locked
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Fetches the reference of the {@code User} that last edited the item
     * @return a reference of {@code User}
     */
    public User getEditedBy(){
        return editedBy;
    }

    /**
     * Fetches the date of the item's latest edit
     * @return a reference of {@code Date}
     */
    public Date getEditedDate(){
        return editedDate;
    }

    public boolean isDone(){
        return todoDone;
    }

    /**
     * Edits the item's value
     * @param value - The new value for this item
     */
    public synchronized void setItemValue(String value){
        itemValue = value;
    }

    /**
     * Defines the user and time of the latest change to the item's value. This method should be called whenever the
     * value of the item is changed, to ensure accurate information of its change
     * @param editor - A reference of the {@code User} that last changed the value
     * @param date - A reference of the {@code Date} that states the time it was changed
     */
    public void setEdited(User editor, Date date){
        this.editedBy = editor;
        this.editedDate = date;
    }

    /**
     * Sets an item as locked or unlocked
     * @param lockState New state for the item (True locks the item, False unlocks the item)
     */
    public void setLocked(boolean lockState){
        locked = lockState;
    }

    public synchronized void setDone(boolean state, User editor){
        editedBy = editor;
        editedDate = new Date();
        todoDone = state;
    }

    @Override
    public String toString(){
        if (editedBy == null) {
            editedBy = new User(0,"unknown",0);
        }

        return String.format("%d:%s:%s:%s:%s", itemID, editedBy.getUsername(), Utils.getFormattedDate(editedDate), itemValue, Boolean.toString(todoDone));
    }

}
