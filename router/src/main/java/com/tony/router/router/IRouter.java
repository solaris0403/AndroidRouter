package com.tony.router.router;

import android.content.Context;

import com.tony.router.route.IRoute;

/**
 * 路由器
 */
public interface IRouter {
    /**
     * 根据url生成route
     */
    IRoute getRoute(String url);

    boolean open(IRoute route);

    boolean open(String url);

    boolean open(Context context, String url);

    /**
     * 只是针对scheme进行match，因为能不能打开只有打开以后才可以知道。
     */
    boolean canOpen(String url);

    boolean canOpen(IRoute route);

    boolean canOpen(Context context, String url);
}
