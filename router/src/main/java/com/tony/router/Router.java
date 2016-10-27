package com.tony.router;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouter;
import com.tony.router.router.BrowserRouter;
import com.tony.router.router.HistoryItem;
import com.tony.router.router.IActivityRouteTableInitializer;
import com.tony.router.router.IRouter;

import java.util.Queue;

public class Router {
    public static synchronized void addRouter(IRouter router) {
        RouterManager.getInstance().addRouter(router);
    }

    public static synchronized void initBrowserRouter(Context context) {
        RouterManager.getInstance().initBrowserRouter(context);
    }

    public static synchronized void initActivityRouter(Context context) {
        RouterManager.getInstance().initActivityRouter(context);
    }

    /**
     * @param context
     * @param scheme
     * @param initializer
     * @See
     */
    @Deprecated
    public static synchronized void initActivityRouter(Context context, String scheme, IActivityRouteTableInitializer initializer) {
        RouterManager.getInstance().initActivityRouter(context, initializer, scheme);
    }


    public static synchronized void initActivityRouter(Context context, IActivityRouteTableInitializer initializer, String... scheme) {
        RouterManager.getInstance().initActivityRouter(context, initializer, scheme);
    }

    public static synchronized void initActivityRouter(Context context, String... scheme) {
        RouterManager.getInstance().initActivityRouter(context, scheme);
    }

    public static boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    public static boolean open(Context context, String url) {
        return RouterManager.getInstance().open(context, url);
    }

    /**
     * AndRouter uses Timber to output logs. Timber needs init, so if you don't use Timber and you want to view logs of AndRouter, you may need to
     * use this method, and set the debug as true
     */
    public static void setDebugMode(boolean debug) {
        if (debug) {
//            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * 清除历史记录
     */
    public void clearHistoryCache() {
    }

    /**
     * 设置记录条数
     *
     * @param size
     */
    public void setHistorySize(int size) {
    }


    public static void with(Context context) {
    }

    public static void with(Activity activity) {
    }

    public static void with(FragmentActivity activity) {
    }

    public static void with(Fragment fragment) {
        return null;
    }

    public static void with(android.support.v4.Fragment fragment) {
        return null;
    }


    /**
     * the route of the url, if there is not router to process the url, return null
     *
     * @param url
     * @return
     */
    public static IRoute getRoute(String url) {
        return RouterManager.getInstance().getRoute(url);
    }


    public static boolean openRoute(IRoute route) {
        return RouterManager.getInstance().openRoute(route);
    }

    public static void setActivityRouter(ActivityRouter router) {
        RouterManager.getInstance().setActivityRouter(router);
    }

    public static void setBrowserRouter(BrowserRouter router) {
        RouterManager.getInstance().setBrowserRouter(router);
    }

    public static Queue<HistoryItem> getActivityChangedHistories() {
        return RouterManager.getInstance().getActivityChangedHistories();
    }

}
