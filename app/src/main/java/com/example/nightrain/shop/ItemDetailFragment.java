package com.example.nightrain.shop;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.nightrain.shop.event.ItemChangedEvent;
import com.example.nightrain.shop.event.ItemDeletedEvent;
import com.example.nightrain.shop.model.ListItem;
import com.example.nightrain.shop.model.ListItemDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private ListItem mItem;
    private EditText titleEdit;
    private EditText priceEdit;
    private EditText qtyEdit;
    private ListItemDao dao;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void restoreState(Bundle savedInstanceState) {
        long productId = 0;

        if (savedInstanceState != null) {
            productId = savedInstanceState.getLong(ARG_ITEM_ID, 0);
        } else if (getArguments().containsKey(ARG_ITEM_ID)) {
            productId = getArguments().getLong(ARG_ITEM_ID);
        }
        if (productId > 0) {
            mItem = dao.load(productId);
        }
        if (mItem == null) {
            mItem = new ListItem();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        dao = ((DaoInterface) getActivity()).getListItemDao();
        restoreState(savedInstanceState);

        titleEdit = (EditText) rootView.findViewById(R.id.title);
        priceEdit = (EditText) rootView.findViewById(R.id.price);
        qtyEdit = (EditText) rootView.findViewById(R.id.qty);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getName());
        }

        titleEdit.setText(mItem.getName());
        priceEdit.setText(Utils.priceToString(mItem.getPrice()));
        qtyEdit.setText(Integer.toString(mItem.getQty()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mItem.setName(titleEdit.getText().toString());
        mItem.setPrice(Utils.priceFromString(priceEdit.getText().toString()));
        String value = qtyEdit.getText().toString();
        try {
            mItem.setQty(Integer.parseInt(qtyEdit.getText().toString()));
        } catch(Exception e) {
            mItem.setQty(0);
        }
        if (!mItem.getName().trim().isEmpty()) {
            dao.save(mItem);
            EventBus.getDefault().post(new ItemChangedEvent(mItem.getId()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemDeletedEvent(ItemDeletedEvent event) {
        titleEdit.setText(null);
        priceEdit.setText(null);
        qtyEdit.setText(null);
        mItem = new ListItem();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_ITEM_ID, mItem.getId());
    }

}
