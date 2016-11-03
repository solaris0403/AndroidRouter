package com.tony.router.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tony.router.route.BrowserRoute;
import com.tony.router.route.IRoute;
import com.tony.router.util.RouterUtils;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created by kris on 16/3/17.
 */
public class BrowserRouter extends AbsRouter {
    private static final Set<String> SCHEMES_CAN_OPEN = new LinkedHashSet<>();

    private Context mBaseContext;

    static BrowserRouter mBrowserRouter = new BrowserRouter();  //浏览器


    static {
        SCHEMES_CAN_OPEN.add("https");
        SCHEMES_CAN_OPEN.add("http");
    }

    public static BrowserRouter getInstance(){
        return mBrowserRouter;
    }

    public void init(Context context){
        mBaseContext = context;
    }


    @Override
    public boolean open(IRoute route) {
        return open(mBaseContext, route);
    }

    @Override
    public boolean open(String url) {
        open(getRoute(url));
        return true;
    }

    @Override
    public boolean open(Context context, String url) {
        return open(context, getRoute(url));
    }

    @Override
    public boolean canOpen(String url) {
        return SCHEMES_CAN_OPEN.contains(RouterUtils.getScheme(url));
    }

    @Override
    public boolean canOpen(IRoute route) {
        return getCanOpenRoute().equals(route.getClass());
    }

    @Override
    public Class<? extends IRoute> getCanOpenRoute() {
        return BrowserRoute.class;
    }

    protected boolean open(Context context, IRoute route){
        Uri uri = Uri.parse(route.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    @Override
    public BrowserRoute getRoute(String url) {
        return new BrowserRoute.Builder(this).setUrl(url).build();
    }
}
