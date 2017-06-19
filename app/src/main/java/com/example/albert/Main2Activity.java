package com.example.albert;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    private ListView tTimeView;
    private TeaAdapter adapter;
    private List<Tea> listTea;

    private String userId;
    private int numTea;

    @BindView(R.id.add_tea) Button mAddTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        userId = HomeActivity.userId;

        tTimeView = (ListView) findViewById(R.id.tea_time);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        ButterKnife.bind(this);

        mAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTea();
            }
        });

        tTimeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mEditIntent = new Intent(Main2Activity.this, EditTea.class);
                mEditIntent.putExtra("position", "" + (position + 1));
                startActivity(mEditIntent);
            }
        });

        tTimeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), "delete1 " + numTea, Toast.LENGTH_SHORT).show();
                numTea--;
//                Toast.makeText(getBaseContext(), "delete2 " + numTea, Toast.LENGTH_SHORT).show();
                listTea.remove(position);
//                Toast.makeText(getBaseContext(), "delete3 " + numTea, Toast.LENGTH_SHORT).show();
                Main2Activity.this.adapter.notifyDataSetChanged();
//                Toast.makeText(getBaseContext(), "delete4 " + numTea, Toast.LENGTH_SHORT).show();
                mFirebaseDatabase.child("teas").child(userId).child(""+(position+1)).setValue(null);
//                Toast.makeText(getBaseContext(), "delete5 " + numTea, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        printTea();

//        Toast.makeText(getBaseContext(), "weird " + numTea, Toast.LENGTH_SHORT).show();
    }

    /**
     * Counts how many children this node has, and prints the user's tea.
     * @param null
     * @return null
     */
    private void printTea()
    {
        mFirebaseDatabase.child("teas").child(userId).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTea = new ArrayList<>();
                numTea = (int)(dataSnapshot.getChildrenCount());
//                Toast.makeText(getBaseContext(), "real " + numTea, Toast.LENGTH_SHORT).show();
                if(numTea == 0){}
                else {
                    for (int i = 1; i <= numTea; i++) {
                        mFirebaseDatabase.child("teas").child(userId).child("" + i).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Tea tea = new Tea(dataSnapshot.getValue().toString());
                                listTea.add(tea);
                                adapter = new TeaAdapter(Main2Activity.this, listTea);
                                tTimeView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                }
            }
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private void addTea()
    {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog);
        d.setTitle("Add Tea");
        d.setCancelable(true);
        final EditText mTeaText = (EditText) d.findViewById(R.id.teaText);
        final EditText mTeaDes = (EditText) d.findViewById(R.id.teaDescription);
//        Toast.makeText(getBaseContext(), "add " + numTea, Toast.LENGTH_SHORT).show();
        Button mAddBtn = (Button) d.findViewById(R.id.add_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTeaName = mTeaText.getText().toString();
                String sTeaDes = mTeaDes.getText().toString();
                writeNewTea(sTeaName, sTeaDes);
                Main2Activity.this.adapter.notifyDataSetChanged();

                d.dismiss();
            }
        });
        d.show();
    }

    private void writeNewTea(String name, String detail)
    {
        Tea teaName = new Tea();
        if(detail.isEmpty())
        {
            teaName.setName(name);
        }
        else
        {
            teaName.setName(name);
            teaName.setDetail(detail);
        }
        mFirebaseDatabase.child("teas").child(userId).child("" + ++numTea).setValue(teaName);
//        Toast.makeText(getBaseContext(), "new " + numTea, Toast.LENGTH_SHORT).show();

        Main2Activity.this.listTea.add(teaName);
    }
}
