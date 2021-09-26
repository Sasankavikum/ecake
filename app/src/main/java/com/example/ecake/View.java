package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View extends AppCompatActivity {

    TextView name,feedback;
    DatabaseReference databaseReference;
    Button buttondelete,buttonupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        buttonupdate = findViewById(R.id.button);
        buttondelete = findViewById(R.id.delete);



        buttondelete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {


                databaseReference= FirebaseDatabase.getInstance().getReference().child("FeedBack");
                databaseReference.child("User1").removeValue();

                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();


            }
        });


        buttonupdate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent=new Intent(getApplicationContext(), feedback_update.class);
                startActivity(intent);
            }
        });






        feedback=findViewById(R.id.textViewfeedback);
        name=findViewById(R.id.textViewname);
          databaseReference= FirebaseDatabase.getInstance().getReference().child("FeedBack").child("User1");
          databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String feedbacks=dataSnapshot.child("feedback").getValue().toString();
                String names=dataSnapshot.child("name").getValue().toString();
               feedback.setText(feedbacks);
               name.setText(names);

           }

         @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
    }
       });

    }
}