package com.example.ecake;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myproducts extends RecyclerView.ViewHolder {

    ImageView imageview;
    TextView name;
    TextView price;
    TextView qty;

    public myproducts(@NonNull View itemView) {
        super(itemView);

        imageview=imageview.findViewById(R.id.display_cart_cake_image);
        name=itemView.findViewById(R.id.display_cart_item_name);
        qty=itemView.findViewById(R.id.cart_item_quantity);
        price=itemView.findViewById(R.id.seller_price);

    }
}
