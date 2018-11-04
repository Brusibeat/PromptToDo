package org.academiadecodigo.hashtronauts.server.users;

import org.academiadecodigo.hashtronauts.server.utils.FileSystem;
import org.academiadecodigo.hashtronauts.server.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/** Stores the Logged Users && manages the user Database */
public class UserStore {

    public static final String USERS_PATH = "resources/users/";
    public static final String USERS_INFO_PATH = "resources/users/userInfo.txt";

    private List<User> users;
    private int nextID;

    public UserStore() {
        nextID = 0;
        this.users = new LinkedList<>();
    }

    public void initStore() {
        byte[] data = FileSystem.loadFile(USERS_INFO_PATH);

        if (data == null) {
            FileSystem.saveFile(USERS_INFO_PATH, (nextID+"").getBytes());
            data = (nextID+"").getBytes();
        }

        nextID = Integer.parseInt(new String(data).replaceAll("\n", ""));
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
            user = loadUser(username);
        }

        if (user != null && user.validPassword(password)) {
            users.add(user);
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
    public User loadUser(String username) {
        byte[] file;

        file = FileSystem.loadFile(USERS_PATH+Utils.getCRC32(username)+".txt");

        if (file == null) {
            return null;
        }

        String fileStr = "";
        try {
            fileStr = new String(file, "UTF-8");
        } catch (UnsupportedEncodingException e) {}


        int endOfLine = fileStr.indexOf(";");

        if (endOfLine == -1) {
            return null;
        }

        String user = fileStr.substring(0, endOfLine);

        String[] userInfo = user.split(":");

        if (userInfo.length != 3) {
            return null;
        }


        if (userInfo[1].equals(username)) {
            return new User(Integer.parseInt(userInfo[0]), userInfo[1], Integer.parseInt(userInfo[2]));
        }

        return null;
    }


    /**
     * Saves a user to the file system
     *
     * @param user the user object to save
     */
    public void saveUser(User user) {
        String entry = user.getId() + ":" + user.getUsername() + ":" + user.getPassword() + ";";

        FileSystem.saveFile(USERS_PATH+Utils.getCRC32(user.getUsername())+".txt", entry.getBytes());
    }

    /**
     * Creates a new user and then saves it to file system (non-blocking)
     *  @param username the user username
     * @param password the user password hashcoded
     */
    public User createUser(String username, int password) {
        User user = new User(nextID, username, password);

        saveUser(user);
        nextID++;

        synchronized (this) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileSystem.saveFile(USERS_INFO_PATH, (nextID+"").getBytes());
                }
            }).start();
        }
        return user;
    }
}
