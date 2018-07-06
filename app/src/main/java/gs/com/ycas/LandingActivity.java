package gs.com.ycas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends BaseActivity implements View.OnClickListener {


    Button btnUser, btnVolunteer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(this);
        btnVolunteer = findViewById(R.id.btnVolunteer);
        btnVolunteer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String operateAs;
        if (view.getId() == btnUser.getId()){
            operateAs = "User";
        }
        else if (view.getId() == btnVolunteer.getId()){
            operateAs = "Volunteer";
        }
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
