package com.tony.router.matcher;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 匹配器
 */
public abstract class AbsMatcher implements IMatcherHandler {
    protected Set<String> MATCH_SCHEMES = new HashSet<>();
    @Override
    public Set<String> getMatchSchemes() {
        return MATCH_SCHEMES;
    }

    @Override
    public void setMatchSchemes(String... schemes) {
        MATCH_SCHEMES.clear();
        for (String scheme : schemes) {
            if (!TextUtils.isEmpty(scheme)) {
                MATCH_SCHEMES.add(scheme);
            }
        }
    }

    @Override
    public void addMatchSchemes(String scheme) {
        MATCH_SCHEMES.add(scheme);
    }

    @Override
    public void removeMatchSchemes(String scheme) {
        MATCH_SCHEMES.remove(scheme);
    }

    public abstract boolean match(Context context, String url);
}
