package com.tony.router.exception;

public class RouteNotFoundException extends Exception {
    public RouteNotFoundException(String url){
        super(String.format("The route not found: %s", url));
    }
}
