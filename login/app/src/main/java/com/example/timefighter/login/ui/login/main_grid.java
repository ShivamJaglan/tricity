package com.example.timefighter.login.ui.login;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timefighter.login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class main_grid extends AppCompatActivity {
   private List<item> mItems;
   private RecyclerView mRecyclerView;
   private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        firstItem= new ArrayList<>();
//        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
//        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
//        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
//        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
//        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
//        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
         mRecyclerView= findViewById(R.id.recyclerview_id);
         mRecyclerView.setHasFixedSize(true);
         mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
         mItems= new ArrayList<>();
         mDatabaseRef= FirebaseDatabase.getInstance().getReference("Products");
//        RecylerViewAdapter myAdapter= new RecylerViewAdapter(this,firstItem);
//        myrv.setLayoutManager(new GridLayoutManager(this, 3));
//        myrv.setAdapter(myAdapter);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    item itemm= postSnapshot.getValue(item.class);
                    mItems.add(itemm);
                }
            RecylerViewAdapter mAdapter= new RecylerViewAdapter(main_grid.this,mItems);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(main_grid.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}