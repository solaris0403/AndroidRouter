package com.tony.router.matcher;

import android.content.Context;

import com.tony.router.util.RouterLogUtils;
import com.tony.router.util.RouterUtils;

/**
 * Created by tony on 11/6/16.
 */
public class BrowserMatcher extends AbsMatcher {
    public BrowserMatcher() {
        MATCH_SCHEMES.add("https");
        MATCH_SCHEMES.add("http");
    }

    @Override
    public boolean match(Context context, String url) {
        if (MATCH_SCHEMES.contains(RouterUtils.getScheme(url))){
            RouterLogUtils.i(url);
            return true;
        }
        return false;
    }
}
