package com.tony.router.route;


public class BrowserRoute extends AbsRoute {
    public BrowserRoute(String url) {
        super(url);
    }

    public static class Builder {
        String mUrl;

        public Builder(String url) {
            mUrl = url;
        }

        public BrowserRoute build() {
            return RouteFactory.getInstance().getBrowserRoute(mUrl);
        }
    }
}
