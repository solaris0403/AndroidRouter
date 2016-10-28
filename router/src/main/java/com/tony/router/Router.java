package com.tony.router;

import android.app.Activity;

import com.tony.router.route.IRoute;
import com.tony.router.router.IRouter;

public class Router {
    /**
     * 添加Router,通常在Application中初始化Router
     * @param router
     */
    public static void addRouter(IRouter router) {
        RouterManager.getInstance().addRouter(router);
    }

    /**
     * 执行url
     * @param url
     * @return 执行结果
     */
    public static boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    /**
     * 执行route
     * @param route
     * @return 执行结果
     */
    public static boolean open(IRoute route) {
        return RouterManager.getInstance().open(route);
    }

    /**
     * 清除缓存记录
     */
    public void clearRouteCache() {
    }

    /**
     * 设置缓存条数
     *
     * @param size
     */
    public void setRouteCacheSize(int size) {
    }

    public static RouterManager with(Activity activity) {
        RouterManager rm = RouterManager.getInstance().with(activity);
        return null;
    }


    /**
     * 通过url获得一个构造出来的Route
     * @param url
     * @return
     */
    public static IRoute getRoute(String url) {
        return RouterManager.getInstance().getRoute(url);
    }
}
