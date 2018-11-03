package org.academiadecodigo.hashtronauts.server.users;

public class User {

    private final int id;
    private final String username;
    private final int password;

    public  User(int id, String username, int password) {

        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return this.id;
    }

    public boolean validPassword(int password) {
        return this.password == password;
    }

    public int getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return id+":"+username+":"+password+";";
    }
}
