package com.example.timefighter.mylistapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(@NonNull Context context,String [] Fruits) {
        super(context, R.layout.custom_row ,Fruits);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator= LayoutInflater.from(getContext());
        View CustomView=myInflator.inflate(R.layout.custom_row,parent,false)      ;
//        get a reference
        String singlefruititem= getItem(position);
        TextView myText=(TextView)CustomView.findViewById(R.id.myText);
        ImageView myImage=(ImageView)CustomView.findViewById(R.id.myImage);
        myText.setText(singlefruititem);
        myImage.setImageResource(R.drawable.fruit);
        return CustomView;
    }
}
