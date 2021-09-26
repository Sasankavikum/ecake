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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserLogin extends AppCompatActivity {
    private TextView createNewUserAccount;
    private EditText userLoginEmail;
    private EditText userLoginPassword;
    private Button userLoginButton;
    private ProgressDialog dialog;
    private TextView adminlogin;
    private String EmailAuth;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), AdminHome.class));
//        }

        login();
    }


    private void login() {
        createNewUserAccount = findViewById(R.id.create_new_account);
        userLoginEmail = findViewById(R.id.user_login_email);
        userLoginPassword = findViewById(R.id.user_login_password);
        userLoginButton = findViewById(R.id.user_login_button);
        adminlogin = findViewById(R.id.login_as_admin);

         EmailAuth = userLoginEmail.getText().toString().trim();


        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userLoginEmail.getText().toString().trim();
                String password = userLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    userLoginEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userLoginPassword.setError("Password is required");
                    return;
                }

                dialog.setMessage("Login in...");
                dialog.show();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();

                            Auth_User Auth=new Auth_User(EmailAuth);

                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserHome.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        createNewUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegistration.class));
            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}