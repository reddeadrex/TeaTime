package com.example.albert;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.BindView;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseUser firebaseUser;

    @BindView(R.id.input_name) EditText mNameText;
    @BindView(R.id.input_email) EditText mEmailText;
    @BindView(R.id.input_password) EditText mPasswordText;
    @BindView(R.id.input_reEnterPassword) EditText mReEnterPasswordText;
    @BindView(R.id.btn_signup) Button mSignUpBtn;
    @BindView(R.id.link_login) TextView mLoginBtn;

    protected static String userId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Tea Time");

        ButterKnife.bind(this);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mSignUpBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        // Implement signup.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            onSignupFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 2000);
                        }
                        else
                        {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//                            Toast.makeText(SignupActivity.this, R.string.auth_success, Toast.LENGTH_SHORT).show();
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            onSignupSuccess();
                                            progressDialog.dismiss();
                                        }
                                    }, 2000);
                        }
                    }
                });
    }


    public void onSignupSuccess() {
        mSignUpBtn.setEnabled(true);
        setResult(RESULT_OK, null);

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();

        createUser(name, email);

        Intent logInIntent = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(logInIntent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        mSignUpBtn.setEnabled(true);
    }

    private void createUser(String name, String email)
    {
        firebaseUser = mAuth.getCurrentUser();
        if(TextUtils.isEmpty(userId))
        {
            userId = firebaseUser.getUid();
        }
        User user = new User(name, email);
        mFirebaseDatabase.child(userId).setValue(user);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String reEnterPassword = mReEnterPasswordText.getText().toString();

        if (name.isEmpty())
        {
            mNameText.setError("Enter Name");
            valid = false;
        }
        else
        {
            mNameText.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("Enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            mPasswordText.setError("Greater than 4 characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || !(reEnterPassword.equals(password))) {
            mReEnterPasswordText.setError("Password does not match");
            valid = false;
        } else {
            mReEnterPasswordText.setError(null);
        }

        return valid;
    }

}