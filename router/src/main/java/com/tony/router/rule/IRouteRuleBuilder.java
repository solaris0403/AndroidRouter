package com.tony.router.rule;

/**
 * Created by tony on 10/26/16.
 */
public interface IRouteRuleBuilder {
    IRouteRuleBuilder setScheme(String scheme);
    IRouteRuleBuilder setHost(String host);
    IRouteRuleBuilder setPath(String path);
    IRouteRuleBuilder addPathSegment(String seg);
    IRouteRuleBuilder addQueryParameter(String key, String value);
}
