package com.tony.router;

import android.content.Context;

import com.tony.router.route.IRoute;
import com.tony.router.router.IActivityRouteTableInitializer;
import com.tony.router.router.IRouter;

public class Router {
    /**
     * 添加Router,通常在Application中初始化Router
     *
     * @param router
     */
    public static void addRouter(IRouter router) {
        RouterManager.getInstance().addRouter(router);
    }

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
    public static boolean open(String url) {
        return RouterManager.getInstance().open(url);
    }

    /**
     * 执行route
     *
     * @param route
     * @return 执行结果
     */
    public static boolean open(IRoute route) {
        return RouterManager.getInstance().open(route);
    }


    public static boolean open(Context context, String url){
        return RouterManager.getInstance().open(context, url);
    }

    /**
     * 清除缓存记录
     */
    public void clearRouteCache() {
    }

    /**
     * 设置缓存条数
     *
     * @param size
     */
    public void setRouteCacheSize(int size) {
    }


    /**
     * 通过url获得一个构造出来的Route
     *
     * @param url
     * @return
     */
    public static IRoute getRoute(String url) {
        return RouterManager.getInstance().getRoute(url);
    }


//    public static final String URL_ROUTER_REFERRER = "UrlRouter.REFERRER";
//    private Context mContext;
//    private Intent mIntent;
//    private boolean isAllowEscape;
//    private int mRequestCode;
//    private String mCategory;
//    private int[] mTransitionAnim;
//
//    private TRouter(Context context) {
//        if (context == null)
//            throw new IllegalArgumentException("context can not be null!");
//        this.mContext = context;
//        this.isAllowEscape = false;
//        this.mRequestCode = -1;
//        this.mIntent = new Intent(Intent.ACTION_VIEW);
//        this.mIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        TRouterUtil.setupReferrer(mContext, mIntent);
//    }
//
//    public static TRouter from(Context context) {
//        return new TRouter(context);
//    }
//
//    /**
//     * 在传递字符串、int、布尔类型时直接使用url拼接形式，涉及到其它复杂的数据类型时使用Bundle传递
//     *
//     * @param bundle
//     * @return
//     */
//    public final TRouter params(Bundle bundle) {
//        if (bundle == null)
//            return this;
//        mIntent.putExtras(bundle);
//        return this;
//    }
//
//    public final TRouter category(String category) {
//        mCategory = category;
//        return this;
//    }
//
//    public final TRouter transitionAnim(int enterAnim, int exitAnim) {
//        if (enterAnim < 0 || exitAnim < 0) {
//            mTransitionAnim = null;
//            return this;
//        }
//        mTransitionAnim = new int[2];
//        mTransitionAnim[0] = enterAnim;
//        mTransitionAnim[1] = exitAnim;
//        return this;
//    }
//
//    public final TRouter requestCode(int reqCode) {
//        mRequestCode = reqCode;
//        return this;
//    }
//
//    public final boolean jump(String url) {
//        return !TextUtils.isEmpty(url) && jump(Uri.parse(url));
//    }
//
//    public final TRouter allowEscape() {
//        isAllowEscape = true;
//        return this;
//    }
//
//    public final TRouter forbidEscape() {
//        isAllowEscape = false;
//        return this;
//    }
//
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
//
//    public final boolean jumpToMain(String url) {
//        return !TextUtils.isEmpty(url) && jumpToMain(Uri.parse(url));
//    }
//
//    public final boolean jumpToMain(Uri uri) {
//        mIntent.setAction(Intent.ACTION_MAIN);
//        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        return jump(uri);
//    }
//
//    public static Route getStartedRoute(Context context) {
//        return TRouterUtil.parseStartedRoute(context);
//    }
//
//    public static Route getCurrentRoute(Context context) {
//        return TRouterUtil.parseCurrentRoute(context);
//    }
}
