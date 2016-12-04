package com.example.nightrain.shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nightrain.shop.event.ItemDeleteRequestEvent;
import com.example.nightrain.shop.event.ItemSelectedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by nightrain on 04/12/2016.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private long itemId;
    private TextView titleView;
    private TextView priceView;
    private TextView qtyView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
        priceView = (TextView) itemView.findViewById(R.id.price);
        qtyView = (TextView) itemView.findViewById(R.id.qty);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ItemSelectedEvent(itemId));
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                EventBus.getDefault().post(new ItemDeleteRequestEvent(itemId));
                return true;
            }
        });
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setPrice(int price) {
        priceView.setText(Utils.priceToString(price) + " р.");
    }

    public void setQty(int qty) {
        qtyView.setText(qty + " шт.");
    }

    public void setId(Long id) {
        this.itemId = id;
    }
}
