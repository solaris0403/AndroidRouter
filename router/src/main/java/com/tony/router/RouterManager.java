package com.tony.router;

import android.content.Context;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouter;
import com.tony.router.router.BrowserRouter;
import com.tony.router.router.ActivityRouteTableInitializer;
import com.tony.router.router.IRouter;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有Router及Router的行为
 */
public class RouterManager {
    private static final String TAG = "RouterManager";
    private static RouterManager mInstance = new RouterManager();
    private static List<IRouter> mRouters = new ArrayList<>();
    public static final String KEY_URL = "key_route_url";
    public static final String KEY_FROM = "key_route_from";

    private RouterManager() {
    }

    public static RouterManager getInstance() {
        return mInstance;
    }

    public void initActivityRouter(Context context, ActivityRouteTableInitializer initializer, String... schemes) {
        ActivityRouter router = ActivityRouter.getInstance();
        router.init(context, initializer);
        if (schemes != null && schemes.length > 0) {
            router.setMatchSchemes(schemes);
        }
        addRouter(router);
    }

    public void initBrowserRouter(Context context) {
        BrowserRouter browserRouter = BrowserRouter.getInstance();
        browserRouter.init(context);
        addRouter(browserRouter);
    }

    /**
     * 如果相同 则更新
     *
     * @param router
     */
    public void addRouter(IRouter router) {
        //不可靠，需要一种更强硬的方案,
        if (router != null) {
            for (IRouter r : mRouters) {
                //由于每次都会执行 确保了列表中只存在一个同一种router
                if (r.getClass().equals(router.getClass())) {
                    mRouters.remove(r);
                }
            }
            mRouters.add(router);
        }
    }

    // TODO: 10/27/16 和添加一样需要优化
    public void removeRouter(IRouter router) {
        if (router != null) {
            for (IRouter r : mRouters) {
                if (r.getClass().equals(router.getClass())) {
                    mRouters.remove(r);
                }
            }
        }
    }

    //获取所有router
    public List<IRouter> getRouters() {
        return mRouters;
    }

    // TODO: 10/27/16 打开方式只会取第一个，局限性？如何针对多open进行优化

    /**
     * 通过url生成route
     */
    public IRoute getRoute(String url) {
        for (IRouter router : mRouters) {
            if (router.canOpen(url)) {
                return router.getRoute(url);
            }
        }
        return null;
    }


    public boolean open(String url) {
        for (IRouter router : mRouters) {
            if (router.canOpen(url)) {
                return router.open(url);
            }
        }
        return false;
    }

    public boolean open(IRoute route) {
        for (IRouter router : mRouters) {
            if (router.canOpen(route)) {
                return router.open(route);
            }
        }
        return false;
    }

    //打开的时候需要针对scheme作匹配 但是无法对xml中的匹配
    //并且根据filter来识别的一般是activity,所以该逻辑应该又activityrouter来实现
    public boolean open(Context context, String url) {
        for (IRouter router : mRouters) {
            if (router.canOpen(context, url)) {
                return router.open(context, url);
            }
        }
        return false;
    }


    /**
     * 设置指定Router执行
     */
    public void setRouter(IRouter routrt) {

    }
}
