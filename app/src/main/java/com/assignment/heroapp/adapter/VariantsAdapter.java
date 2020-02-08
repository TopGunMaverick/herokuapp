package com.assignment.heroapp.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.assignment.heroapp.R;
import com.assignment.heroapp.databinding.RowVariantsBinding;
import com.assignment.heroapp.models.Variant;

import java.util.List;

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.MyViewHolder> {

    List<Variant> list;
    Activity context;
    RowVariantsBinding rowVariantsBinding;
    LayoutInflater layoutInflater;


    public VariantsAdapter(Activity context, List<Variant> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VariantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        rowVariantsBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row_variants,viewGroup,false);



        return new MyViewHolder(rowVariantsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantsAdapter.MyViewHolder myViewHolder, int i) {
        Variant item = list.get(i);

        rowVariantsBinding.tvColor.setText("Color : "+item.getColor());
        rowVariantsBinding.tvSize.setText("Size : "+item.getSize());
        rowVariantsBinding.tvPrice.setText("Price : "+item.getPrice());

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

        RowVariantsBinding rowVariantsBinding;


        public MyViewHolder(@NonNull RowVariantsBinding rowVariantsBinding) {


            super(rowVariantsBinding.getRoot());
            this.rowVariantsBinding = rowVariantsBinding;


        }

    }
}
