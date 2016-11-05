package com.tony.router;

import android.content.Context;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouteTableInitializer;

/**
 * use class
 */
public class Router {
    /**
     *
     * @param context
     * @param initializer
     * @param scheme 可以添加多种scheme
     */
    public static synchronized void initActivityRouter(Context context, ActivityRouteTableInitializer initializer, String... scheme) {
        RouterManager.getInstance().initActivityRouter(context, initializer, scheme);
    }

    public static synchronized void initBrowserRouter(Context context) {
        RouterManager.getInstance().initBrowserRouter(context);
    }

    /**
     * 执行url
     *
     * @param url
     * @return 执行结果
     */
    public static synchronized boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    /**
     * 执行route
     *
     * @param route
     * @return 执行结果
     */
    public static synchronized boolean open(IRoute route) {
        return RouterManager.getInstance().open(route);
    }


    public static synchronized boolean open(Context context, String url){
        return RouterManager.getInstance().open(context, url);
    }

    /**
     * 通过url获得一个构造出来的Route
     *
     * @param url
     * @return
     */
    public static synchronized IRoute getRoute(String url) {
        return RouterManager.getInstance().getRoute(url);
    }
}
