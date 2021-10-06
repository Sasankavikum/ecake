package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;

public class CardDetailsActivity extends AppCompatActivity {

    private EditText card_number, expire_date, cvv;
    Button confirm_payment_btn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        setContentView(R.layout.activity_card_details);
        getSupportActionBar().setTitle("Card Details");

        card_number = findViewById(R.id.card_number_input);
        expire_date = findViewById(R.id.expire_date_input);
        cvv = findViewById(R.id.cvv_input);
        confirm_payment_btn = findViewById(R.id.confirm_pay_btn);


        db = FirebaseFirestore.getInstance();

        //save data in firebase on button click
        confirm_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InsertCardData();

            }
        });
    }

    public void InsertCardData() {
        String cardNumber = card_number.getText().toString();
        String expireDate = expire_date.getText().toString();
        String cvv_number = cvv.getText().toString();
        String id = getIntent().getStringExtra("keyid");

        if (TextUtils.isEmpty(cardNumber)) {
            Toast.makeText(this, "Please write your Card Number..", Toast.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(expireDate)) {
            Toast.makeText(this, "Please write your Expire Date..", Toast.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(cvv_number)) {
            Toast.makeText(this, "Please enter your CVV..", Toast.LENGTH_SHORT);
        }else if(!cvv.getText().toString().matches("[0-9]{3}")) {
            cvv.setError("Enter valid CVV");
        }
        else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("card number", cardNumber);
            map.put("expire date", expireDate);
            map.put("cvv", cvv_number);
            map.put("id", id);

            db.collection("Payments").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CardDetailsActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CardDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(CardDetailsActivity.this, ThankPaymentActivity.class);
            startActivity(intent);
        }
    }
}