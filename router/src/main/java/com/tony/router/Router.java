package com.tony.router;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouter;
import com.tony.router.router.BrowserRouter;
import com.tony.router.router.HistoryItem;
import com.tony.router.router.IRouter;

import java.util.Queue;

public class Router {
    public static synchronized void addRouter(IRouter router) {
        RouterManager.getInstance().addRouter(router);
    }

    public static boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    public static boolean open(IRoute route) {
        return RouterManager.getInstance().open(route);
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


    public static RouterManager with(Context context) {
        return null;
    }

    public static RouterManager with(Activity activity) {
        return null;
    }

    public static RouterManager with(FragmentActivity activity) {
        return null;
    }

    public static RouterManager with(Fragment fragment) {
        return null;
    }

    public static RouterManager with(android.support.v4.Fragment fragment) {
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

    public static Queue<HistoryItem> getActivityChangedHistories() {
        return RouterManager.getInstance().getActivityChangedHistories();
    }

}
