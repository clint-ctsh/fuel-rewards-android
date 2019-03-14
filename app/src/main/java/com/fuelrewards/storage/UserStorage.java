package com.fuelrewards.storage;

import com.fuelrewards.models.User;

public class UserStorage {

    private static User user = null;

    public static void login(User loginUser) {
        user = loginUser;
    }

    public static void logout() {
        user = null;
    }

    public static boolean isUserLoggedIn() {
        return user != null;
    }

    public static User getUser() {
        return user;
    }
}