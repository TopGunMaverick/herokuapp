package com.assignment.heroapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.assignment.heroapp.models.Category;
import com.assignment.heroapp.models.Product;
import com.assignment.heroapp.models.Ranking;
import com.assignment.heroapp.models.RankingProduct;
import com.assignment.heroapp.models.Tax;
import com.assignment.heroapp.models.Variant;

import java.util.ArrayList;
import java.util.List;

public class HeroDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HERO.db";


    // Table Names
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_VARIANTS = "variants";
    private static final String TABLE_RANKINGS = "rankings";


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
    private static final String VARIANT_SIZE= "variant_size";
    private static final String VARIANT_PRICE = "variant_price";

    // RANKING column names
    private static final String RANKING_ID= "ranking_id";
    private static final String RANKING_TYPE= "ranking_type";


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
        // Categories Table
        db.execSQL(
                "create table TABLE_CATEGORIES " +
                        "(category_id integer primary key, category_name text)"
        );

        // Rankings Table
        db.execSQL(
                "create table TABLE_RANKINGS " +
                        "(ranking_id integer primary key autoincrement , ranking_type text)"
        );

        // Products Table
        db.execSQL(
                "create table TABLE_PRODUCTS " +
                        "(product_id integer primary key, product_name text, product_date text, category_id  integer,product_tax_name text,product_tax_value text , foreign key (category_id) references categories (category_id))"
        );

        // Variants Table
        db.execSQL(
                "create table TABLE_VARIANTS " +
                        "(variant_id integer primary key, variant_color text, variant_size integer, variant_price  integer ,product_id integer, foreign key (product_id) references products (product_id))"
        );



        // TABLE_RANKING_MOST_VIEWED Table
        db.execSQL(
                "create table TABLE_RANKING_MOST_VIEWED " +
                        "(mv_product_id integer primary key, mv_view_count integer)"
        );



        // TABLE_RANKING_MOST_ORDERED Table
        db.execSQL(
                "create table TABLE_RANKING_MOST_ORDERED " +
                        "(mo_product_id integer primary key, mo_order_count integer)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_CATEGORIES");
        db.execSQL("DROP TABLE IF EXISTS TABLE_RANKINGS");
        db.execSQL("DROP TABLE IF EXISTS TABLE_PRODUCTS ");
        db.execSQL("DROP TABLE IF EXISTS TABLE_VARIANTS");
        db.execSQL("DROP TABLE IF EXISTS TABLE_RANKING_MOST_VIEWED");
        db.execSQL("DROP TABLE IF EXISTS TABLE_RANKING_MOST_ORDERED");
        onCreate(db);
    }


    // Categories Table Operations

    public Long insertCategories(List<Category> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        //clearing db
        db.execSQL("delete from TABLE_CATEGORIES");

        ContentValues contentValues = new ContentValues();

        Long retval = 0L;

        for (int i = 0; i < list.size(); i++) {

            contentValues.put("category_id", list.get(i).getId());
            contentValues.put("category_name", list.get(i).getName());

            retval = db.insert("TABLE_CATEGORIES", null, contentValues);
        }
        Log.e("shahbaz","inserted" +retval);
        return retval;

    }

    public List<Category> getAllCategories() {
        List<Category> list;

        list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from TABLE_CATEGORIES", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Category category = new Category();
            category.setId(res.getInt(res.getColumnIndex(CATEGORY_ID)));
            category.setName(res.getString(res.getColumnIndex(CATEGORY_NAME)));

            list.add(category);
            res.moveToNext();
        }
        return list;
    }


    // Ranking Table Operations

    public Long insertRankings(List<Ranking> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        //clearing db
        db.execSQL("delete from TABLE_RANKINGS");

        ContentValues contentValues = new ContentValues();

        Long retval = 0L;

        for (int i = 0; i < list.size(); i++) {

            contentValues.put("ranking_type", list.get(i).getRanking());

            retval = db.insert("TABLE_RANKINGS", null, contentValues);
        }
        Log.e("shahbaz","ranking inserted" +retval);
        return retval;

    }

    public List<Ranking> getAllRanking() {
        List<Ranking> list;

        list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from TABLE_RANKINGS", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Ranking ranking = new Ranking();
            ranking.setRanking(res.getString(res.getColumnIndex(RANKING_TYPE)));
            list.add(ranking);
            res.moveToNext();
        }
        return list;
    }

    // Product Table Operations

    public Long insertProduct(List<Category> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        //clearing db
        db.execSQL("delete from TABLE_PRODUCTS");

        ContentValues contentValues = new ContentValues();

        Long retval = 0L;

        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.get(i).getProducts().size() ; j++) {
                contentValues.put("product_id", list.get(i).getProducts().get(j).getId());
                contentValues.put("product_name", list.get(i).getProducts().get(j).getName());
                contentValues.put("product_date", list.get(i).getProducts().get(j).getDateAdded());
                contentValues.put("category_id", list.get(i).getId());
                contentValues.put("product_tax_name", list.get(i).getProducts().get(j).getTax().getName());
                contentValues.put("product_tax_value", list.get(i).getProducts().get(j).getTax().getValue());


                retval = db.insert("TABLE_PRODUCTS", null, contentValues);
            }

        }
        Log.e("shahbaz","products inserted " +retval);
        return retval;

    }

    public List<Product> getAllProducts() {
        List<Product> list;

        list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from TABLE_PRODUCTS", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Product product = new Product();
            product.setId(res.getInt(res.getColumnIndex(PRODUCT_ID)));
            product.setName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
            product.setDateAdded(res.getString(res.getColumnIndex(PRODUCT_DATE)));
            product.setTax(new Tax(res.getString(res.getColumnIndex(PRODUCT_TAX_NAME)),res.getFloat(res.getColumnIndex(PRODUCT_TAX_VALUE))));
            list.add(product);
            res.moveToNext();
        }
        return list;
    }



    // Varinats Table Operations


    public Long insertVariants(List<Category> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        //clearing db
        db.execSQL("delete from TABLE_VARIANTS");

        ContentValues contentValues = new ContentValues();

        Long retval = 0L;

        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.get(i).getProducts().size() ; j++) {

                for (int k = 0; k <  list.get(i).getProducts().get(j).getVariants().size(); k++) {
                    contentValues.put("variant_id", list.get(i).getProducts().get(j).getVariants().get(k).getId());
                    contentValues.put("variant_color", list.get(i).getProducts().get(j).getVariants().get(k).getColor());
                    contentValues.put("variant_size", list.get(i).getProducts().get(j).getVariants().get(k).getSize());
                    contentValues.put("variant_price", list.get(i).getProducts().get(j).getVariants().get(k).getPrice());

                    retval = db.insert("TABLE_VARIANTS", null, contentValues);
                }

            }

        }
        Log.e("shahbaz","variants inserted " +retval);
        return retval;

    }

    public List<Variant> getAllVariants() {
        List<Variant> list;

        list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from TABLE_VARIANTS", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Variant variant = new Variant();
            variant.setId(res.getInt(res.getColumnIndex(VARIANT_ID)));
            variant.setColor(res.getString(res.getColumnIndex(VARIANT_COLOR)));
            variant.setSize(res.getInt(res.getColumnIndex(VARIANT_SIZE)));
            variant.setPrice(res.getInt(res.getColumnIndex(VARIANT_PRICE)));

            list.add(variant);
            res.moveToNext();
        }
        return list;
    }



    // TABLE_RANKING_MOST_VIEWED Table Operations

    public Long insertRankingProducts(List<Ranking> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        //clearing db
        db.execSQL("delete from TABLE_RANKING_MOST_VIEWED");

        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();

        Long retval = 0L;
        Long retval2 = 0L;
        Long retval3 = 0L;

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getRanking().equals("Most Viewed Products")){
                for (int j = 0; j <list.get(i).getRankingProducts().size() ; j++) {
                    contentValues.put("mv_product_id", list.get(i).getRankingProducts().get(j).getId());
                    contentValues.put("mv_view_count", list.get(i).getRankingProducts().get(j).getViewCount());

                    retval = db.insert("TABLE_RANKING_MOST_VIEWED", null, contentValues);
                }
            }


            if(list.get(i).getRanking().equals("Most OrdeRed Products")){
                for (int j = 0; j <list.get(i).getRankingProducts().size() ; j++) {
                    contentValues2.put("mo_product_id", list.get(i).getRankingProducts().get(j).getId());
                    contentValues2.put("mo_order_count", list.get(i).getRankingProducts().get(j).getOrderCount());

                    retval2 = db.insert("TABLE_RANKING_MOST_ORDERED", null, contentValues2);
                }
            }

        }
        Log.e("shahbaz","inserted TABLE_RANKING_MOST_VIEWED " +retval);
        Log.e("shahbaz","inserted TABLE_RANKING_MOST_ORDERED " +retval2);
        return retval;

    }

    public List<Product> getMostViewedProducts() {
        List<Product> list=null;


        return list;
    }


}
