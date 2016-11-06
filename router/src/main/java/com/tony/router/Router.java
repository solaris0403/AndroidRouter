package com.tony.router;

import android.content.Context;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouteTableInitializer;
import com.tony.router.router.IFilter;

/**
 * use class
 */
public class Router {
    /**
     * 注册activity路由器
     *
     * @param context     全局context
     * @param initializer url-class映射表
     * @param scheme      scheme，如果想支持多个，可以在这里配置，如果不配置默认为activity； 目前暂时不支持直接解析initializer中的scheme，
     */
    public static synchronized void initActivityRouter(Context context, ActivityRouteTableInitializer initializer, String... scheme) {
        RouterManager.getInstance().initActivityRouter(context, initializer, scheme);
    }

    /**
     * 注册浏览器路由器
     *
     * @param context 全局context
     */
    public static synchronized void initBrowserRouter(Context context) {
        RouterManager.getInstance().initBrowserRouter(context);
    }

    /**
     * 会清空上一次设置 建议在application中直接初始化
     *
     * @param filter
     */
    public static synchronized void setFilter(IFilter filter) {
        RouterManager.getInstance().setFilter(filter);
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


    /**
     * 根据当前context执行open
     *
     * @param context
     * @param url
     * @return
     */
    public static synchronized boolean open(Context context, String url) {
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
