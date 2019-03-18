package com.fuelrewards.storage;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fuelrewards.adapters.UserAdapter;
import com.fuelrewards.models.User;

public class UserStorage {

    private User user = null;
    private static UserStorage userStorage;

    protected UserStorage() { }

    public static UserStorage getInstance() {

        if (userStorage == null)
            userStorage = new UserStorage();

        return userStorage;
    }


    public void login(User loginUser) {
        user = loginUser;
    }

    public void logout() {
        user = null;
    }

    public boolean isUserLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }
}