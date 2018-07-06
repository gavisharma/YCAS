package gs.com.ycas.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import gs.com.ycas.BaseActivity;
import gs.com.ycas.R;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Context context = this;
        Thread delay = new Thread(){
            public  void run(){
                try {
                    sleep(3000);
                } catch (Exception e){
                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                } finally {
                    startActivity(new Intent(context,LandingActivity.class));
                    finish();
                }
            }
        };
        delay.start();
    }
}
