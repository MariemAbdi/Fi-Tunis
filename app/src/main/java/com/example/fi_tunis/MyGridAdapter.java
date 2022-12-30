package com.example.fi_tunis;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;


public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {

    private Context context;
    String[] img;

    public MyGridAdapter(Context context, String[] img) {
        this.context=context;
        this.img=img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_grid_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context).load(img[position]).placeholder(R.drawable.logo).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return img.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Image);

        }
    }
}
