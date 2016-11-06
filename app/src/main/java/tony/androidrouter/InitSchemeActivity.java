package tony.androidrouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tony.router.annotation.RouterMap;

@RouterMap("activity://init")
public class InitSchemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent= getIntent();
        int a = intent.getIntExtra("key", 0);
        Bundle bundle = intent.getExtras();
    }
}
