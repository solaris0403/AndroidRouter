package com.tony.router.router;

import android.app.Activity;

import java.util.Map;

/**
 * 路由表初始化
 */
public interface ActivityRouteTableInitializer{
    void initRouterTable(Map<String, Class<? extends Activity>> router);
}
