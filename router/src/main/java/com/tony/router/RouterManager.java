package com.tony.router;

import android.content.Context;
import android.support.annotation.Nullable;

import com.tony.router.route.IRoute;
import com.tony.router.router.ActivityRouter;
import com.tony.router.router.BrowserRouter;
import com.tony.router.router.HistoryItem;
import com.tony.router.router.IActivityRouteTableInitializer;
import com.tony.router.router.IRouter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by tony on 10/26/16.
 */
public class RouterManager {
    private static RouterManager mInstance = new RouterManager();
    private static List<IRouter> mRouters = new LinkedList<>();
    private RouterManager() {
    }

    public static RouterManager getInstance() {
        return mInstance;
    }
    public synchronized void addRouter(IRouter router) {
        if (router != null) {
            //first remove all the duplicate routers
            List<IRouter> duplicateRouters = new ArrayList<>();
            for (IRouter r : mRouters) {
                if (r.getClass().equals(router.getClass())) {
                    duplicateRouters.add(r);
                }
            }
            mRouters.removeAll(duplicateRouters);
            mRouters.add(router);
        } else {
//            Timber.e(new NullPointerException("The Router" +
//                    "is null" +
//                    ""), "");
        }
    }

    public synchronized void initBrowserRouter(Context context) {
        BrowserRouter browserRouter = BrowserRouter.getInstance();
        browserRouter.init(context);
        addRouter(browserRouter);
    }


    public synchronized void initActivityRouter(Context context) {
        ActivityRouter activityRouter = ActivityRouter.getInstance();
        activityRouter.init(context);
        addRouter(activityRouter);
    }

    public synchronized void initActivityRouter(Context context, String... schemes) {
        initActivityRouter(context, null, schemes);
    }

    public synchronized void initActivityRouter(Context context, IActivityRouteTableInitializer initializer, String... schemes) {
        ActivityRouter router = ActivityRouter.getInstance();
        if (initializer == null) {
            router.init(context);
        } else {
            router.init(context, initializer);
        }
        if (schemes != null && schemes.length > 0) {
            router.setMatchSchemes(schemes);
        }
        addRouter(router);
    }

    public List<IRouter> getRouters() {
        return mRouters;
    }


    public boolean open(String url) {
        for (IRouter router : mRouters) {
            if (router.canOpenTheUrl(url)) {
                return router.open(url);
            }
        }
        return false;
    }

//    /**
//     * the route of the url, if there is not router to process the url, return null
//     *
//     * @param url
//     * @return
//     */
//    @Nullable
//    public IRoute getRoute(String url) {
//        for (IRouter router : mRouters) {
//            if (router.canOpenTheUrl(url)) {
//                return router.getRoute(url);
//            }
//        }
//        return null;
//    }
//
//    public boolean open(Context context, String url) {
//        for (IRouter router : mRouters) {
//            if (router.canOpenTheUrl(url)) {
//                return router.open(context, url);
//            }
//        }
//        return false;
//    }
//
//
//    public boolean openRoute(IRoute route) {
//        for (IRouter router : mRouters) {
//            if (router.canOpenTheRoute(route)) {
//                return router.open(route);
//            }
//        }
//        return false;
//    }

    /**
     * set your own activity router
     */
    public void setActivityRouter(ActivityRouter router) {
        addRouter(router);
    }

    /**
     * set your own BrowserRouter
     */
    public void setBrowserRouter(BrowserRouter router) {
        addRouter(router);
    }


    public Queue<HistoryItem> getActivityChangedHistories() {
        ActivityRouter aRouter = null;
        for (IRouter router : mRouters) {
            if (router instanceof ActivityRouter) {
                aRouter = (ActivityRouter) router;
                break;
            }
        }
        return aRouter != null ? aRouter.getRouteHistories() : null;
    }
}
