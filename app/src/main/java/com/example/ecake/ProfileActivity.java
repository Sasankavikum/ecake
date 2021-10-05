package com.example.ecake.Registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecake.MainActivity;
import com.example.ecake.R;
import com.example.ecake.activity_feedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {


    private Button signOut ,backToMain;
    private TextView prname, premail,prphone;
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prname = findViewById(R.id.prname);
        premail = findViewById(R.id.premail);
        prphone = findViewById(R.id.prphone);


        auth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userId = auth.getCurrentUser().getUid();

        //retreave data
        DocumentReference documentReference = fstore.collection("UserProfile").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                prname.setText(documentSnapshot.getString("Name"));
                prphone.setText(documentSnapshot.getString("Phone"));
                premail.setText(documentSnapshot.getString("Email"));

            }
        });


        backToMain=findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });





        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }
}