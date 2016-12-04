package com.example.nightrain.shop;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.nightrain.shop.event.ItemDeletedEvent;
import com.example.nightrain.shop.model.ListItemDao;

import org.greenrobot.eventbus.EventBus;

public class DeleteDialog extends DialogFragment {
    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final long itemId = getArguments().getLong(ARG_ITEM_ID);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_product)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListItemDao dao = ((DaoInterface)getActivity()).getListItemDao();
                        dao.deleteByKey(itemId);
                        EventBus.getDefault().post(new ItemDeletedEvent(itemId));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}