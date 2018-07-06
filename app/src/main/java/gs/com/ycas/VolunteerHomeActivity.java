package gs.com.ycas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VolunteerHomeActivity extends BaseActivity implements View.OnClickListener {

    Button btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        btnSignOut = findViewById(R.id.btn_sign_out);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSignOut.getId()){
            super.signOut();
        }
    }
}
