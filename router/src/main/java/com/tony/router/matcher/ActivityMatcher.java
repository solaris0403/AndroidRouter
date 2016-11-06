package com.tony.router.matcher;

/**
 * Activity匹配器  使用职责链  优先匹配手动设置的 在匹配manifest
 */
public abstract class ActivityMatcher extends AbsMatcher {
    protected AbsMatcher matcher;
    public void setMatcher(AbsMatcher matcher){
        this.matcher = matcher;
    }
}
