package com.assignment.heroapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.assignment.heroapp.R;
import com.assignment.heroapp.adapter.CategoriesAdapter;
import com.assignment.heroapp.adapter.RankingAdapter;
import com.assignment.heroapp.database.HeroDb;
import com.assignment.heroapp.interfaces.HeroApi;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Category;
import com.assignment.heroapp.models.MainObject;
import com.assignment.heroapp.models.Product;
import com.assignment.heroapp.models.Ranking;
import com.assignment.heroapp.utils.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<MainObject>, MyItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView rvCategories;

    private RankingAdapter mAdapter;
    private CategoriesAdapter categoriesAdapter;


    ProgressDialog progressDoalog;

    private List<Ranking> rankingList;
    private List<Category> categoryList;

    private HeroDb heroDb;

    List<Category> listItem;
    List<Ranking> rankingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(getResources().getString(R.string.title_categories));

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        rvCategories = (RecyclerView) findViewById(R.id.rvCategories);

        heroDb = new HeroDb(MainActivity.this);


        if (isNetworkConnected()) {

            webCall();

        }else {

            Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

            // rankings
            mAdapter = new RankingAdapter(MainActivity.this,heroDb.getAllRanking(),this);
            rankingList = heroDb.getAllRanking();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //categories
            categoriesAdapter = new CategoriesAdapter(MainActivity.this,heroDb.getAllCategories(),this);
            categoryList = heroDb.getAllCategories();
            RecyclerView.LayoutManager categoriesLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
            rvCategories.setLayoutManager(categoriesLayoutManager);
            rvCategories.setItemAnimator(new DefaultItemAnimator());
            rvCategories.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();

        }
    }

    void webCall(){

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Please Wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HeroApi.base_url)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroApi heroApi = retrofit.create(HeroApi.class);

        Call<MainObject> call = heroApi.getFeed();
        call.enqueue(this);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onResponse(Call<MainObject> call, Response<MainObject> response) {

        if (response.isSuccessful()) {

            listItem = response.body().getCategories();
            rankingItem = response.body().getRankings();

            if (listItem.size()!= 0){

                Long retval = heroDb.insertCategories(listItem);
                Long retval2 = heroDb.insertRankings(rankingItem);

                Long retval3 = heroDb.insertProduct(listItem);
                Long retval4 = heroDb.insertVariants(listItem);
                Long retval5 = heroDb.insertRankingProducts(rankingItem);



            }else {
                Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
            }

            if (progressDoalog.isShowing()){
                progressDoalog.dismiss();
            }


            mAdapter = new RankingAdapter(MainActivity.this,heroDb.getAllRanking(),this);
            rankingList = heroDb.getAllRanking();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //categories
            categoriesAdapter = new CategoriesAdapter(MainActivity.this,heroDb.getAllCategories(),this);
            categoryList = heroDb.getAllCategories();
            RecyclerView.LayoutManager categoriesLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
            rvCategories.setLayoutManager(categoriesLayoutManager);
            rvCategories.setItemAnimator(new DefaultItemAnimator());
            rvCategories.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();


        } else {
            if (progressDoalog.isShowing()){
                progressDoalog.dismiss();
            }

        }

    }

    @Override
    public void onFailure(Call<MainObject> call, Throwable t) {

        if (progressDoalog.isShowing()){
            progressDoalog.dismiss();
        }


    }

    @Override
    public void onItemClick(int position, View v, boolean isCategories) {



        Intent intent = new Intent(this,ProductListActivity.class);

        if (isCategories){


            intent.putExtra(Constants.ID,categoryList.get(position).getId());
            intent.putExtra(Constants.IS_CATEGORY,isCategories);

            startActivity(intent);

        }else {

            intent.putExtra(Constants.ID,rankingList.get(position).getRanking());
            intent.putExtra(Constants.IS_CATEGORY,isCategories);

            startActivity(intent);



        }
    }



}
