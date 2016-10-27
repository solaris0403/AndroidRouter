package com.tony.router.route;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import com.tony.router.router.IRouter;

import java.lang.ref.WeakReference;

/**
 * Created by tony on 10/26/16.
 */
public class ActivityRoute extends AbsRoute {
    public static final int START_ACTIVITY = 0;
    public static final int START_ACTIVITY_FOR_RESULT = 1;
    private int mStartType = 0;
    //Activity requestCode
    private int mRequestCode = 0;
    //Intent Bundle
    private Bundle mExtras;
    //Intent flags
    private int mFlags = 0;
    //Current Activity
    private WeakReference<Activity> mActivity;

    public ActivityRoute(String url) {
        super(url);
    }

    public Bundle getExtras() {
        return mExtras;
    }

    public void setExtras(Bundle extras) {
        this.mExtras = mExtras;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public int getStartType() {
        return mStartType;
    }

    public void setFlags(int flags) {
        this.mFlags = flags;
    }

    public int getFlags() {
        return mFlags;
    }

    public Activity getActivity(){
        if(mActivity != null && mActivity.get() != null){
            return mActivity.get();
        } else {
            return null;
        }
    }
    /**
     * 默认方式启动
     * @return
     */
    public ActivityRoute startActivity(Activity activity){
        mStartType = START_ACTIVITY;
        mActivity = new WeakReference<>(activity);
        return this;
    }

    public ActivityRoute startActivityForResult(Activity activity, int requestCode){
        mRequestCode = requestCode;
        mStartType = START_ACTIVITY_FOR_RESULT;
        mActivity = new WeakReference<>(activity);
        return this;
    }

    public static class Builder {
        private String mUrl;
        private IRouter mRouter;
        private Bundle mBundle;
        private Activity mActivity;
        private int mStartType = 0;
        private int mRequestCode = 0;
        private int mFlags = 0;


        public Builder(IRouter router) {
            mBundle = new Bundle();
            mRouter = router;
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder putParcelable(String key, Parcelable value) {
            mBundle.putParcelable(key, value);
            return this;
        }

        public Builder putInt(String key, int value) {
            mBundle.putInt(key, value);
            return this;
        }

        public Builder putDouble(String key, double value) {
            mBundle.putDouble(key, value);
            return this;
        }

        public Builder putFloat(String key, float value) {
            mBundle.putFloat(key, value);
            return this;
        }

        public Builder putChar(String key, char value) {
            mBundle.putChar(key, value);
            return this;
        }

        public Builder putCharSequence(String key, CharSequence value) {
            mBundle.putCharSequence(key, value);
            return this;
        }

        public Builder putString(String key, String value) {
            mBundle.putString(key, value);
            return this;
        }

        public Builder putLong(String key, long value) {
            mBundle.putLong(key, value);
            return this;
        }

        public Builder putAll(Bundle extra) {
            mBundle.putAll(extra);
            return this;
        }

        public Builder setFlags(int flags){
            this.mFlags = flags;
            return this;
        }

        /**
         * 默认方式启动
         * @return
         */
        public ActivityRoute.Builder startActivity(Activity activity){
            mStartType = START_ACTIVITY;
            mActivity = activity;
            return this;
        }

        public ActivityRoute.Builder startActivityForResult(Activity activity, int requestCode){
            mRequestCode = requestCode;
            mStartType = START_ACTIVITY_FOR_RESULT;
            mActivity = activity;
            return this;
        }


        public ActivityRoute build() {
            ActivityRoute route = new ActivityRoute(mUrl);
            switch (mStartType) {
                case START_ACTIVITY:
                    route.startActivity(mActivity);
                    break;
                case START_ACTIVITY_FOR_RESULT:
                    route.startActivityForResult(mActivity, mRequestCode);
                    break;
                default:
                    route.startActivity(mActivity);
                    break;
            }
            route.setExtras(mBundle);
            route.setFlags(mFlags);
            return route;
        }
    }
}
