package com.tony.router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tony.router.BuildConfig;
import com.tony.router.exception.InvalidValueTypeException;
import com.tony.router.exception.RouteNotFoundException;
import com.tony.router.route.ActivityRoute;
import com.tony.router.route.IRoute;
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
    private static ActivityRouter mActivityRouter = new ActivityRouter();
    private static final String DEFAULT_SCHEME = "activity";
    private static List<String> MATCH_SCHEMES = new ArrayList<>();
    private static final int HISTORY_CACHE_SIZE = 20;
    public static final String KEY_URL = "key_and_activity_router_url";

    static {
        MATCH_SCHEMES.add(DEFAULT_SCHEME);
    }

    private Context mContext;
    Map<String, Class<? extends Activity>> mRouteTable = new HashMap<>();
    List<HistoryItem> mHistoryCaches = new ArrayList<>(HISTORY_CACHE_SIZE);

    public static ActivityRouter getInstance() {
        return mActivityRouter;
    }

    @Override
    public boolean open(IRoute route) {
        boolean canOpen = false;
        if (route instanceof ActivityRoute) {
            ActivityRoute activityRouteRoute = (ActivityRoute) route;
            try {
                switch (activityRouteRoute.getStartType()) {
                    case ActivityRoute.START_ACTIVITY:
                        startActivity(activityRouteRoute.getActivity(), activityRouteRoute);
                        canOpen = true;
                        break;
                    case ActivityRoute.START_ACTIVITY_FOR_RESULT:
                        startActivityForResult(activityRouteRoute.getActivity(), activityRouteRoute, activityRouteRoute.getRequestCode());
                        canOpen = true;
                        break;
                    default:
                        startActivity(activityRouteRoute.getActivity(), activityRouteRoute);
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
        IRoute route = getRoute(url);
        if (route instanceof ActivityRoute) {
            ActivityRoute activityRoute = (ActivityRoute) route;
            try {
                startActivity(mContext, activityRoute);
                return true;
            } catch (Exception e) {
//                Timber.e(e, "Url route not specified: %s", route.getUrl());
            }
        }
        return false;
    }


    private void startActivity(Context context, ActivityRoute activityRoute) throws RouteNotFoundException {
        Class<?> fromClazz = context != null ? context.getClass() : mContext.getClass();
        Intent intent = match(fromClazz, activityRoute);
        if (intent == null) {
            throw new RouteNotFoundException(activityRoute.getUrl());
        }
        if (context == null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | activityRoute.getFlags());
            mContext.startActivity(intent);
        } else {
            intent.setFlags(activityRoute.getFlags());
            context.startActivity(intent);
        }
    }

    private void startActivityForResult(Activity activity, ActivityRoute activityRoute, int requestCode) throws RouteNotFoundException {
        Intent intent = match(activity.getClass(), activityRoute);
        if (intent == null) {
            throw new RouteNotFoundException(activityRoute.getUrl());
        }
        intent.setFlags(activityRoute.getFlags());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * host 和path匹配称之为路由匹匹配
     * @param route
     * @return String the match routePath
     */
    @Nullable
    private String findMatchedRoute(ActivityRoute route) {
        List<String> givenPathSegs = route.getPath();
        OutLoop:
        for (String routeUrl : mRouteTable.keySet()) {
            List<String> routePathSegs = RouterUtils.getPathSegments(routeUrl);
            if (!TextUtils.equals(RouterUtils.getHost(routeUrl), route.getHost())) {
                continue;
            }
            if (givenPathSegs.size() != routePathSegs.size()) {
                continue;
            }
            for (int i = 0; i < routePathSegs.size(); i++) {
                if (!routePathSegs.get(i).startsWith(":")
                        && !TextUtils.equals(routePathSegs.get(i), givenPathSegs.get(i))) {
                    continue OutLoop;
                }
            }
            //find the match route
            return routeUrl;
        }

        return null;
    }

    /**
     * find the key value in the path and set them in the intent
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

    private Intent setOptionParams(String url, Intent intent) {
        Map<String, String> queryParams = RouterUtils.getQueryParameter(url);
        for (String key : queryParams.keySet()) {
            intent.putExtra(key, queryParams.get(key));
        }

        return intent;
    }

    private Intent setExtras(Bundle bundle, Intent intent) {
        intent.putExtras(bundle);
        return intent;
    }

    @Nullable
    private Intent match(Class<?> from, ActivityRoute route) {
        String matchedRoute = findMatchedRoute(route);
        if (matchedRoute == null) {
            return null;
        }
        Class<? extends Activity> matchedActivity = mRouteTable.get(matchedRoute);
        Intent intent = new Intent(mContext, matchedActivity);
        mHistoryCaches.add(new HistoryItem(from, matchedActivity));
        //find the key value in the path
        intent = setKeyValueInThePath(matchedRoute, route.getUrl(), intent);
        intent = setOptionParams(route.getUrl(), intent);
        intent = setExtras(route.getExtras(), intent);
        intent.putExtra(KEY_URL, route.getUrl());
        return intent;
    }


    public static String getKeyUrl() {
        return KEY_URL;
    }

    public List<HistoryItem> getRouteHistories() {
        return mHistoryCaches;
    }

    @Override
    public IRoute getRoute(String url) {
        return new ActivityRoute.Builder(this).setUrl(url).build();
    }

    public List<String> getMatchSchemes() {
        return MATCH_SCHEMES;
    }

    public void setMatchScheme(String scheme) {
        MATCH_SCHEMES.clear();
        MATCH_SCHEMES.add(scheme);
    }

    public void setMatchSchemes(String... schemes) {
        MATCH_SCHEMES.clear();
        List<String> list = Arrays.asList(schemes);
        list.remove("");
        list.remove(null);
        MATCH_SCHEMES.addAll(list);
    }

    public void addMatchSchemes(String scheme) {
        MATCH_SCHEMES.add(scheme);
    }
}
