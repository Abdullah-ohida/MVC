package com.example.blogger.web.exceptions;

public class PostDoesNotExistException extends BloggerException {
    public PostDoesNotExistException(String message) {
        super(message);
    }
}
