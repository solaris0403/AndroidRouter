package com.tony.router.matcher;

import android.content.Context;

import com.tony.router.util.RouterLogUtils;
import com.tony.router.util.RouterUtils;

/**
 * Created by tony on 2016/11/5.
 */
public class ActivitySchemeMatcher extends ActivityMatcher {
    private static final String DEFAULT_SCHEME = "activity";

    public ActivitySchemeMatcher() {
        MATCH_SCHEMES.add(DEFAULT_SCHEME);
    }

    @Override
    public boolean match(Context context, String url) {
        if (MATCH_SCHEMES.contains(RouterUtils.getScheme(url))) {
            RouterLogUtils.i(url);
            return true;
        }
        return matcher.match(context, url);
    }
}
