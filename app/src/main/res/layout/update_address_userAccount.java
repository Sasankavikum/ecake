package com.example.cake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class update_address_userAccount extends AppCompatActivity {


    EditText EditName,EditCont,EditStreet,EditEditStreet2,EditCity;
    Button updateAddress;
    String CardNo;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceAddress;
    private DatabaseReference databaseReference_Address_Update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        EditName=(EditText) findViewById(R.id.txtEditname);
        EditCont=(EditText) findViewById(R.id.txtEditCont);
        EditStreet=(EditText) findViewById(R.id.txteditStreet);
        EditEditStreet2=(EditText) findViewById(R.id.txteditStreet2);
        EditCity=(EditText) findViewById(R.id.txtEditCity);
        auth = FirebaseAuth.getInstance();
        databaseReferenceAddress=database.getReference().child("Addresss").child(auth.getUid());

        updateAddress=(Button)findViewById(R.id.btnupdateAddress);
        updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateAddress();
            }
        });

        databaseReferenceAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String City= dataSnapshot.child("cities").getValue().toString();
                String Name= dataSnapshot.child("names").getValue().toString();
                String Phn= dataSnapshot.child("phoneNumber").getValue().toString();
                String Address1= dataSnapshot.child("street1").getValue().toString();
                String Address2= dataSnapshot.child("street3").getValue().toString();

                EditName.setText(Name);
                EditStreet.setText(Address1);
                EditEditStreet2.setText(City);
                EditCity.setText(City);
                EditCont.setText(Phn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    private void updateAddress(){




        databaseReference_Address_Update=database.getReference().child("Addresss").child(auth.getUid());
        String BuyerName=EditName.getText().toString();
        String PhoneNumber=EditCont.getText().toString();
        String Street_one=EditStreet.getText().toString();
        String Street_two=EditEditStreet2.getText().toString();
        String Cit=EditCity.getText().toString();

        if (!BuyerName.isEmpty()){
            if(!PhoneNumber.isEmpty()){
                if(!Street_one.isEmpty()){
                    if (!Cit.isEmpty()){

                        Map<String,Object> updateValues=new HashMap<>();
                        updateValues.put("cities",Cit);
                        updateValues.put("names",BuyerName);
                        updateValues.put("phoneNumber",PhoneNumber);
                        updateValues.put("street1",Street_one);
                        updateValues.put("street3",Street_two);

                        databaseReferenceAddress.updateChildren(updateValues);

                        Intent intent=new Intent(getApplicationContext(), user_profile.class);
                        intent.putExtra("CardNo",CardNo);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Datainserted", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(getApplicationContext(), "City required", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Street", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Contact number required", Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(getApplicationContext(), "name required", Toast.LENGTH_SHORT).show();



    }
}