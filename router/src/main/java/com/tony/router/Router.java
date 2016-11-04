package com.tony.router;

import android.content.Context;

import com.tony.router.route.IRoute;
import com.tony.router.router.IActivityRouteTableInitializer;

/**
 * use class
 */
public class Router {
    /**
     *
     * @param context
     * @param initializer
     * @param scheme 可以添加多种scheme
     */
    public static synchronized void initActivityRouter(Context context, IActivityRouteTableInitializer initializer, String... scheme) {
        RouterManager.getInstance().initActivityRouter(context, initializer, scheme);
    }

    public static synchronized void initBrowserRouter(Context context) {
        RouterManager.getInstance().initBrowserRouter(context);
    }

    /**
     * 执行url
     *
     * @param url
     * @return 执行结果
     */
    public static synchronized boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    /**
     * 执行route
     *
     * @param route
     * @return 执行结果
     */
    public static synchronized boolean open(IRoute route) {
        return RouterManager.getInstance().open(route);
    }


    public static synchronized boolean open(Context context, String url){
        return RouterManager.getInstance().open(context, url);
    }

    /**
     * 通过url获得一个构造出来的Route
     *
     * @param url
     * @return
     */
    public static synchronized IRoute getRoute(String url) {
        return RouterManager.getInstance().getRoute(url);
    }

    /**
     * 针对相同url进行选择
     */
//    public final boolean jump(Uri uri) {
//        if (uri == null)
//            return false;
//        if (!isAllowEscape) {
//            mIntent.setPackage(mContext.getApplicationContext().getPackageName());
//        }
//        if (!TextUtils.isEmpty(mCategory)) {
//            mIntent.addCategory(mCategory);
//        }
//        mIntent.setData(uri);
//        ResolveInfo targetActivity = TRouterUtil.queryActivity(mContext, mIntent);
//        if (targetActivity == null)
//            return false;
//        String packageName = targetActivity.activityInfo.packageName;
//        String className = targetActivity.activityInfo.name;
//        mIntent.setClassName(packageName, className);
//        ComponentName targetComponentName = mIntent.getComponent();
//        if (!(mContext instanceof Activity)) {
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            ContextCompat.startActivities(mContext, new Intent[]{mIntent});
//            return true;
//        }
//        if (mContext instanceof Activity) {
//            ComponentName thisComponentName = ((Activity) mContext).getComponentName();
//            if (thisComponentName.equals(targetComponentName))
//                return true;
//            if (mRequestCode >= 0) {
//                ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, null);
//                return true;
//            }
//            ActivityCompat.startActivity((Activity) mContext, mIntent, null);
//            return true;
//        }
//        if (mTransitionAnim != null) {
//            ((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
//        }
//        return false;
//    }
}
