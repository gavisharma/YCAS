package gs.com.ycas.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gs.com.ycas.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button forgetPasswordButton;
    private EditText fieldEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        forgetPasswordButton = findViewById(R.id.button_forget);
        fieldEmail = findViewById(R.id.field_email);

        forgetPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LandingActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        String emailAddress = fieldEmail.getText().toString();
        if (!(emailAddress.isEmpty() && emailAddress.equals(null))){
            mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, LandingActivity.class));
                    } else{
                        Toast.makeText(ForgotPasswordActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else
            Toast.makeText(this, "Kindly enter email address", Toast.LENGTH_SHORT).show();
    }
}
