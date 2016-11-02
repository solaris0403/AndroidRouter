package tony.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tony.router.annotation.RouterMap;
import com.tony.router.annotation.RouterMapUtil;

@RouterMap("")
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
//                Router.open("activity://second?a=1");
                Log.e("123", String.valueOf(RouterMapUtil.getAllClassByAnnotation(RouterMap.class, "tony.androidrouter").size()));
            }
        });
    }
}
