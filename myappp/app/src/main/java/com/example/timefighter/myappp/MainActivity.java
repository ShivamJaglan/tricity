package com.example.timefighter.myappp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.graphics.Color;
import android.widget.EditText;
import android.content.res.Resources;
import android.util.TypedValue;
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    RelativeLayout MyLayout= new RelativeLayout(this);
    Button myButton= new Button(this);


    MyLayout.setBackgroundColor(Color.BLUE);
    myButton.setBackgroundColor(Color.GREEN);
    myButton.setText("Click Here");
    RelativeLayout.LayoutParams buttonDetails= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
     buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
     buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        MyLayout.addView(myButton,buttonDetails);


        EditText username= new EditText(this);
        myButton.setId(1);
        username.setId(2);
        RelativeLayout.LayoutParams usernameDetails= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        usernameDetails.addRule(RelativeLayout.ABOVE,myButton.getId());
        usernameDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usernameDetails.setMargins(0,0,0,50);
//        margin isliye set kiya bacause buton aur username me gap nahi tha
        MyLayout.addView(username,usernameDetails);
        Resources r=getResources();
       int pixels= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,r.getDisplayMetrics());
       username.setWidth(pixels);
//       this is to conbert pixels into a unit which is same for all devices
        setContentView(MyLayout);
    }
}