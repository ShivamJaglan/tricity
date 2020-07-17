package com.example.timefighter.login.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timefighter.login.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<item> mData;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;

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
        item newitem= mData.get(position);
//        holder.img_item.setImageResource(mData.get(position).getThumbnail());
        firebaseStorage= FirebaseStorage.getInstance();
        StorageReference gsrefer= firebaseStorage.getReferenceFromUrl(mData.get(position).getImage_url());
//        private String= mData.get(position).getImage_url().
        Glide.with(mContext).load(gsrefer.getDownloadUrl().toString()).into(holder.img_item);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name;
        ImageView img_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name=(TextView) itemView.findViewById(R.id.item_name);
            img_item=(ImageView) itemView.findViewById(R.id.item_image);

        }
    }
}

