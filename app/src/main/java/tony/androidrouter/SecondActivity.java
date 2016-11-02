package tony.androidrouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent= getIntent();
        String a = intent.getStringExtra("a");
        Bundle bundle = intent.getExtras();
    }
}
