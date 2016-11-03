package com.tony.router.route;

import android.util.Log;

import com.tony.router.Router;
import com.tony.router.cache.RouteCache;

/**
 * 复用类
 */
public class RouteFactory {
    private static RouteFactory mInstance = new RouteFactory();
    private static RouteCache mRouteCache;

    private RouteFactory() {
        mRouteCache = new RouteCache(30);
    }

    public static RouteFactory getInstance() {
        return mInstance;
    }

    public IRoute get(String key) {
        if (mRouteCache.get(key) == null){
            return Router.getRoute(key);
        }
        return mRouteCache.get(key);
    }

    public void put(String key, IRoute route) {
        mRouteCache.put(key, route);
    }

    public void display(){
        Log.e("123+s", mRouteCache.size()+"");
        Log.e("123+m", mRouteCache.maxSize()+"");
    }
}
