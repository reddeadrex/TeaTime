package com.example.albert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
{
    protected static String userId = "";

    private Button button2;
    private Button mLogoutBtn;
    private TextView mUserTxt;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        if(LoginActivity.userId.isEmpty())
            userId = SignupActivity.userId;
        else
            userId = LoginActivity.userId;

        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mUserTxt = (TextView) findViewById(R.id.txt_user);
        mLogoutBtn = (Button)findViewById(R.id.btn_logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                userId = "";
                Toast.makeText(HomeActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                if(mAuth.getCurrentUser() == null)
                {
                    Intent logOutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(logOutIntent);
                    finish();
                }
            }
        });

        mFirebaseDatabase.child("users").child(userId).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserTxt.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        onClick2();
    }

    public void teaToast(View v)
    {
        onClick("I'll toast to that.");
    }

    public void onClick(String msg)
    {
        Toast tea = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        tea.show();
    }

    public void onClick2()
    {
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent("com.example.albert.Main2Activity");
                        startActivity(intent);
                    }
                }
        );
    }
}