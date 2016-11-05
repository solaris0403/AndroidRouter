package com.tony.router.matcher;

import android.content.Context;
import android.text.TextUtils;

import com.tony.router.route.IRoute;
import com.tony.router.util.RouterUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tony on 2016/11/5.
 */
public class ActivitySchemeMatcher extends ActivityMatcher {
    private static final String DEFAULT_SCHEME = "activity";
    private static Set<String> MATCH_SCHEMES = new HashSet<>();

    static {
        MATCH_SCHEMES.add(DEFAULT_SCHEME);
    }

    public Set<String> getMatchSchemes() {
        return MATCH_SCHEMES;
    }

    public void setMatchSchemes(String... schemes) {
        MATCH_SCHEMES.clear();
        for (String scheme : schemes) {
            if (!TextUtils.isEmpty(scheme)) {
                MATCH_SCHEMES.add(scheme);
            }
        }
    }

    public void addMatchSchemes(String scheme) {
        MATCH_SCHEMES.add(scheme);
    }

    @Override
    public boolean match(Context context, String url) {
        if (MATCH_SCHEMES.contains(RouterUtils.getScheme(url))) {
            return true;
        }
        return matcher.match(context, url);
    }

    @Override
    public boolean match(Context context, IRoute route) {
        if (MATCH_SCHEMES.contains(route.getScheme())){
            return true;
        }
        return matcher.match(context, route);
    }
}
