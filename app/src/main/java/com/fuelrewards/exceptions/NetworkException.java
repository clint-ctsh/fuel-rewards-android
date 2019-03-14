package com.fuelrewards.exceptions;

public class NetworkException extends Exception {

    public NetworkException(String errorMessage) {
        super(errorMessage);
    }

    public NetworkException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}