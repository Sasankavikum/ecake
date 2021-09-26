package com.example.cake;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRegister extends AppCompatActivity {

    private EditText adminName;
    private EditText adminEmail;
    private EditText adminShopName;
    private EditText phoneNumber;
    private EditText password;
    private EditText conformPassword;
    private Button adminRegisterButton;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Admin");
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        registerAdmin();
    }

    private void registerAdmin() {
        adminName = findViewById(R.id.admin_register_name);
        adminEmail = findViewById(R.id.admin_register_email);
        adminShopName = findViewById(R.id.admin_shop_name);
        phoneNumber = findViewById(R.id.admin_register_pNumber);
        password = findViewById(R.id.admin_register_password);
        conformPassword = findViewById(R.id.admin_register_conformPassword);
        adminRegisterButton = findViewById(R.id.admin_register_button);

        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = adminName.getText().toString().trim();
                final String email = adminEmail.getText().toString().trim();
                final String shopName = adminShopName.getText().toString().trim();
                final String adminPhone = phoneNumber.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                final String confPass = conformPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    adminName.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    adminEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(shopName)) {
                    adminShopName.setError("Shop name is required");
                    return;
                }
                if (TextUtils.isEmpty(adminPhone)) {
                    phoneNumber.setError("Phone number is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(confPass)) {
                    conformPassword.setError("Please conform the password");
                    return;
                }
                if (!TextUtils.equals(pass, confPass)) {
                    conformPassword.setError("Passwords are not match");
                    return;
                }

                dialog.setMessage("Sending data...");
                dialog.show();

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                            userId = auth.getCurrentUser().getUid();
                            Admin newAdmin = new Admin(name, email, shopName, adminPhone, pass);
                            reference.child(userId).setValue(newAdmin);
                            startActivity(new Intent(getApplicationContext(), AdminHome.class));


                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}