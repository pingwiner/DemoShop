package com.example.nightrain.shop;

import android.app.Application;

import com.example.nightrain.shop.model.DaoMaster;
import com.example.nightrain.shop.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by nightrain on 03/12/2016.
 */

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}



