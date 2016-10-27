package com.tony.router;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * 1、可取代使用startActivity、startActivityForResult跳转的情景，便于协同开发
 * 2、通过一串url可任意跳转到指定界面，使用应尽可能简单
 * 3、支持各种类型参数传递、界面转场动画
 * 4、可获取起跳界面的路径和当前界面路径，以便支持后期埋点等需求
 * 5、支持从H5到Native，Native到H5，这是Hybrid开发模式中常用到的需求
 * 6、对于push、浏览器外链跳转等可方便配置化，通过一个url来跳转指定界面
 * <p/>
 * 外置浏览器跳App内页面的处理:
 * <intent-filter>
 * <action android:name="android.intent.action.VIEW"/>
 * <category android:name="android.intent.category.DEFAULT"/>
 * <category android:name="android.intent.category.BROWSABLE"/>
 * </intent-filter>
 */
public class TRouter {
    public static final String URL_ROUTER_REFERRER = "UrlRouter.REFERRER";
    private Context mContext;
    private Intent mIntent;
    private boolean isAllowEscape;
    private int mRequestCode;
    private String mCategory;
    private int[] mTransitionAnim;

    private TRouter(Context context) {
        if (context == null)
            throw new IllegalArgumentException("context can not be null!");
        this.mContext = context;
        this.isAllowEscape = false;
        this.mRequestCode = -1;
        this.mIntent = new Intent(Intent.ACTION_VIEW);
        this.mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        TRouterUtil.setupReferrer(mContext, mIntent);
    }

    public static TRouter from(Context context) {
        return new TRouter(context);
    }

    /**
     * 在传递字符串、int、布尔类型时直接使用url拼接形式，涉及到其它复杂的数据类型时使用Bundle传递
     *
     * @param bundle
     * @return
     */
    public final TRouter params(Bundle bundle) {
        if (bundle == null)
            return this;
        mIntent.putExtras(bundle);
        return this;
    }

    public final TRouter category(String category) {
        mCategory = category;
        return this;
    }

    public final TRouter transitionAnim(int enterAnim, int exitAnim) {
        if (enterAnim < 0 || exitAnim < 0) {
            mTransitionAnim = null;
            return this;
        }
        mTransitionAnim = new int[2];
        mTransitionAnim[0] = enterAnim;
        mTransitionAnim[1] = exitAnim;
        return this;
    }

    public final TRouter requestCode(int reqCode) {
        mRequestCode = reqCode;
        return this;
    }

    public final boolean jump(String url) {
        return !TextUtils.isEmpty(url) && jump(Uri.parse(url));
    }

    public final TRouter allowEscape() {
        isAllowEscape = true;
        return this;
    }

    public final TRouter forbidEscape() {
        isAllowEscape = false;
        return this;
    }

    public final boolean jump(Uri uri) {
        if (uri == null)
            return false;
        if (!isAllowEscape) {
            mIntent.setPackage(mContext.getApplicationContext().getPackageName());
        }
        if (!TextUtils.isEmpty(mCategory)) {
            mIntent.addCategory(mCategory);
        }
        mIntent.setData(uri);
        ResolveInfo targetActivity = TRouterUtil.queryActivity(mContext, mIntent);
        if (targetActivity == null)
            return false;
        String packageName = targetActivity.activityInfo.packageName;
        String className = targetActivity.activityInfo.name;
        mIntent.setClassName(packageName, className);
        ComponentName targetComponentName = mIntent.getComponent();
        if (!(mContext instanceof Activity)) {
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ContextCompat.startActivities(mContext, new Intent[]{mIntent});
            return true;
        }
        if (mContext instanceof Activity) {
            ComponentName thisComponentName = ((Activity) mContext).getComponentName();
            if (thisComponentName.equals(targetComponentName))
                return true;
            if (mRequestCode >= 0) {
                ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, null);
                return true;
            }
            ActivityCompat.startActivity((Activity) mContext, mIntent, null);
            return true;
        }
        if (mTransitionAnim != null) {
            ((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
        }
        return false;
    }

    public final boolean jumpToMain(String url) {
        return !TextUtils.isEmpty(url) && jumpToMain(Uri.parse(url));
    }

    public final boolean jumpToMain(Uri uri) {
        mIntent.setAction(Intent.ACTION_MAIN);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return jump(uri);
    }

    public static Route getStartedRoute(Context context) {
        return TRouterUtil.parseStartedRoute(context);
    }

    public static Route getCurrentRoute(Context context) {
        return TRouterUtil.parseCurrentRoute(context);
    }
}
