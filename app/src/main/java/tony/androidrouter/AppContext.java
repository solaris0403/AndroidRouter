package tony.androidrouter;

import android.app.Activity;
import android.app.Application;

import com.tony.router.Router;
import com.tony.router.router.ActivityRouteTableInitializer;

import java.util.Map;

/**
 * Created by tony on 11/2/16.
 */
public class AppContext extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Router.initActivityRouter(this, new ActivityRouteTableInitializer() {
            @Override
            public void initRouterTable(Map<String, Class<? extends Activity>> router) {
//                router.put("zx://activity/main", MainActivity.class);

                router.put("zx://activity/init", InitSchemeActivity.class);
                router.put("zx://activity/manifest", ManifestActivity.class);
                router.put("zx://activity/filter", FilterActivity.class);
            }
        }, "zx");
        Router.initBrowserRouter(this);
    }
}
