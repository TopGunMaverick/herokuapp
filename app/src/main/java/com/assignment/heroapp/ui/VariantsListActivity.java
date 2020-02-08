package com.assignment.heroapp.ui;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assignment.heroapp.R;
import com.assignment.heroapp.adapter.ProductsAdapter;
import com.assignment.heroapp.adapter.VariantsAdapter;
import com.assignment.heroapp.database.HeroDb;
import com.assignment.heroapp.databinding.ActivityVariantsListBinding;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Product;
import com.assignment.heroapp.models.Variant;
import com.assignment.heroapp.utils.Constants;

import java.util.List;

public class VariantsListActivity extends AppCompatActivity {

    private VariantsAdapter mVariantsAdapter;

    private HeroDb heroDb;
    private int productId;
    private String productName, taxName,taxValue;
    private ActivityVariantsListBinding activityVariantsListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityVariantsListBinding = DataBindingUtil.setContentView(this,R.layout.activity_variants_list);

        this.setTitle(getResources().getString(R.string.title_variants));

        heroDb = new HeroDb(VariantsListActivity.this);

        productId = getIntent().getExtras().getInt(Constants.ID);
        productName = getIntent().getExtras().getString(Constants.PRODUCT_NAME);
        taxName = getIntent().getExtras().getString(Constants.TAX_NAME);
        taxValue = getIntent().getExtras().getString(Constants.TAX_VALUE);

        setVariants();
    }

    public void setVariants(){

        activityVariantsListBinding.tvTitle.setText(productName);
        activityVariantsListBinding.tvTax.setText(taxName+" "+taxValue+" %");



        mVariantsAdapter = new VariantsAdapter(VariantsListActivity.this,heroDb.getVariants(productId));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        activityVariantsListBinding.rv.setLayoutManager(mLayoutManager);
        activityVariantsListBinding.rv.setItemAnimator(new DefaultItemAnimator());
        activityVariantsListBinding.rv.setAdapter(mVariantsAdapter);
        mVariantsAdapter.notifyDataSetChanged();

    }
}
