package com.example.timefighter.login.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.timefighter.login.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class firecloud_main extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private  FirestoreRecyclerAdapter adapter;
    private ImageView list_image;
    private Context mContext;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firecloud_main);
        list_image=(ImageView)findViewById(R.id.list_image);
        mFirestoreList = findViewById(R.id.firestore_list);
        firebaseFirestore = FirebaseFirestore.getInstance();
//        query
        Query query = firebaseFirestore.collection("Products");
//        recycler options
        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class).build();
       adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {


            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);

                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                ProductsModel currentProduct;
        holder.list_name.setText(model.getName());
        holder.list_price.setText(model.getPrice()+"");
                firebaseStorage= FirebaseStorage.getInstance();
                StorageReference gsrefer= firebaseStorage.getReferenceFromUrl(model.getImageurl());

                Glide.with(firecloud_main.this).load(gsrefer.getDownloadUrl().toString()).into(list_image);

            }
        };
       mFirestoreList.setHasFixedSize(true);
       mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
       mFirestoreList.setAdapter(adapter);

    };
//        View holder



    private class ProductsViewHolder extends RecyclerView.ViewHolder{
       private TextView list_name;
       private TextView list_price;

//        private ImageView list_image;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name=itemView.findViewById(R.id.list_name);
            list_price=itemView.findViewById(R.id.list_price);
//            list_image=itemView.findViewById(R.id.list_image);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}