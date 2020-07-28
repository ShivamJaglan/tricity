package com.example.myapplication;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    List<Books> lstBook ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        lstBook = new ArrayList<>();
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.rounded_button_solid));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.rounded_button_solid));

        RecyclerView myrv = (RecyclerView) getView().findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(),lstBook);
        myrv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));
        myrv.setAdapter(myAdapter);


    }
}


