package gs.com.ycas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
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
