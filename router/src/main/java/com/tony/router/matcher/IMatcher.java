package com.tony.router.matcher;

import android.content.Context;

import com.tony.router.route.IRoute;

/**
 * Created by tony on 2016/11/5.
 */
public interface IMatcher {
    boolean match(Context context, String url);

    // TODO: 2016/11/5 可以去掉 match的时候用url, open的时候才会用到route 
    boolean match(Context context, IRoute route);
}
