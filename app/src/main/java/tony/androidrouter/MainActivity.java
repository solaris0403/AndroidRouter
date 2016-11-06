package tony.androidrouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tony.router.Router;
import com.tony.router.annotation.RouterMap;
import com.tony.router.router.IFilter;
import com.tony.router.util.RouterLogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

@RouterMap(value = "zx://activity/map")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_activity_init)
    Button mBtnActivityInit;
    @BindView(R.id.btn_activity_manifest)
    Button mBtnActivityManifest;
    @BindView(R.id.btn_browser)
    Button mBtnBrowser;
    @BindView(R.id.btn_activity_init_filter)
    Button mBtnActivityInitFilter;
    @BindView(R.id.btn_activity_manifest_filter)
    Button mBtnActivityManifestFilter;
    @BindView(R.id.btn_browser_filter)
    Button mBtnBrowserFilter;
    @BindView(R.id.btn_activity_normal)
    Button mBtnActivityNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBtnActivityNormal.setOnClickListener(this);
        mBtnActivityInit.setOnClickListener(this);
        mBtnActivityManifest.setOnClickListener(this);
        mBtnBrowser.setOnClickListener(this);
        mBtnActivityInitFilter.setOnClickListener(this);
        mBtnActivityManifestFilter.setOnClickListener(this);
        mBtnBrowserFilter.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        long start;
        switch (view.getId()) {
            case R.id.btn_activity_normal:
                start = System.currentTimeMillis();
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_activity_init:
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "activity://init");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_activity_manifest:
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "activity://activity/manifest");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_browser:
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "http://www.baidu.com");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_activity_init_filter:
                Router.setFilter(new IFilter() {
                    @Override
                    public String doFilter(String url) {
                        if ("zx://activity/init".equals(url)) {
                            url = "zx://activity/filter";
                        }
                        return url;
                    }
                });
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "zx://activity/init");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_activity_manifest_filter:
                Router.setFilter(new IFilter() {
                    @Override
                    public String doFilter(String url) {
                        if ("zx://activity/manifest".equals(url)) {
                            url = "zx://activity/filter";
                        }
                        return url;
                    }
                });
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "zx://activity/manifest");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
            case R.id.btn_browser_filter:
                Router.setFilter(new IFilter() {
                    @Override
                    public String doFilter(String url) {
                        if ("http://www.baidu.com".equals(url)) {
                            url = "http://www.sina.com";
                        }
                        return url;
                    }
                });
                start = System.currentTimeMillis();
                Router.open(MainActivity.this, "http://www.baidu.com");
                RouterLogUtils.i(System.currentTimeMillis() - start);
                break;
        }
    }
}
