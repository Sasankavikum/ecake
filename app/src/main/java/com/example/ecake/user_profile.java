package com.example.cake;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class user_profile extends AppCompatActivity {

    TextView Email,txtheaderName,name,contact;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceuser;
    private TextView address_name,address_street1,address_street2,address_city,address_contact,address_card_details;
    private DatabaseReference databaseReference_Payment;
    private DatabaseReference databaseReference_Address;


    ImageButton editaddresss,paymentDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        auth = FirebaseAuth.getInstance();
        databaseReferenceuser = database.getReference().child("User").child(auth.getUid());
        databaseReference_Address=database.getReference().child("Addresss").child(auth.getUid());
        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());



        Email=findViewById(R.id.Email);
        txtheaderName=findViewById(R.id.txtheaderName);
//        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact);


        address_name=findViewById(R.id.txtname);
        address_street1=findViewById(R.id.txtstreet1);
        address_street2=findViewById(R.id.street2);
        address_city=findViewById(R.id.City);
        address_contact=findViewById(R.id.addresscontact);
        address_card_details=findViewById(R.id.txtcard_details);
        paymentDelete=findViewById(R.id.paymentDelete);
        editaddresss=findViewById(R.id.editaddresss);

        edit_payment();
        edit_Adderss();

        databaseReferenceuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Emails= dataSnapshot.child("email").getValue().toString();
                String Name= dataSnapshot.child("name").getValue().toString();
                String contacts= dataSnapshot.child("phone").getValue().toString();

                Email.setText(Emails);
                txtheaderName.setText(Name);
//                name.setText(Name);
                contact.setText(contacts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Email.setText(null);
                txtheaderName.setText(null);
                name.setText(null);
                contact.setText(null);
            }
        });

        databaseReference_Payment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Cardno = dataSnapshot.child("cardNo").getValue().toString();
                address_card_details.setText(Cardno);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference_Address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Phn= dataSnapshot.child("phoneNumber").getValue().toString();
                String Name= dataSnapshot.child("names").getValue().toString();
                String Address1= dataSnapshot.child("street1").getValue().toString();
                String Address2= dataSnapshot.child("street3").getValue().toString();
                String City= dataSnapshot.child("cities").getValue().toString();

                address_name.setText(Name);
                address_street1.setText(Address1);
                address_street2.setText(Address2);
                address_city.setText(City);
                address_contact.setText(Phn);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private  void edit_Adderss(){

        editaddresss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), update_address_userAccount.class);
                startActivity(i);
            }
        });
    }
    private void edit_payment(){

        paymentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailogFragment_UserAccount Dialog= new DailogFragment_UserAccount();
                Dialog.show(getSupportFragmentManager(),"DialogFragment");
            }
        });

    }

    }

