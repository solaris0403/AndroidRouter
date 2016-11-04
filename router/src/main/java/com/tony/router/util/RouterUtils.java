package com.tony.router.util;

import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by tony on 10/26/16.
 */
public class RouterUtils {
    private static final String TAG = "RouterUtils";

    public static String getScheme(String url) {
        return Uri.parse(url).getScheme();
    }

    public static String getHost(String url) {
        return Uri.parse(url).getHost();
    }

    public static List<String> getPathSegments(String url) {
        return Uri.parse(url).getPathSegments();
    }

    public static int getPort(String url) {
        return Uri.parse(url).getPort();
    }

    public static HashMap<String, String> getQueryParameter(String url) {
        HashMap<String, String> parameters = new HashMap<>();
        try {
            Uri uri = Uri.parse(url);
            Set<String> keys = uri.getQueryParameterNames();
            for (String key : keys) {
                parameters.put(key, uri.getQueryParameter(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameters;
    }

    public static String addQueryParameters(String url, String key, String value){
        try{
            Uri uri = Uri.parse(url);
            return uri.buildUpon().appendQueryParameter(key, value).build().toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

//
//    public static Route parseCurrentRoute(Context context) {
//        if (context != null && context instanceof Activity) {
//            Route route = Route.newInstance();
//            Intent startedIntent = ((Activity) context).getIntent();
//            Uri uri = startedIntent.getData();
//            if (uri == null)
//                return null;
//            route.scheme = TRouterUtil.getScheme(uri);
//            route.host = TRouterUtil.getHost(uri);
//            route.path = TRouterUtil.getPath(uri);
//            ResolveInfo resolveInfo = TRouterUtil.queryActivity(context, startedIntent);
//            if (resolveInfo == null)
//                return route;
//            route.packageName = resolveInfo.activityInfo.packageName;
//            route.activityName = resolveInfo.activityInfo.name;
//            return route;
//        }
//        return null;
//    }
//
//    /**
//     * 是否已经正确匹配到目标Activity，匹配成功则跳转，失败则自己处理
//     * 由于系统在匹配过程中，当匹配到多个时，会依匹配符合程度按循序排序好返回给我们，
//     * 不过这时候难免会有第三方包的Activity，需优先匹配本应用包中的Activity，本包中没有再返回系统最匹配的
//     */
//    public static ResolveInfo queryActivity(Context context, Intent intent) {
//        if (context == null || intent == null)
//            return null;
//        PackageManager packageManager = context.getApplicationContext().getPackageManager();
//        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent,
//                PackageManager.MATCH_DEFAULT_ONLY);
//        if (resolveInfoList == null || resolveInfoList.size() == 0)
//            return null;
//        int size = resolveInfoList.size();
//        if (size == 1)
//            return resolveInfoList.get(0);
//        String appPackageName = context.getApplicationContext().getPackageName();
//        for (int i = 0; i < size; i++) {
//            ResolveInfo resolveInfo = resolveInfoList.get(i);
//            String activityName = resolveInfo.activityInfo.name;
//            if (TextUtils.isEmpty(activityName))
//                continue;
//            if (activityName.startsWith(appPackageName)) {
//                return resolveInfo;
//            }
//        }
//        return resolveInfoList.get(0);
//    }
}
