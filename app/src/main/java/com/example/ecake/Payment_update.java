package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Payment_update extends AppCompatActivity {

    private EditText HolderName;
    private Button done;
    private DatabaseReference databaseReference;
    private EditText CardNo,MM,YY,CVV;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference_Payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        done = findViewById(R.id.btnaddtresspage);

        CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true);
        cardForm.expirationRequired(true);
        cardForm.cvvRequired(true);
        cardForm.setup(Payment_update.this);

        CVV = cardForm.getCvvEditText();
        CardNo = cardForm.getCardEditText();
        MM = cardForm.getExpirationDateEditText();
        HolderName = findViewById(R.id.txtEditname);


        auth = FirebaseAuth.getInstance();
        databaseReference_Payment=database.getReference().child("Payment").child(auth.getUid());






        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NameOfHolder = HolderName.getText().toString();
                String CardNumber = CardNo.getText().toString();
                String Month = MM.getText().toString();
                String SecurityCode = CVV.getText().toString();

                if (!NameOfHolder.isEmpty()){
                    if(!CardNumber.isEmpty()){
                        if(!Month.isEmpty()){
                            if (!SecurityCode.isEmpty()){
                                Payment_update();
                                Toast.makeText(Payment_update.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), user_profile.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(Payment_update.this, "Required Security Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Payment_update.this, "Required Date", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Payment_update.this, "Required Card Number", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(Payment_update.this, "Required Holder Name", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void Payment_update(){

        String NameOfHolder = HolderName.getText().toString();
        String CardNumber = CardNo.getText().toString();
        String Month = MM.getText().toString();
        String SecurityCode = CVV.getText().toString();

        Map<String,Object> updateValues=new HashMap<>();

        updateValues.put("cardNo",CardNumber);
        updateValues.put("holderName",NameOfHolder);
        updateValues.put("mm",Month);
        updateValues.put("cvv",SecurityCode);
        databaseReference_Payment.updateChildren(updateValues);

    }
}
