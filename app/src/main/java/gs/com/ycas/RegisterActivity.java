package gs.com.ycas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    User regUser;
    private static final String THIS_ACTIVITY = "RegisterActivity";
    Button buttonReg, buttonLogin;
    EditText fieldEmail, fieldPassword, fieldName, fieldLanguage, fieldContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fieldEmail = findViewById(R.id.field_email);
        fieldPassword = findViewById(R.id.field_password);
        fieldName = findViewById(R.id.field_name);
        fieldLanguage = findViewById(R.id.field_language);
        fieldContact = findViewById(R.id.field_contact);
        buttonLogin = findViewById(R.id.button_sign_in);
        buttonLogin.setOnClickListener(this);
        buttonReg = findViewById(R.id.button_sign_up);
        buttonReg.setOnClickListener(this);
        regUser = new User();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LandingActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sign_up: signUp();
                break;
            case R.id.button_sign_in:{
                startActivity(new Intent(getApplicationContext(), LandingActivity.class));
                finish();
            }
            break;
        }
    }

    private void signUp() {
        Log.d(THIS_ACTIVITY, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        regUser.email = fieldEmail.getText().toString();
        regUser.name = fieldName.getText().toString();
        regUser.contact = fieldContact.getText().toString();
        regUser.language = fieldLanguage.getText().toString();
        String password = fieldPassword.getText().toString();
        regUser.type = getIntent().getStringExtra(KEY_OPERATOR);

        getmAuth().createUserWithEmailAndPassword(regUser.email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(THIS_ACTIVITY, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void onAuthSuccess(FirebaseUser fbUser) {
        String username = usernameFromEmail(fbUser.getEmail());
        regUser.uid = fbUser.getUid();
        regUser.writeNewUser();
        startActivity(new Intent(getApplicationContext(), LandingActivity.class));
        // Go to MainActivity
//        readDataForUser(fbUser);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(fieldEmail.getText().toString())) {
            fieldEmail.setError("Please enter your email");
            result = false;
        } else if(fieldEmail.getText().toString().indexOf("@") == -1 || fieldEmail.getText().toString().indexOf(".") == -1) {
            fieldEmail.setError("Please enter a valid email");
            result = false;
        } else {
            fieldEmail.setError(null);
        }

        if (TextUtils.isEmpty(fieldPassword.getText().toString())) {
            fieldPassword.setError("Please enter your password");
            result = false;
        } else if (fieldPassword.getText().toString().length() < 6){
            fieldPassword.setError("Password should be 6 characters long");
            result = false;
        } else {
            fieldPassword.setError(null);
        }

        if (TextUtils.isEmpty(fieldName.getText().toString())) {
            fieldName.setError("Please enter your name");
            result = false;
        } else {
            fieldName.setError(null);
        }
        if (TextUtils.isEmpty(fieldContact.getText().toString())) {
            fieldContact.setError("Please enter your contact");
            result = false;
        } else {
            fieldContact.setError(null);
        }
        if (TextUtils.isEmpty(fieldLanguage.getText().toString())) {
            fieldLanguage.setError("Please enter your language");
            result = false;
        } else {
            fieldLanguage.setError(null);
        }
        return result;
    }
}