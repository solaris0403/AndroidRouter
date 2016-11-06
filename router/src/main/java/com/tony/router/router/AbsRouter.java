package com.tony.router.router;

/**
 * 空实现
 */
public abstract class AbsRouter implements IRouter {
    protected IFilter mFilter;

    public void setFilter(IFilter filter) {
        this.mFilter = filter;
    }
}
