package com.tony.router.route;

import com.tony.router.router.IRouter;

import java.util.List;
import java.util.Map;

/**
 * 路由分子
 */
public interface IRoute {
    IRouter getRouter();

    String getUrl();

    String getScheme();

    String getHost();

    int getPort();

    List<String> getPath();

    Map<String, String> getParameters();
    //Route can open itself

    /**
     * @return true: open success, false : open fail
     */
    boolean open();
}
