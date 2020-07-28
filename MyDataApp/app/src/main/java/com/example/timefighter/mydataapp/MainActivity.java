package com.example.timefighter.mydataapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText myInput;
    TextView myText;
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myInput=(EditText) findViewById(R.id.myInput);
        myText=(TextView) findViewById(R.id.myText);
        dbHandler= new MyDBHandler(this,null,null,1);
        printDatabase();



        }
        public void printDatabase(){
        String dbstring=dbHandler.databaseToString();
        myText.setText(dbstring);
        myInput.setText("");


    }
    public void addButtonClick(View view)
    {
        Products products=  new Products(myInput.getText().toString());
        dbHandler.addProduct(products);
        printDatabase();
    }
    public void deleteButtonClick(View view)
    {
        String inputText=myInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }
}