package com.tony.router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tony.router.RouterManager;
import com.tony.router.exception.RouteNotFoundException;
import com.tony.router.matcher.ActivityManifestMatcher;
import com.tony.router.matcher.ActivitySchemeMatcher;
import com.tony.router.matcher.IMatcherHandler;
import com.tony.router.route.ActivityRoute;
import com.tony.router.route.IRoute;
import com.tony.router.util.RouterLogUtils;
import com.tony.router.util.RouterUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tony on 10/26/16.
 */
public class ActivityRouter extends AbsRouter implements IMatcherHandler {
    private static ActivityRouter mActivityRouter = new ActivityRouter();
    //目前只有两个匹配器
    private ActivitySchemeMatcher mActivitySchemeMatcher;
    private ActivityManifestMatcher mActivityManifestMatcher;
    //如果没有提供context 当使用全局的ApplicationContent
    private Context mApplicationContent;

    public ActivityRouter() {
        //初始化匹配器
        mActivitySchemeMatcher = new ActivitySchemeMatcher();
        mActivityManifestMatcher = new ActivityManifestMatcher();
        mActivitySchemeMatcher.setMatcher(mActivityManifestMatcher);
    }

    //所有路由的表
    private Map<String, Class<? extends Activity>> mRouteTable = new HashMap<>();

    public static ActivityRouter getInstance() {
        return mActivityRouter;
    }

    /**
     * 初始化
     *
     * @param appContext  全局的context
     * @param initializer 路由表
     */
    public void init(Context appContext, ActivityRouteTableInitializer initializer) {
        mApplicationContent = appContext;
        if (initializer != null) {
            initializer.initRouterTable(mRouteTable);
        }
        //todo 直接针对mRouteTable不安全 后期可添加验证规则
    }

    @Override
    public IRoute getRoute(String url) {
        return new ActivityRoute.Builder(url).build();
    }

    @Override
    public boolean canOpen(String url) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
        }
        return mActivitySchemeMatcher.match(mApplicationContent, url);
    }

    @Override
    public boolean canOpen(IRoute route) {
        if (route != null) {
            String url = route.getUrl();
            if (mFilter != null) {
                url = mFilter.doFilter(url);
            }
            return mActivitySchemeMatcher.match(mApplicationContent, url);
        }
        return false;
    }

    @Override
    public boolean canOpen(Context context, String url) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
        }
        return mActivitySchemeMatcher.match(context, url);
    }

    @Override
    public Set<String> getMatchSchemes() {
        return mActivitySchemeMatcher.getMatchSchemes();
    }

    @Override
    public void setMatchSchemes(String... schemes) {
        mActivitySchemeMatcher.setMatchSchemes(schemes);
    }

    @Override
    public void addMatchSchemes(String scheme) {
        mActivitySchemeMatcher.addMatchSchemes(scheme);
    }

    @Override
    public void removeMatchSchemes(String scheme) {
        mActivitySchemeMatcher.removeMatchSchemes(scheme);
    }


    @Override
    public boolean open(IRoute route) {
        boolean canOpen = false;
        if (route instanceof ActivityRoute) {
            ActivityRoute activityRoute = (ActivityRoute) route;
            try {
                switch (activityRoute.getStartType()) {
                    case ActivityRoute.START_ACTIVITY:
                        openActivity(activityRoute.getActivity(), activityRoute);
                        canOpen = true;
                        break;
                    case ActivityRoute.START_ACTIVITY_FOR_RESULT:
                        openActivityForResult(activityRoute.getActivity(), activityRoute, activityRoute.getRequestCode());
                        canOpen = true;
                        break;
                    default:
                        openActivity(activityRoute.getActivity(), activityRoute);
                        canOpen = true;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            ActivityRoute activityRoute = (ActivityRoute) route;
            try {
                openActivity(context, activityRoute);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 打开的具体操作
     */
    protected void openActivity(Context context, ActivityRoute route) throws RouteNotFoundException {
        //创建intent
        Intent intent = match(route);
        if (intent == null) {
            throw new RouteNotFoundException(route.getUrl());
        }
        if (context == null) {
            //使用app启动  重新设置flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | route.getFlags());
            mApplicationContent.startActivity(intent);
        } else {
            //正常启动
            context.startActivity(intent);
        }
    }

    /**
     * 打开的具体操作
     */
    protected void openActivityForResult(Activity activity, ActivityRoute route, int requestCode) throws RouteNotFoundException {
        Intent intent = match(route);
        if (intent == null) {
            throw new RouteNotFoundException(route.getUrl());
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 在routrtable里面查找存才的activity class所对应的url
     * 如果没有的话则开始从xml中找
     * 如果有外部链接也被找到,则优先使用内部链接
     */
    @Nullable
    private String matchRouteTable(String url) {
        //根据route获取所有path
        List<String> givenPathSegs = RouterUtils.getPathSegments(url);
        OutLoop:
        //遍历mRouteTable中所有url
        for (String routeUrl : mRouteTable.keySet()) {
            //获得每一个url的path列表
            List<String> routePathSegs = RouterUtils.getPathSegments(routeUrl);
            //针对host进行match
            if (!TextUtils.equals(RouterUtils.getHost(routeUrl), RouterUtils.getHost(url))) {
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

    // TODO: 2016/11/5 需要重新优化匹配策略  如果成功的话加到table里面会提高效率?????????
    public Intent matchAndroidManifest(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (!mActivityManifestMatcher.isAllowEscape()) {
            intent.setPackage(mApplicationContent.getPackageName());
        }
        intent.setData(Uri.parse(url));
        ResolveInfo targetActivity = RouterUtils.queryActivity(mApplicationContent, intent);
        if (targetActivity == null) {
            return null;
        }
        String packageName = targetActivity.activityInfo.packageName;
        String className = targetActivity.activityInfo.name;
        intent.setClassName(packageName, className);
        return intent;
    }

    /**
     * 拼接url中存在的queryParams
     */
    private Intent putExtras(Intent intent, String url) {
        Map<String, String> queryParams = RouterUtils.getQueryParameter(url);
        for (String key : queryParams.keySet()) {
            intent.putExtra(key, queryParams.get(key));
        }
        return intent;
    }

    /**
     * 添加bundle
     */
    private Intent putBundle(Intent intent, Bundle bundle) {
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * 从routemap中找出可以match的class 并且赋值
     */
    private Intent match(ActivityRoute route) {
        Intent intent = null;
        //从routemap中寻找匹配的url
        String matchedRoute = matchRouteTable(mFilter != null ? mFilter.doFilter(route.getUrl()) : route.getUrl());
        if (matchedRoute != null) {
            //根据找出的rul找到activity的class
            Class<? extends Activity> matchedActivity = mRouteTable.get(matchedRoute);
            intent = new Intent(mApplicationContent, matchedActivity);
        } else {
            //可以通过xml查找
            intent = matchAndroidManifest(mFilter != null ? mFilter.doFilter(route.getUrl()) : route.getUrl());
            if (intent == null) {
                return null;
            }
        }
        //找到query中的值放到intent中
        intent = putExtras(intent, mFilter != null ? mFilter.doFilter(route.getUrl()) : route.getUrl());
        //找到设置的bundle放到intent中
        intent = putBundle(intent, route.getExtras());
        //setflags
        intent.setFlags(route.getFlags());
        //再网intent中放置一个标记key
        intent.putExtra(RouterManager.KEY_URL, mFilter != null ? mFilter.doFilter(route.getUrl()) : route.getUrl());
        RouterLogUtils.i(mFilter != null ? mFilter.doFilter(route.getUrl()) : route.getUrl());
        return intent;
    }

    public String getKeyUrl() {
        return RouterManager.KEY_URL;
    }

    public boolean isAllowEscape() {
        return mActivityManifestMatcher.isAllowEscape();
    }

    public void setAllowEscape(boolean allowEscape) {
        mActivityManifestMatcher.setAllowEscape(allowEscape);
    }
}
