package com.example.blogger.web.exceptions;

public class BloggerException extends Exception{
    public BloggerException(String message) {
        super(message);
    }

    public BloggerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BloggerException(Throwable cause) {
        super(cause);
    }
}
