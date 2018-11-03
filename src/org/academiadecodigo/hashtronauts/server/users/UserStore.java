package org.academiadecodigo.hashtronauts.server.users;

import java.util.LinkedList;
import java.util.List;

public class UserStore {

    //+ loadUsers():void
    //+ createUser(String, int):User


    private List<User> users;

    public UserStore() {
        this.users = new LinkedList<>();
    }

    public User getUser(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(int userID) {
        for (User user: users) {
            if (user.getId() == userID) {
                return user;
            }
        }

        return null;
    }

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

    public void loadUsers() {

    }

    public void saveUser(User user) {

    }

    public void saveUsers() {
        for (User user : users) {
            saveUser(user);
        }
    }
}
