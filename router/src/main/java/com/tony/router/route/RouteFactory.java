package com.tony.router.route;

import com.tony.router.cache.RouteCache;
import com.tony.router.util.RouterLogUtils;

/**
 * route复用工厂
 */
public class RouteFactory {
    private static final String TAG = "RouteFactory";
    private static RouteFactory mInstance = new RouteFactory();
    private RouteCache mRouteCache;

    private RouteFactory() {
        //默认缓存20个 之后需要设置可调整参数
        mRouteCache = new RouteCache(20);
    }
    //// TODO: 2016/11/5 需要改为懒加载 
    public static RouteFactory getInstance(){
        return mInstance;
    }

    public ActivityRoute getActivityRoute(String key){
        ActivityRoute activityRoute = (ActivityRoute) mRouteCache.get(key);
        if (activityRoute == null){
            activityRoute = new ActivityRoute(key);
            mRouteCache.put(key, activityRoute);
        }
        display();
        return activityRoute;
    }

    public BrowserRoute getBrowserRoute(String key){
        BrowserRoute browserRoute = (BrowserRoute) mRouteCache.get(key);
        if (browserRoute == null){
            browserRoute = new BrowserRoute(key);
            mRouteCache.put(key, browserRoute);
        }
        display();
        return browserRoute;
    }

    public void display() {
        StringBuilder sb = new StringBuilder("mRouteCache = ");
        for (IRoute route : mRouteCache.snapshot().values()){
            sb.append(route.getUrl());
            sb.append(", ");
        }
        RouterLogUtils.i(sb);
    }
}
