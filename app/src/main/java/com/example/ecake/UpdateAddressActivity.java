package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateAddressActivity extends AppCompatActivity {

    private EditText name_up, phone_up, address_up, town_up;
    Button update_btn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        setContentView(R.layout.activity_update_address);
        getSupportActionBar().setTitle("Update Address");

        name_up = findViewById(R.id.name_update);
        phone_up = findViewById(R.id.phone_number_update);
        address_up = findViewById(R.id.address_update);
        town_up = findViewById(R.id.town_update);
        update_btn = findViewById(R.id.update_address_btn);

        String name = getIntent().getStringExtra("keyname");
        String addresss = getIntent().getStringExtra("keyaddress");
        String phone = getIntent().getStringExtra("keyphone");
        String town = getIntent().getStringExtra("keytown");
        String id = getIntent().getStringExtra("keyid");

        name_up.setText(name);
        phone_up.setText(phone);
        address_up.setText(addresss);
        town_up.setText(town);

        db = FirebaseFirestore.getInstance();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_update = name_up.getText().toString();
                String address_update = address_up.getText().toString();
                String phone_number_update = phone_up.getText().toString();
                String town_update = town_up.getText().toString();
                String id1 = id;
                updateFirebase(id1, name_update, address_update, phone_number_update, town_update);

                Intent intent = new Intent(UpdateAddressActivity.this, ContinuePayActivity.class);
                intent.putExtra("keyname",name_update);
                intent.putExtra("keyphone",phone_number_update);
                intent.putExtra("keyaddress",address_update);
                intent.putExtra("keytown",town_update);
                intent.putExtra("keyid",id1);
                startActivity(intent);
            }
        });
    }

    public void updateFirebase(String id1, String name_update, String address_update, String phone_number_update, String town_update){
        db.collection("Documents").document(id1).update("name",name_update,"address",address_update,"phone",phone_number_update,"town",town_update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateAddressActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(UpdateAddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateAddressActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}