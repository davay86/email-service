package com.babcock.email.stream.payload;

import java.util.List;

public class UserPayload {

    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserPayload{" +
                "users=" + users +
                '}';
    }
}
