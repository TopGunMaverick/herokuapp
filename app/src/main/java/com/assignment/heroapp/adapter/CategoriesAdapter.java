package com.assignment.heroapp.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.heroapp.R;
import com.assignment.heroapp.databinding.RowCategoryBinding;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    List<Category> list;
    Activity context;
    RowCategoryBinding rowCategoryBinding;
    LayoutInflater layoutInflater;

    private static MyItemClickListener myItemClickListener;

    public CategoriesAdapter(Activity context, List<Category> list, MyItemClickListener myItemClickListener) {

        this.context = context;
        this.list = list;
        this.myItemClickListener = myItemClickListener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        rowCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row_category,viewGroup,false);



        return new MyViewHolder(rowCategoryBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.MyViewHolder myViewHolder, final int i) {



        Category item = list.get(i);

        rowCategoryBinding.tvTitle.setText(item.getName());


        rowCategoryBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("shahbaz","position : "+rowCategoryBinding);
                myItemClickListener.onItemClick(i,v,true);
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RowCategoryBinding rowCategoryBinding;


        public MyViewHolder(@NonNull RowCategoryBinding rowCategoryBinding) {


            super(rowCategoryBinding.getRoot());
            this.rowCategoryBinding = rowCategoryBinding;


        }

    }
}
