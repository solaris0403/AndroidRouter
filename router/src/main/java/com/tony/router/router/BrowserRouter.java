package com.tony.router.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tony.router.matcher.BrowserMatcher;
import com.tony.router.route.BrowserRoute;
import com.tony.router.route.IRoute;


public class BrowserRouter extends AbsRouter {
    private Context mApplicationContext;
    private static BrowserRouter mBrowserRouter = new BrowserRouter();  //浏览器
    private BrowserMatcher mBrowserMatcher;

    public BrowserRouter() {
        mBrowserMatcher = new BrowserMatcher();
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
        return open(getRoute(url));
    }

    @Override
    public boolean open(Context context, String url) {
        return openBrowser(context, getRoute(url));
    }

    @Override
    public boolean canOpen(String url) {
        if (mFilter != null){
            url = mFilter.doFilter(url);
        }
        return mBrowserMatcher.match(mApplicationContext, url);
    }

    @Override
    public boolean canOpen(IRoute route) {
        if (route != null) {
            String url = route.getUrl();
            if (mFilter != null) {
                url = mFilter.doFilter(url);
            }
            return mBrowserMatcher.match(mApplicationContext, url);
        }
        return false;
    }

    @Override
    public boolean canOpen(Context context, String url) {
        if (mFilter != null){
            url = mFilter.doFilter(url);
        }
        return mBrowserMatcher.match(context, url);
    }

    protected boolean openBrowser(Context context, IRoute route) {
        String url = route.getUrl();
        if (mFilter != null){
            url = mFilter.doFilter(url);
        }
        Uri uri = Uri.parse(url);
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
