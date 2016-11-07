package com.tony.router.exception;

/**
 * Created by tony on 11/7/16.
 */
public class UrlInvalidException extends Exception{
    public UrlInvalidException(){
        super("The url can't be null");
    }
}
