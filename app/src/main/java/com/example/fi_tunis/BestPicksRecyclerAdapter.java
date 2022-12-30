package com.example.fi_tunis;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class BestPicksRecyclerAdapter extends RecyclerView.Adapter<BestPicksRecyclerAdapter.ViewHolder>{

    Integer[] img;
    Context context;
    Class intentClass;


    public BestPicksRecyclerAdapter(Integer[] img, Context context , Class intentClass) {
        this.img=img;
        this.context=context;
        this.intentClass=intentClass;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bestpick_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.image.setImageResource(img[position]);

        int pos=position;
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,intentClass);
                intent.putExtra("pos",pos);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}

