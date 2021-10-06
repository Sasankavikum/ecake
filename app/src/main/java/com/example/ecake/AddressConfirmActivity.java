package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddressConfirmActivity extends AppCompatActivity {

    private TextView name_con, phone_con, address_con, town_con;
    Button Update_Address, Delete_Address, confirm_btn;
    private FirebaseFirestore db;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_confirm);

        setContentView(R.layout.activity_address_confirm);
        getSupportActionBar().setTitle("Address Confirm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name_con = findViewById(R.id.text_name);
        phone_con = findViewById(R.id.text_phone_number);
        address_con = findViewById(R.id.text_address);
        town_con = findViewById(R.id.text_town);
        Update_Address = findViewById(R.id.update_btn);
        Delete_Address = findViewById(R.id.delete_btn);
        confirm_btn = findViewById(R.id.confirm_payment);


        String name = getIntent().getStringExtra("keyname");
        String addresss = getIntent().getStringExtra("keyaddress");
        String phone = getIntent().getStringExtra("keyphone");
        String town = getIntent().getStringExtra("keytown");
        String id = getIntent().getStringExtra("keyid");


        name_con.setText(name);
        phone_con.setText(phone);
        address_con.setText(addresss);
        town_con.setText(town);

        db = FirebaseFirestore.getInstance();


        Update_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressConfirmActivity.this, com.example.cake_sasanka.UpdateAddressActivity.class);
                intent.putExtra("keyname",name);
                intent.putExtra("keyphone",phone);
                intent.putExtra("keyaddress",addresss);
                intent.putExtra("keytown",town);
                intent.putExtra("keyid",id);
                startActivity(intent);

            }
        });

        Delete_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Documents").document(id).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(AddressConfirmActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddressConfirmActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddressConfirmActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(AddressConfirmActivity.this, com.example.cake_sasanka.MainActivity.class);
                startActivity(intent);
            }

        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressConfirmActivity.this, com.example.cake_sasanka.CardDetailsActivity.class);
                intent.putExtra("keyid",id);
                startActivity(intent);
            }
        });
    }

}
