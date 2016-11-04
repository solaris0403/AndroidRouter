package tony.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tony.router.Router;
import com.tony.router.router.ActivityRouter;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button mBtnSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSecond = (Button) findViewById(R.id.btn_second);
        mBtnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Router.open(MainActivity.this, "activity://second");
//                ActivityRoute route = new ActivityRoute.Builder(ActivityRouter.getInstance()).build();
//                route.open();
//                Router.open(route);
//                Log.e("123", String.valueOf(RouterMapUtil.getAllClassByAnnotation(RouterMap.class, "tony.androidrouter").size()));
//                Random random = new Random();
//                Router.getRoute("zx://activity").open();
                Router.open(MainActivity.this, "zx://activity/second");
                Set set = ActivityRouter.getInstance().getMatchSchemes();
            }
        });
    }
}
