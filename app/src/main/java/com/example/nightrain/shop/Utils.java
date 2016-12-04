package com.example.nightrain.shop;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nightrain.shop.model.ListItem;
import com.example.nightrain.shop.model.ListItemDao;

import java.util.Locale;

/**
 * Created by nightrain on 04/12/2016.
 */

public class Utils {
    private static final String KEY_FIRST_RUN = "FIRST_RUN";

    public static boolean isFirstRun(Activity context) {
        SharedPreferences pref = context.getPreferences(Context.MODE_PRIVATE);
        boolean result = pref.getBoolean(KEY_FIRST_RUN, true);
        pref.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        return result;
    }

    public static void generateTestContent(ListItemDao dao) {
        dao.insert(new ListItem(null, "30 ГБ SSD-накопитель Kingston SSDNow mS200", 1750, 2));
        dao.insert(new ListItem(null, "64 ГБ SSD-накопитель AData Premier Pro SP310", 2350 , 1));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель Smartbuy S9M", 2399, 5));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель SiliconPower Velox V55", 2450, 3));
        dao.insert(new ListItem(null, "32 ГБ SSD-накопитель Transcend 370S ", 2450, 4));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель Kingston SSDNow mS200 ", 2450, 2));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель Qumo Novation MM ", 2450, 6));
        dao.insert(new ListItem(null, "64 ГБ SSD-накопитель Transcend 340K ", 2499, 4));
        dao.insert(new ListItem(null, "32 ГБ SSD-накопитель AData SP600 ", 2599, 8));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель Smartbuy Splash", 2699, 2));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель Patriot Blaze", 2699, 1));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель Corsair Force LS", 2799, 1));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель SiliconPower Slim S55", 2850, 2));
        dao.insert(new ListItem(null, "60 ГБ SSD-накопитель SiliconPower S60", 2899, 1));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель Sandisk SSD Plus", 3050, 2));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель Transcend 220", 3050, 1));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель AMD Radeon R3 Series", 3099, 4));
        dao.insert(new ListItem(null, "64 ГБ SSD-накопитель AData SP600", 3099, 1));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель Smartbuy Revival", 3099, 2));
        dao.insert(new ListItem(null, "120 ГБ SSD-накопитель Patriot Blast", 3150, 3));
        dao.insert(new ListItem(null, "128 ГБ SSD-накопитель Transcend 360S", 3199, 1));
    }

    public static String priceToString(int price) {
        return Integer.toString(price);
    }

    public static int priceFromString(String value) {
        try {
            return Integer.parseInt(value);
        } catch(Exception e) {
            return 0;
        }
    }

}

