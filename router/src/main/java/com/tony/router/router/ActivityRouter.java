package com.tony.router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.tony.router.BuildConfig;
import com.tony.router.exception.InvalidValueTypeException;
import com.tony.router.exception.RouteNotFoundException;
import com.tony.router.route.ActivityRoute;
import com.tony.router.route.IRoute;
import com.tony.router.rule.ActivityRouteRuleBuilder;
import com.tony.router.util.RouterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tony on 10/26/16.
 */
public class ActivityRouter extends AbsRouter {
    private static final String TAG = "ActivityRouter";
    private static ActivityRouter mActivityRouter = new ActivityRouter();

    private static final String DEFAULT_SCHEME = "activity";
    private static List<String> MATCH_SCHEMES = new ArrayList<>();
    public static final String KEY_URL = "key_and_activity_router_url";

    static {
        MATCH_SCHEMES.add(DEFAULT_SCHEME);
        CAN_OPEN_ROUTE = ActivityRoute.class;
    }

    private Context mBaseContext;
    Map<String, Class<? extends Activity>> mRouteTable = new HashMap<>();

    public static ActivityRouter getInstance() {
        return mActivityRouter;
    }

    public void init(Context appContext, IActivityRouteTableInitializer initializer) {
        mBaseContext = appContext;
        initActivityRouterTable(initializer);
    }


    public void init(Context appContext) {
        mBaseContext = appContext;
        for (String pathRule : mRouteTable.keySet()) {
            boolean isValid = ActivityRouteRuleBuilder.isActivityRuleValid(pathRule);
            if (!isValid) {
                mRouteTable.remove(pathRule);
            }
        }
    }

    public void initActivityRouterTable(IActivityRouteTableInitializer initializer) {
        if (initializer != null) {
            initializer.initRouterTable(mRouteTable);
        }
        for (String pathRule : mRouteTable.keySet()) {
            boolean isValid = ActivityRouteRuleBuilder.isActivityRuleValid(pathRule);
            if (!isValid) {
                mRouteTable.remove(pathRule);
            }
        }
    }

    @Override
    public IRoute getRoute(String url) {
        return new ActivityRoute.Builder(this).setUrl(url).build();
    }

    @Override
    public boolean canOpen(String url) {
        for (String scheme : MATCH_SCHEMES) {
            if (TextUtils.equals(scheme, RouterUtils.getScheme(url))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canOpen(IRoute route) {
        if (route != null) {
            for (String scheme : MATCH_SCHEMES) {
                if (TextUtils.equals(scheme, route.getScheme())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getMatchSchemes() {
        return MATCH_SCHEMES;
    }

    public void setMatchSchemes(String... schemes) {
        MATCH_SCHEMES.clear();
        List<String> list = Arrays.asList(schemes);
        MATCH_SCHEMES.addAll(list);
    }

    public void addMatchSchemes(String scheme) {
        MATCH_SCHEMES.add(scheme);
    }


    @Override
    public Class<? extends IRoute> getCanOpenRoute() {
        return CAN_OPEN_ROUTE;
    }


    @Override
    public boolean open(IRoute route) {
        boolean canOpen = false;
        if (route instanceof ActivityRoute) {
            ActivityRoute activityRoute = (ActivityRoute) route;
            try {
                switch (activityRoute.getStartType()) {
                    case ActivityRoute.START_ACTIVITY:
                        open(activityRoute, activityRoute.getActivity());
                        canOpen = true;
                        break;
                    case ActivityRoute.START_ACTIVITY_FOR_RESULT:
                        openForResult(activityRoute, activityRoute.getActivity(), activityRoute.getRequestCode());
                        canOpen = true;
                        break;
                    default:
                        open(activityRoute, activityRoute.getActivity());
                        canOpen = true;
                        break;
                }
            } catch (Exception e) {
                canOpen = false;
            }
        }
        return canOpen;
    }

    @Override
    public boolean open(String url) {
        return open(null, url);
    }

    @Override
    public boolean open(Context context, String url) {
        IRoute route = getRoute(url);
        if (route instanceof ActivityRoute) {
            ActivityRoute aRoute = (ActivityRoute) route;
            try {
                open(aRoute, context);
                return true;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return false;
    }

    /**
     * 打开的具体操作
     *
     * @param route
     * @param context
     * @throws RouteNotFoundException
     */
    protected void open(ActivityRoute route, Context context) throws RouteNotFoundException {
        //创建intent
        Intent intent = match(route);
        if (intent == null) {
            throw new RouteNotFoundException(route.getUrl());
        }
        if (context == null) {
            //使用app启动  重新设置flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | route.getFlags());
            mBaseContext.startActivity(intent);
        } else {
            //正常启动
            context.startActivity(intent);
        }
    }

    /**
     * 打开的具体操作
     *
     * @param route
     * @param activity
     * @param requestCode
     * @throws RouteNotFoundException
     */
    protected void openForResult(ActivityRoute route, Activity activity, int requestCode) throws RouteNotFoundException {
        Intent intent = match(route);
        if (intent == null) {
            throw new RouteNotFoundException(route.getUrl());
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 在routrtable里面查找存才的activity class所对应的url
     *
     * @param route
     * @return String the match routePath
     */
    @Nullable
    private String findMatchedRoute(ActivityRoute route) {
        //根据route获取所有path
        List<String> givenPathSegs = route.getPath();
        OutLoop:
        //遍历mRouteTable中所有url
        for (String routeUrl : mRouteTable.keySet()) {
            //获得每一个url的path列表
            List<String> routePathSegs = RouterUtils.getPathSegments(routeUrl);
            //针对host进行match
            if (!TextUtils.equals(RouterUtils.getHost(routeUrl), route.getHost())) {
                continue;
            }
            //针对path数量进行match
            if (givenPathSegs.size() != routePathSegs.size()) {
                continue;
            }
            //遍历每一个path
            for (int i = 0; i < routePathSegs.size(); i++) {
                //如果每个片段不是以：开头，并且两个值不相等的话重新循环
                if (!routePathSegs.get(i).startsWith(":") && !TextUtils.equals(routePathSegs.get(i), givenPathSegs.get(i))) {
                    continue OutLoop;
                }
            }
            //find the match route
            return routeUrl;
        }
        return null;
    }

    /**
     * 从url中找出基本数据类型放到intent中,对此，是有点复杂的，如果把kv放在query中，的话可以简单一点，而且如果这样搞的话，服务端也需要重新修改，不符合标准
     *
     * @param routeUrl the matched route path
     * @param givenUrl the given path
     * @param intent   the intent
     * @return the intent
     */
    private Intent setKeyValueInThePath(String routeUrl, String givenUrl, Intent intent) {
        List<String> routePathSegs = RouterUtils.getPathSegments(routeUrl);
        List<String> givenPathSegs = RouterUtils.getPathSegments(givenUrl);
        for (int i = 0; i < routePathSegs.size(); i++) {
            String seg = routePathSegs.get(i);
            if (seg.startsWith(":")) {
                int indexOfLeft = seg.indexOf("{");
                int indexOfRight = seg.indexOf("}");
                String key = seg.substring(indexOfLeft + 1, indexOfRight);
                char typeChar = seg.charAt(1);
                switch (typeChar) {
                    //interger type
                    case 'i':
                        try {
                            int value = Integer.parseInt(givenPathSegs.get(i));
                            intent.putExtra(key, value);
                        } catch (Exception e) {
//                            Log.e(TAG, "解析整形类型失败 " + givenPathSegs.get(i), e);
                            if (BuildConfig.DEBUG) {
                                throw new InvalidValueTypeException(givenUrl, givenPathSegs.get(i));
                            } else {
                                //如果是在release情况下则给一个默认值
                                intent.putExtra(key, 0);
                            }
                        }
                        break;
                    case 'f':
                        //float type
                        try {
                            float value = Float.parseFloat(givenPathSegs.get(i));
                            intent.putExtra(key, value);
                        } catch (Exception e) {
//                            Log.e(TAG, "解析浮点类型失败 " + givenPathSegs.get(i), e);
                            if (BuildConfig.DEBUG) {
                                throw new InvalidValueTypeException(givenUrl, givenPathSegs.get(i));
                            } else {
                                intent.putExtra(key, 0f);
                            }
                        }
                        break;
                    case 'l':
                        //long type
                        try {
                            long value = Long.parseLong(givenPathSegs.get(i));
                            intent.putExtra(key, value);
                        } catch (Exception e) {
//                            Log.e(TAG, "解析长整形失败 " + givenPathSegs.get(i), e);
                            if (BuildConfig.DEBUG) {
                                throw new InvalidValueTypeException(givenUrl, givenPathSegs.get(i));
                            } else {
                                intent.putExtra(key, 0l);
                            }
                        }
                        break;
                    case 'd':
                        try {
                            double value = Double.parseDouble(givenPathSegs.get(i));
                            intent.putExtra(key, value);
                        } catch (Exception e) {
//                            Log.e(TAG, "解析double类型失败 " + givenPathSegs.get(i), e);
                            if (BuildConfig.DEBUG) {
                                throw new InvalidValueTypeException(givenUrl, givenPathSegs.get(i));
                            } else {
                                intent.putExtra(key, 0d);
                            }
                        }
                        break;
                    case 'c':
                        try {
                            char value = givenPathSegs.get(i).charAt(0);
                        } catch (Exception e) {
//                            Log.e(TAG, "解析Character类型失败" + givenPathSegs.get(i), e);
                            if (BuildConfig.DEBUG) {
                                throw new InvalidValueTypeException(givenUrl, givenPathSegs.get(i));
                            } else {
                                intent.putExtra(key, ' ');
                            }
                        }
                        break;
                    case 's':
                    default:
                        intent.putExtra(key, givenPathSegs.get(i));
                }
            }

        }
        return intent;
    }

    private Intent putExtras(String url, Intent intent) {
        Map<String, String> queryParams = RouterUtils.getQueryParameter(url);
        for (String key : queryParams.keySet()) {
            intent.putExtra(key, queryParams.get(key));
        }
        return intent;
    }

    private Intent putBundle(Bundle bundle, Intent intent) {
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 从routemap中找出可以match的class 并且赋值
     *
     * @param route
     * @return
     */
    @Nullable
    private Intent match(ActivityRoute route) {
        //从routemap中寻找匹配的url
        String matchedRoute = findMatchedRoute(route);
        if (matchedRoute == null) {
            return null;
        }
        //根据找出的rul找到activity的class
        Class<? extends Activity> matchedActivity = mRouteTable.get(matchedRoute);
        Intent intent = new Intent(mBaseContext, matchedActivity);
        //find the key value in the path
//        intent = setKeyValueInThePath(matchedRoute, route.getUrl(), intent);
        //找到query中的值放到intent中
        intent = putExtras(route.getUrl(), intent);
        //找到设置的bundle放到intent中
        intent = putBundle(route.getExtras(), intent);
        //setflags
        intent.setFlags(route.getFlags());
        //再网intent中放置一个标记key
        intent.putExtra(KEY_URL, route.getUrl());
        return intent;
    }


    public static String getKeyUrl() {
        return KEY_URL;
    }
}
