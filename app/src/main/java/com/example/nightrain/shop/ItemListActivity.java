package com.example.nightrain.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.View;
import com.example.nightrain.shop.event.ItemChangedEvent;
import com.example.nightrain.shop.event.ItemDeleteRequestEvent;
import com.example.nightrain.shop.event.ItemDeletedEvent;
import com.example.nightrain.shop.event.ItemSelectedEvent;
import com.example.nightrain.shop.model.DaoSession;
import com.example.nightrain.shop.model.ListItem;
import com.example.nightrain.shop.model.ListItemDao;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ItemListActivity extends AppCompatActivity implements DaoInterface {
    private boolean mTwoPane;
    private DaoSession daoSession;
    private ListItemDao listItemDao;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        daoSession = ((App) getApplication()).getDaoSession();
        listItemDao = daoSession.getListItemDao();

        if (Utils.isFirstRun(this)) {
            Utils.generateTestContent(listItemDao);
        }

        adapter = new ProductsAdapter();
        adapter.setData(listItemDao.loadAll());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItem(0);
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void showItem(long itemId) {
        if (mTwoPane) {
            ItemDetailFragment fragment = new ItemDetailFragment();
            Bundle args = new Bundle();
            args.putLong(ItemDetailFragment.ARG_ITEM_ID, itemId);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, itemId);
            startActivity(intent);
        }
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemChangedEvent(ItemChangedEvent event) {
        ListItem item = listItemDao.load(event.itemId);
        adapter.itemChanged(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemSelectedEvent(ItemSelectedEvent event) {
        showItem(event.itemId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemDeletedEvent(ItemDeletedEvent event) {
        adapter.itemDeleted(event.itemId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemDeleteRequestEvent(ItemDeleteRequestEvent event) {
        DialogFragment dialog = new DeleteDialog();
        Bundle args = new Bundle();
        args.putLong(DeleteDialog.ARG_ITEM_ID, event.itemId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }

    @Override
    public ListItemDao getListItemDao() {
        return listItemDao;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
