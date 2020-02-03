package com.assignment.heroapp;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.assignment.heroapp.adapter.CategoriesAdapter;
import com.assignment.heroapp.adapter.RankingAdapter;
import com.assignment.heroapp.interfaces.HeroApi;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Category;
import com.assignment.heroapp.models.MainObject;
import com.assignment.heroapp.models.Ranking;

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

    private MyItemClickListener myItemClickListener;

    ProgressDialog progressDoalog;

    private List<Ranking> rankingList;
    private List<Category> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        rvCategories = (RecyclerView) findViewById(R.id.rvCategories);

        //click listeners

        if (isNetworkConnected()) {

            webCall();

        }else {

            Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

            //ranking
            //mAdapter = new RankingAdapter(MainActivity.this,docDb.getAllItems(),this);
            rankingList = null;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //categories
            //categoriesAdapter = new CategoriesAdapter(MainActivity.this,docDb.getAllItems(),this);
            categoryList = null;
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


//        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(SimpleXmlConverterFactory.create())
//                .build();

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
            if (progressDoalog.isShowing()){
                progressDoalog.dismiss();
            }

            Log.e("Response success", response.message());
            Log.e("Response success", response.body().toString());
            //Log.e("Response ", ""+response.body().toString());

            /*listItem = response.body().getItem();

            if (listItem.size()!= 0){
                Long retval = docDb.insertItems(listItem);
                Toast.makeText(MainActivity.this,retval+" rows inserted",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
            }

            mAdapter = new MyAdapter(MainActivity.this,docDb.getAllItems());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();*/

            //ranking
            mAdapter = new RankingAdapter(MainActivity.this,response.body().getRankings(),this);
            rankingList = response.body().getRankings();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //categories
            categoriesAdapter = new CategoriesAdapter(MainActivity.this,response.body().getCategories(),this);
            categoryList = response.body().getCategories();
            RecyclerView.LayoutManager categoriesLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
            rvCategories.setLayoutManager(categoriesLayoutManager);
            rvCategories.setItemAnimator(new DefaultItemAnimator());
            rvCategories.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();


        } else {
            if (progressDoalog.isShowing()){
                progressDoalog.dismiss();
            }
            Log.e("Response Failed", response.message());
        }

    }

    @Override
    public void onFailure(Call<MainObject> call, Throwable t) {

        if (progressDoalog.isShowing()){
            progressDoalog.dismiss();
        }
        Log.e("Response fail", t.getMessage());

    }

    @Override
    public void onItemClick(int position, View v, boolean isCategories) {

        Log.e("shahbaz","position : "+position);
        if (isCategories){
            Log.e("shahbaz","value : "+categoryList.get(position).getName());
        }else {
            Log.e("shahbaz","value : "+rankingList.get(position).getRanking());
        }
    }
}