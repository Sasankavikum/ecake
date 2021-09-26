package com.example.cake;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserRegistration extends AppCompatActivity {

    //Layout variables

    private EditText userName;
    private EditText userEmail;
    private EditText userContact;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private ProgressDialog dialog;
    private TextView login;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference account = database.getReference().child("User");
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        registerUser();
    }

    private void registerUser() {
        userName = findViewById(R.id.user_register_name);
        userEmail = findViewById(R.id.user_register_email);
        userContact = findViewById(R.id.user_register_pNumber);
        password = findViewById(R.id.user_register_password);
        confirmPassword = findViewById(R.id.user_register_conformPassword);
        register = findViewById(R.id.user_register_button);
        login = findViewById(R.id.user_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String phone = userContact.getText().toString().trim();
                final String password1 = password.getText().toString().trim();
                final String password2 = confirmPassword.getText().toString().trim();

                //validation
                if(TextUtils.isEmpty(name)){
                    userName.setError("Enter Name");
                }
                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    userContact.setError("Phone Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    password.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    confirmPassword.setError("Password is Required");
                    return;
                }
                if (!TextUtils.equals(password1, password2)) {
                    confirmPassword.setError("Passwords Not Match");
                    return;
                }

                dialog.setMessage("Sending Data...");
                dialog.show();

                auth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                            userId = auth.getCurrentUser().getUid();
                            User user = new User(name,email,phone,password1);
                            account.child(userId).setValue(user);
                        }else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
        });
    }
}