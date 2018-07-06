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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        if (view.getId() == btnUser.getId()){
            intent.putExtra(KEY_OPERATOR, USER_OPERATOR);
        }
        else if (view.getId() == btnVolunteer.getId()){
            intent.putExtra(KEY_OPERATOR, VOLUNTEER_OPERATOR);
        }
        startActivity(intent);
        finish();
    }
}
