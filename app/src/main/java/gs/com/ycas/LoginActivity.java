package gs.com.ycas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String THIS_ACTIVITY = "SignInActivity";
    private static final String PROCESS_REGISTER = "REGISTER";
    private static final String PROCESS_LOGIN = "LOGIN";

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        mSignInButton = findViewById(R.id.button_sign_in);
        mSignUpButton = findViewById(R.id.button_sign_up);
        resetPasswordButton = findViewById(R.id.button_reset_password);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        resetPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (getCurrentUser() != null) {
            onAuthSuccess(getCurrentUser());
        }
    }

    private void signIn() {
        Log.d(THIS_ACTIVITY, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        getmAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(THIS_ACTIVITY, "signIn:onComplete:" + task.isSuccessful());
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(LoginActivity.this, "Sign In Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void onAuthSuccess(FirebaseUser fbUser) {
        final String username = usernameFromEmail(fbUser.getEmail());
        boolean volunteerUser = false;
        // Go to MainActivity
        getmDatabase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> volunteers = new ArrayList<User>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    if (user != null && !user.uid.equals(getUid()) && !user.type.equalsIgnoreCase("blind")){
                        Log.w("User", "Name "+user.name);
                        volunteers.add(user);
                    }
                }
                Volunteer.getInstance().setVolunteers(volunteers);
                User currentUser = dataSnapshot.child(getUid()).getValue(User.class);
                if (currentUser.type.equalsIgnoreCase("blind")){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    Toast.makeText(LoginActivity.this, "User: "+username, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), VolunteerHomeActivity.class));
                    Toast.makeText(LoginActivity.this, "Volunteer: "+username, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
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

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Please enter your email");
            result = false;
        } else if(mEmailField.getText().toString().indexOf("@") == -1 || mEmailField.getText().toString().indexOf(".") == -1) {
            mEmailField.setError("Please enter a valid email");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Please enter your password");
            result = false;
        } else if (mPasswordField.getText().toString().length() < 6){
            mPasswordField.setError("Password should be 6 characters long");
            result = false;
        } else {
            mPasswordField.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        } else if (i == R.id.button_sign_up) {
            Intent nextIntent = new Intent(getApplicationContext(), RegisterActivity.class);
            nextIntent.putExtra(KEY_OPERATOR, getIntent().getStringExtra(KEY_OPERATOR));
            startActivity(nextIntent);
            finish();
        } else if (i == R.id.button_reset_password) {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            finish();
        }
    }
}