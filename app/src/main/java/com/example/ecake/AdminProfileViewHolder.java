package com.example.cake;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminProfileViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemImage;
    public TextView itemName;
    public TextView itemPrice;
    public TextView itemQuantity;
    public CardView itemCard;

    public AdminProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.admin_profile_item_image);
        itemName = itemView.findViewById(R.id.admin_profile_item_name);
        itemPrice = itemView.findViewById(R.id.admin_profile_item_price);
        itemQuantity = itemView.findViewById(R.id.admin_profile_item_quantity);
        itemCard = itemView.findViewById(R.id.admin_card_item);
    }
}
