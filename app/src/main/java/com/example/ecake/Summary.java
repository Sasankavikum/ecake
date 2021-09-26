package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Summary extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseRecyclerOptions<Products> options;
    private FirebaseRecyclerAdapter<Products, myproducts> adapter;
    private DatabaseReference reference = database.getReference().child("Cart");
    RecyclerView recyclerView;

    TextView CustomerName,CustomerAddress1,CustomerAddress2,CustomerPhone,cardNo;
    ImageButton editaddress;
    String Cardno;
    Button PlaceOrder;
    ImageButton deletecardDetails;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Address");

    private DatabaseReference databaseReferencecard = FirebaseDatabase.getInstance().getReference().child("Payment");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        CustomerName=(TextView) findViewById(R.id.txtuserName);
        CustomerAddress1=(TextView) findViewById(R.id.txtuserAddress);
        CustomerAddress2=(TextView) findViewById(R.id.txtuserAddress2);
        CustomerPhone=(TextView) findViewById(R.id.txtuserPhone);

        editaddress=(ImageButton) findViewById(R.id.txtimageeditaddress);
        PlaceOrder=(Button) findViewById(R.id.btnPlaceorder);
        cardNo=(TextView) findViewById(R.id.txtcardno);

        databaseReference = databaseReference.child(auth.getCurrentUser().getUid());
        databaseReferencecard = databaseReferencecard.child(auth.getCurrentUser().getUid());


        databaseReferencecard.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    Cardno = dataSnapshot.child("cardNo").getValue().toString();
                    cardNo.setText(Cardno);
                }
                catch (Exception e){
                    Cardno=null;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Cardno=null;
            }
        });

        deletecardDetails=(ImageButton) findViewById(R.id.btndeletepayment);


        final Loading loading=new Loading(Summary.this);



        deletecardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment Dialog= new DialogFragment();
                Dialog.show(getSupportFragmentManager(),"DialogFragment");

            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String City= dataSnapshot.child("cities").getValue().toString();
                String Name= dataSnapshot.child("names").getValue().toString();
                String Phn= dataSnapshot.child("phoneNumber").getValue().toString();
                String Address1= dataSnapshot.child("street1").getValue().toString();
                String Address2= dataSnapshot.child("street3").getValue().toString();

                CustomerName.setText(Name);
                CustomerAddress1.setText(Address1);
                CustomerAddress2.setText(City);
                CustomerPhone.setText(Phn);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), editAddress.class);
                startActivity(i);
            }
        });


        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.PaymentLoadingAnimation();

                Handler handler =new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismissDialog();
                        Intent i = new Intent(getApplicationContext(), purchaseSuccess.class);
                        startActivity(i);
                    }

                },5000);


            }
        });


    }
}