package com.example.timefighter.trial1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class main_grid extends AppCompatActivity {
    List<item> firstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstItem= new ArrayList<>();
        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
        firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));firstItem.add(new item("Maggi","descrip","category",R.drawable.x));
        firstItem.add(new item("Maggi2","descrip2","category2",R.drawable.y));
        firstItem.add(new item("Maggi3","descrip3","category3",R.drawable.z));
        RecyclerView myrv= (RecyclerView) findViewById(R.id.recyclerview_id);
        RecylerViewAdapter myAdapter= new RecylerViewAdapter(this,firstItem);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
    }
}