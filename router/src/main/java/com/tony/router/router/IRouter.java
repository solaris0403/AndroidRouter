package com.tony.router.router;

import com.tony.router.route.IRoute;

/**
 * Created by tony on 10/26/16.
 */
public interface IRouter {
    IRoute getRoute(String url);

    boolean open(IRoute route);

    boolean open(String url);
}
