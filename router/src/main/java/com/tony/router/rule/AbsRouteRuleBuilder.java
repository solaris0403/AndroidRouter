package com.tony.router.rule;

import android.net.Uri;

/**
 * Created by tony on 10/26/16.
 */
public abstract class AbsRouteRuleBuilder implements IRouteRuleBuilder {
    private Uri.Builder builder = new Uri.Builder();

    @Override
    public IRouteRuleBuilder setScheme(String scheme) {
        builder.scheme(scheme);
        return this;
    }

    @Override
    public IRouteRuleBuilder setHost(String host) {
        builder.authority(host);
        return this;
    }

    @Override
    public IRouteRuleBuilder setPath(String path) {
        builder.path(path);
        return this;
    }

    @Override
    public IRouteRuleBuilder addPathSegment(String seg) {
        builder.appendPath(seg);
        return this;
    }

    @Override
    public IRouteRuleBuilder addQueryParameter(String key, String value) {
        builder.appendQueryParameter(key, value);
        return this;
    }

    public String build() {
        return builder.build().toString();
    }
}
