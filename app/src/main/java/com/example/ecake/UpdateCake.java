package com.example.ecake;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCake extends Fragment {

    private EditText updateItemName;
    private EditText updateItemDescription;
    private EditText updateItemPrice;
    private EditText updateItemQuantity;
    private Button updateItemButton;
    private Button deleteItemButton;
    private Bundle bundle;
    private ProgressDialog dialog;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("All_Cakes");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_cake, container, false);

        updateItemName = view.findViewById(R.id.update_cake_name);
        updateItemDescription = view.findViewById(R.id.update_cake_description);
        updateItemPrice = view.findViewById(R.id.update_cake_price);
        updateItemQuantity = view.findViewById(R.id.update_cake_quantity);
        updateItemButton = view.findViewById(R.id.update_cake_button);
        deleteItemButton = view.findViewById(R.id.delete_cake_button);
        bundle = this.getArguments();
        dialog = new ProgressDialog(getContext());

        displayUpdateItemDetails();

        updateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

        return view;
    }

    private void displayUpdateItemDetails() {
        updateItemName.setText(bundle.getString("itemName"));
        updateItemDescription.setText(bundle.getString("itemDescription"));
        updateItemPrice.setText(bundle.getString("itemPrice"));
        updateItemQuantity.setText(bundle.getString("itemQuantity"));
    }

    private void updateProduct() {
        String cakeId = bundle.getString("itemId");
        String imageUrl = bundle.getString("itemImage");
        String name = updateItemName.getText().toString().trim();
        String description = updateItemDescription.getText().toString().trim();
        String price = updateItemPrice.getText().toString().trim();
        String quantity = updateItemQuantity.getText().toString().trim();

        System.out.println("image Url : " + imageUrl);

        if (TextUtils.isEmpty(name)) {
            updateItemName.setError("Name is required");
        }

        dialog.setMessage("Update Product...");
        dialog.show();

        Cake updateCake = new Cake(cakeId, name, description, quantity, price, imageUrl);

        reference.child(bundle.getString("itemId")).setValue(updateCake).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Product Updated", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new AdminProfile());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Product Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteProduct() {
        dialog.setMessage("Deleting Product...");
        dialog.show();

        reference.child(bundle.getString("itemId")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Product Deleted", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new AdminProfile());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Product Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}