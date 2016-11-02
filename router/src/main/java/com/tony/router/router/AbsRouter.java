package com.tony.router.router;

import com.tony.router.route.IRoute;

/**
 * 构造基本的Router实现
 */
public abstract class AbsRouter implements IRouter {
    //有可能是一对多 可以重构
    protected static Class<? extends IRoute> CAN_OPEN_ROUTE;
}
