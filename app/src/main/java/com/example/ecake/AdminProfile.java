package com.example.cake;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminProfile extends Fragment {

    private TextView adminName;
    private TextView adminEmail;
    private TextView adminPhoneNumber;
    private TextView adminShopName;
    private RecyclerView adminItemsRecyclerView;
    private UpdateCake updateCake;
    private Button logout;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference adminReference = database.getReference().child("Admin");
    private DatabaseReference itemReference = database.getReference().child("All_Cakes");
    private DatabaseReference adminDetailsReference;

    private FirebaseRecyclerOptions<Cake> options;
    private FirebaseRecyclerAdapter<Cake, AdminProfileViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        adminName = view.findViewById(R.id.admin_profile_name);
        adminEmail = view.findViewById(R.id.admin_profile_email);
        adminPhoneNumber = view.findViewById(R.id.admin_profile_phone_number);
        adminShopName = view.findViewById(R.id.admin_profile_shop_name);
        adminItemsRecyclerView = view.findViewById(R.id.admin_profile_items_recycler_view);
        logout = view.findViewById(R.id.admin_logout);
        auth = FirebaseAuth.getInstance();
        updateCake = new UpdateCake();

        adminItemsRecyclerView.setHasFixedSize(true);
        adminItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adminDetailsReference = adminReference.child(auth.getCurrentUser().getUid());
        adminDetailsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adminName.setText(dataSnapshot.child("name").getValue().toString());
                adminEmail.setText(dataSnapshot.child("email").getValue().toString());
                adminPhoneNumber.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                adminShopName.setText(dataSnapshot.child("shopName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        options = new FirebaseRecyclerOptions.Builder<Cake>().setQuery(itemReference, Cake.class).build();
        adapter = new FirebaseRecyclerAdapter<Cake, AdminProfileViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminProfileViewHolder holder, int position, @NonNull final Cake model) {
                holder.itemName.setText(model.getName());
                holder.itemPrice.setText(model.getCakePrice());
                holder.itemQuantity.setText(model.getCakeQuantity());
                Picasso.get().load(model.getImgUri()).into(holder.itemImage);

                holder.itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("itemName", model.getName());
                        bundle.putString("itemPrice", model.getCakePrice());
                        bundle.putString("itemDescription", model.getCakeDescription());
                        bundle.putString("itemQuantity", model.getCakeQuantity());
                        bundle.putString("itemId", model.getCakeId());
                        bundle.putString("itemImage", model.getImgUri());
                        updateCake.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, updateCake);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }

            @NonNull
            @Override
            public AdminProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_cake_item, parent, false);
                return new AdminProfileViewHolder(v);
            }
        };

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(getContext(), "You are now logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        adapter.startListening();
        adminItemsRecyclerView.setAdapter(adapter);

        return view;
    }
}