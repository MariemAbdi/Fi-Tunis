package com.example.fi_tunis;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    String[] ids;
    String[] names;
    String[] img;
    Context context;
    Class intentClass;
    String type;

    public MyRecyclerAdapter(String[] ids,String[] names, String[] img, Context context, Class intentClass, String type ) {
        this.ids=ids;
        this.names=names;
        this.img=img;
        this.type=type;
        this.intentClass=intentClass;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recyclerview_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(names[position]);

        int pos=position;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,intentClass);
                intent.putExtra("id",ids[pos]);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });

        Glide.with(context).load(img[position]).placeholder(R.drawable.logo).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            layout=itemView.findViewById(R.id.pageLayout);
            image=itemView.findViewById(R.id.image);
        }
    }
}

