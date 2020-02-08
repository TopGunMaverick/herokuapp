package com.assignment.heroapp.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.heroapp.BR;
import com.assignment.heroapp.R;
import com.assignment.heroapp.databinding.RowRankingBinding;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Ranking;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {

    List<Ranking> list;
    Activity context;
    RowRankingBinding rowRankingBinding;
    LayoutInflater layoutInflater;

    private static MyItemClickListener myItemClickListener;

    public RankingAdapter(Activity context, List<Ranking> list, MyItemClickListener myItemClickListener) {

        this.context = context;
        this.list = list;
        this.myItemClickListener = myItemClickListener;
    }

    @NonNull
    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }


        rowRankingBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row_ranking,viewGroup,false);

        return new MyViewHolder(rowRankingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.MyViewHolder myViewHolder, final int i) {



        Ranking item = list.get(i);

        rowRankingBinding.tvTitle.setText(item.getRanking());

        rowRankingBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myItemClickListener.onItemClick(i,v,false);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RowRankingBinding rowRankingBinding;


        public MyViewHolder(@NonNull RowRankingBinding rowRankingBinding) {


            super(rowRankingBinding.getRoot());
            this.rowRankingBinding = rowRankingBinding;



        }



    }
}
