package com.example.albert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import com.example.albert.LoginActivity;
import com.example.albert.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private static Button button2;
    private static Button mLogoutBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mLogoutBtn = (Button)findViewById(R.id.btn_logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                if(mAuth.getCurrentUser() == null)
                {
                    Intent logOutIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logOutIntent);
                    finish();
                }
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