package com.assignment.heroapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HeroDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HERO.db";
    public static final String ITEM_TABLE_NAME = "TABLE_ITEMS";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";


    // Table Names
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_VARIANTS = "variants";
    private static final String RANKINGS = "rankings";


    // RANKINGS Tables
    private static final String TABLE_RANKING_MOST_VIEWED = "ranking_most_viewed";
    private static final String TABLE_RANKING_MOST_ORDERED = "ranking_most_ordered";
    private static final String TABLE_RANKING_MOST_SHARED = "ranking_most_shared";

    // CATEGORIES Table - column nmaes
    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "category_name";


    // PRODUCTS Table - column names
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_DATE = "product_date";
    private static final String PRODUCT_TAX_NAME = "product_tax_name";
    private static final String PRODUCT_TAX_VALUE = "product_tax_value";

    // VARIANTS Table - column names
    private static final String VARIANT_ID = "variant_id";
    private static final String VARIANT_COLOR = "variant_color";
    private static final String VARIANT_SIZE= "variant_color";
    private static final String VARIANT_PRICE = "variant_price";

    // RANKING_MOST_VIEWED Table - column names
    private static final String MV_PRODUCT_ID = "mv_product_id";
    private static final String MV_VIEW_COUNT = "mv_view_count";

    // RANKING_MOST_ORDERED Table - column names
    private static final String MO_PRODUCT_ID = "mo_product_id";
    private static final String MO_ORDER_COUNT = "mo_order_count";

    // RANKING_MOST_SHARED Table - column names
    private static final String MS_PRODUCT_ID = "ms_product_id";
    private static final String MS_SHARES = "ms_view_count";





    public HeroDb( Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_ITEMS");
        onCreate(db);
    }



   /* public Long insertItems(List<Item> list) {

    }

    public List<Item> getAllItems() {

    }*/
}
