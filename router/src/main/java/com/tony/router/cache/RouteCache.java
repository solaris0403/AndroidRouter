package com.tony.router.cache;

import com.tony.router.route.IRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * 不需要
 */
public class RouteCache{
    private static Map<Class<? extends IRoute>, IRoute> mRouteCache = new HashMap<>();
    public static IRoute get(String key) {
        return mRouteCache.get(key);
    }

    public static void put(Class<? extends IRoute> key, IRoute route) {
        mRouteCache.put(key, route);
    }
}
