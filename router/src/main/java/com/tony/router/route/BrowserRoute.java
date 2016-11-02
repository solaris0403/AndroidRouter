package com.tony.router.route;


import com.tony.router.router.IRouter;

public class BrowserRoute extends AbsRoute {
    public BrowserRoute(IRouter router, String url) {
        super(router, url);
    }

    public static class Builder {
        String mUrl;
        IRouter mRouter;
        public Builder(IRouter router) {
            mRouter = router;
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public BrowserRoute build() {
            return new BrowserRoute(mRouter, mUrl);
        }
    }
}
