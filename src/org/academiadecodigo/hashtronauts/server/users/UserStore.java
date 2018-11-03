package org.academiadecodigo.hashtronauts.server.users;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/** Stores the Logged Users && manages the user Database */
public class UserStore {

    public static final String USERS_PATH = "/resources/users/";
    public static final String USERS_INFO_PATH = "/resources/users/userInfo.txt";

    private List<User> users;
    private int nextID;

    public UserStore() {
        this.users = new LinkedList<>();
    }

    /**
     * Gets a Logged User
     *
     * @param username
     * @return
     */
    public User getUser(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }


    /**
     * Gets a Logged user
     *
     * @see UserStore#getUser(String) Overload method
     * @param userID
     * @return
     */
    public User getUser(int userID) {
        for (User user: users) {
            if (user.getId() == userID) {
                return user;
            }
        }

        return null;
    }

    /**
     * Validates a user an if successful return it
     *
     * @param username the user username
     * @param password the user password hashcoded
     * @return the validated user
     */
    public User validateUser(String username, int password) {
        User user = getUser(username);
        if (user == null ){
            return null;
        }

        if (user.validPassword(password)) {
            return user;
        }
        return null;
    }

    /**
     * Loads a user from the file system
     *
     * @param username the user to load
     * @return true if successful
     */
    public boolean loadUser(String username) {
        byte[] file = null;
        //byte[] file = readFile(USERS_PATH+Utils.getCRC32(username)+".txt");

        String fileStr = "";
        try {
            fileStr = new String(file, "UTF-8");
        } catch (UnsupportedEncodingException e) {}


        int endOfLine = fileStr.indexOf(";");

        if (endOfLine == -1) {
            return false;
        }

        String user = fileStr.substring(0, endOfLine);

        String[] userInfo = user.split(":");

        if (userInfo.length != 3) {
            return false;
        }

        if (userInfo[1].equals(username)) {
            users.add(new User(Integer.parseInt(userInfo[0]), userInfo[1], Integer.parseInt(userInfo[2])));
            return true;
        }

        return false;
    }


    /**
     * Saves a user to the file system
     *
     * @param user the user object to save
     */
    public void saveUser(User user) {
        String entry = user.getId()+":"+user.getId()+":"+user.getPassword()+";";

        //saveFile(USERS_PATH+Utils.getCRC32(username)+".txt", entry);
    }

    /**
     *  Saves all users
     */
    public void saveUsers() {
        for (User user : users) {
            saveUser(user);
        }
    }


    /**
     * Creates a new user and then saves it to file system (non-blocking)
     *
     * @param username the user username
     * @param password the user password hashcoded
     */
    public void createUser(String username, int password) {
        saveUser(new User(nextID, username, password));
        nextID++;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //saveFile(USERS_INFO_PATH, nextID);
            }
        }).start();
    }
}
