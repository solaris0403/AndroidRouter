package com.tony.router.matcher;

/**
 * Created by tony on 2016/11/5.
 */
public abstract class ActivityMatcher implements IMatcher{
    protected IMatcher matcher;
    public void setMatcher(IMatcher matcher){
        this.matcher = matcher;
    }
}
