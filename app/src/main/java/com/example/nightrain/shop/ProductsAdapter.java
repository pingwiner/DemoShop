package com.example.nightrain.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nightrain.shop.model.ListItem;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    List<ListItem> mData;
    long selectedItem = 0;

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ProductViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ListItem item = mData.get(position);
        holder.setId(item.getId());
        holder.setTitle(item.getName());
        holder.setPrice(item.getPrice());
        holder.setQty(item.getQty());
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void setData(List<ListItem> listItems) {
        mData = listItems;
    }

    public void itemChanged(ListItem changedItem) {
        for (int i = 0; i < mData.size(); i++) {
            ListItem item = mData.get(i);
            if (item.getId() == changedItem.getId()) {
                item.setName(changedItem.getName());
                item.setPrice(changedItem.getPrice());
                item.setQty(changedItem.getQty());
                notifyItemChanged(i);
                return;
            }
        }
        mData.add(changedItem);
        notifyItemInserted(mData.size() - 1);
    }

    public void itemDeleted(long itemId) {
        int position = -1;
        for (int i = 0; i < mData.size(); i++) {
            ListItem item = mData.get(i);
            if (item.getId() == itemId) {
                position = i;
                break;
            }
        }
        if (position == -1) return;
        mData.remove(position);
        notifyItemRemoved(position);
    }
}
