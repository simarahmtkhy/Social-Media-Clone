package com.socialmediaclone.socialmediaclone.exceptions;

public class UserHasNoAuthorizationException extends RuntimeException{
    public UserHasNoAuthorizationException(String message) {
        super(message);
    }
}
