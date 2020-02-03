package com.assignment.heroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.assignment.heroapp.adapter.CategoriesAdapter;
import com.assignment.heroapp.adapter.ProductsAdapter;
import com.assignment.heroapp.interfaces.MyItemClickListener;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProducts;

    private ProductsAdapter productssAdapter;

    private MyItemClickListener myItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
    }
}
