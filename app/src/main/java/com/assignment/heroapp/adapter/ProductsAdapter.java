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
import com.assignment.heroapp.databinding.RowProductBinding;
import com.assignment.heroapp.interfaces.MyItemClickListener;
import com.assignment.heroapp.models.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    List<Product> list;
    Activity context;
    RowProductBinding rowProductBinding;
    LayoutInflater layoutInflater;

    private static MyItemClickListener myItemClickListener;


    public ProductsAdapter(Activity context, List<Product> list, MyItemClickListener myItemClickListener) {

        this.context = context;
        this.list = list;
        this.myItemClickListener = myItemClickListener;
    }

    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        rowProductBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row_product,viewGroup,false);



        return new MyViewHolder(rowProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder myViewHolder, final int i) {
        Product item = list.get(i);

        rowProductBinding.tvProdcutName.setText(item.getName());

        rowProductBinding.tvProdcutName.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RowProductBinding rowProductBinding;


        public MyViewHolder(@NonNull RowProductBinding rowProductBinding) {


            super(rowProductBinding.getRoot());
            this.rowProductBinding = rowProductBinding;


        }

    }
}
