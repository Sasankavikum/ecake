package com.example.ecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;


import java.util.HashMap;
import java.util.UUID;

public class activity_feedback<FirebaseFirestore> extends AppCompatActivity {

    //Define variables
    private EditText name,message;
    public Button submitBtn, showBtn;
    private FirebaseFirestore db;
    private String ufId,ufname,umessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.feeName);
        message = findViewById(R.id.feeMessage);
        submitBtn = findViewById(R.id.feeInBtn);
        showBtn = findViewById(R.id.feeShowBtn);



        //db = FirebaseFirestore.getInstance();
        //collecting feedAdapter class data to update
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            //adapter class assigned variables
            submitBtn.setText("Update");
            ufId = bundle.getString("ufId");
            ufname = bundle.getString("ufname");
            umessage = bundle.getString("umessage");
            //get data for refill
            name.setText(ufname);
            message.setText(umessage);

        }else{
            submitBtn.setText("Save");
        }
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_feedback.this, activity_show_feedback.class));
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Collect all the input text fields
                String fname = name.getText().toString();
                String fmessage = message.getText().toString();
                //Usere want to save data show "Save" and wants to update bdata show "update" button
                Bundle bundle1=getIntent().getExtras();
                if(bundle1!=null){
                    String fid=  ufId;
                    updateToFireStore(fid,fname,fmessage);
                }else{
                    //insert method9
                    String fid = UUID.randomUUID().toString();
                    saveToFirestore(fid,fname,fmessage);
                }


            }
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_feedback.this, MainActivity.class));
            }
        });

    }
    private void updateToFireStore(String fid,String fname,String fmessage){
        db.collection("Feedbacks").document(fid).update("fname",fname,"fmessage",fmessage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_feedback.this, "Feedback Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity_feedback.this, activity_show_feedback.class));
                        }else{
                            Toast.makeText(activity_feedback.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(activity_feedback.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFirestore(String fid,String fname,String fmessage){

        if(!fname.isEmpty() && !fmessage.isEmpty()){
            HashMap<String,Object> map = new HashMap<>();
            map.put("fid",fid);
            map.put("fname",fname);
            map.put("fmessage",fmessage);
            //Database instance
            db.collection("Feedbacks").document(fid).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(activity_feedback.this,"Message Saved",Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(activity_feedback.this, activity_show_feedback.class));
                           }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity_feedback.this,"Save unsuccssfull",Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(this,"Please fill those all fields",Toast.LENGTH_SHORT).show();
        }
    }

}