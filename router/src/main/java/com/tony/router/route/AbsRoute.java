package com.tony.router.route;

import com.tony.router.router.IRouter;
import com.tony.router.util.RouterUtils;

import java.util.List;
import java.util.Map;

/**
 * 生成基本元素
 */
public abstract class AbsRoute implements IRoute {
    private String mUrl;
    private String mScheme;
    private String mHost;
    private int mPort;
    private List<String> mPath;
    private Map<String, String> mQueryParameters;
    private IRouter mRouter;

    public AbsRoute(IRouter router, String url) {
        this.mRouter = router;
        this.mUrl = url;
        this.mScheme = RouterUtils.getScheme(url);
        this.mHost = RouterUtils.getHost(url);
        this.mPort = RouterUtils.getPort(url);
        this.mPath = RouterUtils.getPathSegments(url);
        this.mQueryParameters = RouterUtils.getQueryParameter(url);
        RouteFactory.getInstance().put(url, this);
    }

    @Override
    public IRouter getRouter() {
        return mRouter;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getScheme() {
        return mScheme;
    }

    @Override
    public String getHost() {
        return mHost;
    }

    @Override
    public int getPort() {
        return mPort;
    }

    @Override
    public List<String> getPath() {
        return mPath;
    }

    @Override
    public Map<String, String> getParameters() {
        return mQueryParameters;
    }

    @Override
    public boolean open() {
        return mRouter.open(this);
    }
}
