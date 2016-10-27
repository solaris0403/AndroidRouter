package com.tony.router.route;

import java.util.List;
import java.util.Map;

/**
 * 路由分子
 */
public interface IRoute {
    String getUrl();

    String getScheme();

    String getHost();

    int getPort();

    List<String> getPath();

    Map<String, String> getParameters();
}
