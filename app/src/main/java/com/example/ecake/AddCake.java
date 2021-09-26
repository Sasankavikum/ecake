package com.example.ecake;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddCake extends Fragment {

    private EditText cakeName;
    private EditText description;
    private EditText quantity;
    private EditText price;
    private Button buttonAdd;
    private ImageView cakeImg;
    private String adminId;
    private static final int GALARY_INTENT_INTENT = 2;
    private Uri imageUri;
    private ProgressDialog mProgressDialog;

    private String adminName;
    private String adminEmail;
    private String adminContactNo;

    private FirebaseAuth spAuth;
    private FirebaseDatabase spFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference spReference = spFirebaseDatabase.getReference().child("All_Cakes");
    private DatabaseReference getDetails;
    private StorageReference allItemReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cake,container,false);
        cakeName = view.findViewById(R.id.add_cake_name);
        description = view.findViewById(R.id.add_description);
        quantity = view.findViewById(R.id.add_quantity);
        price = view.findViewById(R.id.add_price);
        buttonAdd = view.findViewById(R.id.add_button);
        cakeImg = view.findViewById(R.id.add_product_image);
        spAuth = FirebaseAuth.getInstance();

        allItemReference = FirebaseStorage.getInstance().getReference("Cake_Images");

        mProgressDialog = new ProgressDialog(getActivity());

        cakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"CakeImage"), GALARY_INTENT_INTENT);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println("This is in buttonAdd on click");
                sendCake();
            }

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALARY_INTENT_INTENT && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                cakeImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void sendCake() {
        if(imageUri != null){
            mProgressDialog.setMessage("Adding....");
            mProgressDialog.show();

            final StorageReference imgRef = allItemReference.child(System.currentTimeMillis() +"."+ GetFileExtension(imageUri));
            imgRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String name = cakeName.getText().toString().trim();
                    final String cakeDescription = description.getText().toString().trim();
                    final String cakeQuantity = quantity.getText().toString().trim();
                    final String cakePrice = price.getText().toString().trim();
                    final String cakeId = spReference.push().getKey();

                    if (TextUtils.isEmpty(name)) {
                        cakeName.setError("Cake name is required");
                        return;
                    }

                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(),"Added", Toast.LENGTH_LONG).show();

                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Cake newCake = new Cake(cakeId,name,cakeDescription,cakeQuantity,cakePrice, url);
                            spReference.child(cakeId).setValue(newCake);

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getContext(),"Please select an image", Toast.LENGTH_LONG).show();
        }
    }


}