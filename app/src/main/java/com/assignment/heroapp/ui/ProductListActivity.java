package com.assignment.heroapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.assignment.heroapp.R;
import com.assignment.heroapp.adapter.CategoriesAdapter;
import com.assignment.heroapp.adapter.ProductsAdapter;
import com.assignment.heroapp.database.HeroDb;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView rvProducts;

    private ProductsAdapter mProductssAdapter;

    private MyItemClickListener myItemClickListener;
    ProgressDialog progressDoalog;
    private HeroDb heroDb;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        rvProducts = (RecyclerView)findViewById(R.id.rvProducts);
        heroDb = new HeroDb(ProductListActivity.this);

        setData();
    }


    public void setData(){
        mProductssAdapter = new ProductsAdapter(ProductListActivity.this,heroDb.getAllProducts());
        productList = heroDb.getAllProducts();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.setAdapter(mProductssAdapter);
        mProductssAdapter.notifyDataSetChanged();
    }
}
