package com.tony.router.cache;

import android.util.LruCache;

import com.tony.router.route.IRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * url生成route,且为内部数据,所以只能根据url来缓存route
 */
public class RouteCache extends LruCache<String, IRoute>{
    public RouteCache(int maxSize) {
        super(maxSize);
    }
}
