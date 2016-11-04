package com.tony.router.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tony.router.route.BrowserRoute;
import com.tony.router.route.IRoute;
import com.tony.router.util.RouterUtils;

import java.util.LinkedHashSet;
import java.util.Set;


public class BrowserRouter extends AbsRouter {
    private static final Set<String> MATCH_SCHEMES = new LinkedHashSet<>();

    private Context mApplicationContext;

    static BrowserRouter mBrowserRouter = new BrowserRouter();  //浏览器


    static {
        MATCH_SCHEMES.add("https");
        MATCH_SCHEMES.add("http");
    }

    public static BrowserRouter getInstance() {
        return mBrowserRouter;
    }

    public void init(Context context) {
        mApplicationContext = context;
    }


    @Override
    public boolean open(IRoute route) {
        return openBrowser(mApplicationContext, route);
    }

    @Override
    public boolean open(String url) {
        open(getRoute(url));
        return true;
    }

    @Override
    public boolean open(Context context, String url) {
        return openBrowser(context, getRoute(url));
    }

    @Override
    public boolean canOpen(String url) {
        return MATCH_SCHEMES.contains(RouterUtils.getScheme(url));
    }

    @Override
    public boolean canOpen(IRoute route) {
        return MATCH_SCHEMES.contains(route.getScheme());
    }

    protected boolean openBrowser(Context context, IRoute route) {
        Uri uri = Uri.parse(route.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //浏览器有自己的flag 不过保险起见 这边也要设置
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    @Override
    public BrowserRoute getRoute(String url) {
        return new BrowserRoute.Builder(url).build();
    }
}
