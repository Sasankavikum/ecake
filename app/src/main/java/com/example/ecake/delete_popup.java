package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delete_popup extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Button Delete,Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_popup);

        Delete=(Button) findViewById(R.id.btnDelete);
        Cancel=(Button) findViewById(R.id.btnCancel);


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Address").child("User2");
                databaseReference.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(delete_popup.this, "Delete Payment ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Payment.class);
                        startActivity(i);
                    }
                });

//                Toast.makeText(delete_popup.this, "Delete Payment ", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(), cake_pages.class);
//                startActivity(i);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Summary.class);
                startActivity(i);
            }
        });


    }
}