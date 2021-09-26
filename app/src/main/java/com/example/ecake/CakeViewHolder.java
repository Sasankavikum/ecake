package com.example.ecake;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cake.R;

public class CakeViewHolder extends RecyclerView.ViewHolder {

    public ImageView cakeImage;
    public TextView cakeName;
    public TextView cakePrice;
    public TextView cakeQuantity;
    public TextView cakeDescription;
    public CardView itemCard;

    public CakeViewHolder(@NonNull View itemView) {
        super(itemView);
        cakeImage = itemView.findViewById(R.id.display_cake_image);
        cakeName = itemView.findViewById(R.id.display_cake_name);
        cakeQuantity = itemView.findViewById(R.id.display_cake_quantity);
        cakePrice = itemView.findViewById(R.id.display_cake_price);
        itemCard = itemView.findViewById(R.id.item_card);
        cakeDescription = itemView.findViewById(R.id.display_Description);

    }
}
