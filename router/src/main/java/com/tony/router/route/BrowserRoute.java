package com.tony.router.route;


import com.tony.router.router.IRouter;

public class BrowserRoute extends AbsRoute {
    public BrowserRoute(String url) {
        super(url);
    }

    public static class Builder {
        String mUrl;
        IRouter mRouter;

        public Builder(String url) {
            mUrl = url;
        }

        public BrowserRoute build() {
            return new BrowserRoute(mUrl);
        }
    }
}
