package gs.com.ycas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import gs.com.ycas.BaseActivity;
import gs.com.ycas.R;

public class LandingActivity extends BaseActivity implements View.OnClickListener {


    Button btnUser, btnVolunteer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(this);
        btnVolunteer = findViewById(R.id.btnVolunteer);
        btnVolunteer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String operateAs = "";
        if (view.getId() == btnUser.getId()){
            operateAs = "User";
        }
        else if (view.getId() == btnVolunteer.getId()){
            operateAs = "Volunteer";
        }
        Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
        intent.putExtra("user", operateAs);
        startActivity(intent);
        //finish();
    }
}
