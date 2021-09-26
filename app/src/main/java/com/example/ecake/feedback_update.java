package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class feedback_update extends AppCompatActivity {

    EditText txtname,txtfeedbakc;
    Button submit,btnview,btnUpdate;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_update);

        txtname=findViewById(R.id.txtupdatename);
        txtfeedbakc=findViewById(R.id.txtupdatefeedback);
        submit=findViewById(R.id.btnUpdate);

        btnview=findViewById(R.id.btnview);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference= FirebaseDatabase.getInstance().getReference().child("FeedBack").child("User1");
                String name= txtname.getText().toString();
                String yourReview= txtfeedbakc.getText().toString();

                Map<String,Object> updatefeedback=new HashMap<>();
                updatefeedback.put("name",name);
                updatefeedback.put("feedback",yourReview);

                databaseReference.updateChildren(updatefeedback);
            }
        });


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),View.class);
                startActivity(intent);
            }
        });


    }
}