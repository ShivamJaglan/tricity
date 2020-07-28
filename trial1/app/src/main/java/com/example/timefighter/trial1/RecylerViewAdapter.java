package com.example.timefighter.trial1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<item> mData;

    public RecylerViewAdapter(Context mContext, List<item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view;
        LayoutInflater mInflator= LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_item_name.setText(mData.get(position).getTitle());
        holder.img_item_thumbnail.setImageResource(mData.get(position).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name;
        ImageView img_item_thumbnail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name=(TextView) itemView.findViewById(R.id.item_name);
            img_item_thumbnail=(ImageView) itemView.findViewById(R.id.item_image);

        }
    }
}

