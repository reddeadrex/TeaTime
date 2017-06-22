package com.example.albert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTea extends AppCompatActivity {

    private TabHost mTabHost;
    private ImageView mImageView;
    private TextView mTeaName;
    private TextView mTeaDes;

    private Button mUpdateBtn;
    private ImageButton mImageBtn;
    private EditText mNameEdit;
    private EditText mDesEdit;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    private String userId;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tea);

        mImageView = (ImageView) findViewById(R.id.teaImage);
        mTeaName = (TextView) findViewById(R.id.teaTitle);
        mTeaDes = (TextView) findViewById(R.id.teaDes);
        userId = HomeActivity.userId;
        position = getIntent().getStringExtra("position");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        mUpdateBtn = (Button) findViewById(R.id.update);
        mImageBtn = (ImageButton) findViewById(R.id.teaImageBtn);
        mNameEdit = (EditText) findViewById(R.id.editTeaTitle);
        mDesEdit = (EditText) findViewById(R.id.editTeaDes);

        TabHost.TabSpec spec;

        mTabHost = (TabHost)findViewById(R.id.tabHost);
        mTabHost.setup();

        loadTea();

        spec = mTabHost.newTabSpec("Overview");
        spec.setContent(R.id.overview);
        spec.setIndicator("OVERVIEW");
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec("Edit");
        spec.setContent(R.id.edit);
        spec.setIndicator("EDIT");
        mTabHost.addTab(spec);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTea();
            }
        });
    }

    private void loadTea()
    {
        mFirebaseDatabase.child("teas").child(userId).child(position).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){}
                else
                    mTeaName.setText(dataSnapshot.getValue().toString());
                //Toast.makeText(getBaseContext(), "Name", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mFirebaseDatabase.child("teas").child(userId).child(position).child("detail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){}
                else
                    mTeaDes.setText(dataSnapshot.getValue().toString());
                //Toast.makeText(getBaseContext(), "Detail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTea()
    {
        String name = mNameEdit.getText().toString();
        String des = mDesEdit.getText().toString();

        if(name.isEmpty()){}
        else
        {
            mFirebaseDatabase.child("teas").child(userId).child(position).child("name").setValue(name);
        }
        if(des.isEmpty()){}
        else
        {
            mFirebaseDatabase.child("teas").child(userId).child(position).child("detail").setValue(des);
        }
    }
}
