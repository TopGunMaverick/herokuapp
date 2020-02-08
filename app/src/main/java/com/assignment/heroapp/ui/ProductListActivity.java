package com.assignment.heroapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assignment.heroapp.R;
import com.assignment.heroapp.adapter.CategoriesAdapter;
import com.assignment.heroapp.adapter.ProductsAdapter;
import com.assignment.heroapp.database.HeroDb;
import com.assignment.heroapp.databinding.ActivityProductListBinding;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Product;
import com.assignment.heroapp.utils.Constants;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements  MyItemClickListener  {


    private ProductsAdapter mProductssAdapter;

    private HeroDb heroDb;
    private List<Product> productList;

    private boolean isCategories;
    private int categoryId;
    private String rankingType;
    ActivityProductListBinding productListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListBinding = DataBindingUtil.setContentView(this,R.layout.activity_product_list);

        this.setTitle(getResources().getString(R.string.title_products));

        heroDb = new HeroDb(ProductListActivity.this);

        isCategories = getIntent().getExtras().getBoolean(Constants.IS_CATEGORY);

        if(isCategories){

            categoryId = getIntent().getExtras().getInt(Constants.ID);

        }else {

            rankingType = getIntent().getExtras().getString(Constants.ID);
        }

        setData();
    }


    public void setData(){

        if (isCategories){

            productList = heroDb.getProducts(isCategories,String.valueOf(categoryId));
        }else {
            productList = heroDb.getProducts(isCategories,rankingType);
        }

        if( productList.size()>0){
            productListBinding.tvNoData.setVisibility(View.GONE);
        }

        mProductssAdapter = new ProductsAdapter(ProductListActivity.this,productList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        productListBinding.rvProducts.setLayoutManager(mLayoutManager);
        productListBinding.rvProducts.setItemAnimator(new DefaultItemAnimator());
        productListBinding.rvProducts.setAdapter(mProductssAdapter);
        mProductssAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, View v, boolean isCategories) {

        Intent intent = new Intent(this,VariantsListActivity.class);


        String productName = productList.get(position).getName();
        String taxName = productList.get(position).getTax().getName();
        String taxValue = String.valueOf(productList.get(position).getTax().getValue());


            intent.putExtra(Constants.ID,productList.get(position).getId());
            intent.putExtra(Constants.PRODUCT_NAME,productName);
            intent.putExtra(Constants.TAX_NAME,taxName);
            intent.putExtra(Constants.TAX_VALUE,taxValue);

            startActivity(intent);


    }
}
