package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContinuePayActivity extends AppCompatActivity {

    private TextView name_con, phone_con, address_con, town_con;
    Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_pay);

        setContentView(R.layout.activity_continue_pay);
        getSupportActionBar().setTitle("Continue Payment");

        name_con = findViewById(R.id.text_name);
        phone_con = findViewById(R.id.text_phone_number);
        address_con = findViewById(R.id.text_address);
        town_con = findViewById(R.id.text_town);
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

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContinuePayActivity.this, com.example.cake_sasanka.CardDetailsActivity.class);
                intent.putExtra("keyid",id);
                startActivity(intent);
            }
        });
    }
}