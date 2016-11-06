package com.tony.router.matcher;

import android.content.Context;

import com.tony.router.router.ActivityRouter;
import com.tony.router.util.RouterLogUtils;
import com.tony.router.util.RouterUtils;

/**
 * Created by tony on 2016/11/5.
 */
public class ActivityManifestMatcher extends ActivityMatcher{
    private boolean isAllowEscape = true;

    public boolean isAllowEscape() {
        return isAllowEscape;
    }

    public void setAllowEscape(boolean allowEscape) {
        isAllowEscape = allowEscape;
    }

    @Override
    public boolean match(Context context, String url) {
        //避免匹配到手机的browser
        if ((!RouterUtils.getScheme(url).equals("http") && !RouterUtils.getScheme(url).equals("https")) && RouterUtils.queryActivity(context, isAllowEscape, url)){
            ActivityRouter.getInstance().addMatchSchemes(RouterUtils.getScheme(url));
            RouterLogUtils.i(url);
            return true;
        }
        return false;
    }
}
