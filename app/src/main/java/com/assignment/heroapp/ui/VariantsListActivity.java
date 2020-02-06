package com.assignment.heroapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.assignment.heroapp.R;
import com.assignment.heroapp.adapter.VariantsAdapter;
import com.assignment.heroapp.database.HeroDb;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Variant;

import java.util.List;

public class VariantsListActivity extends AppCompatActivity {
    private RecyclerView rvVariants;

    private VariantsAdapter mVariantsAdapter;

    private MyItemClickListener myItemClickListener;
    ProgressDialog progressDoalog;
    private HeroDb heroDb;
    private List<Variant> variantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variants_list);
        rvVariants = (RecyclerView)findViewById(R.id.rvVariants);
        heroDb = new HeroDb(VariantsListActivity.this);
        setVariants();
    }

    public void setVariants(){

    }
}
