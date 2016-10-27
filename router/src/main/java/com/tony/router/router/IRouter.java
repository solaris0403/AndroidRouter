package com.tony.router.router;

import com.tony.router.route.IRoute;

/**
 * Created by tony on 10/26/16.
 */
public interface IRouter {
    IRoute getRoute(String url);

    boolean open(IRoute route);

    boolean open(String url);

    /**
     * 只是针对scheme进行match，因为能不能打开只有打开以后才可以知道。
     * @param url
     * @return
     */
    boolean canOpen(String url);

    boolean canOpen(IRoute route);
}
