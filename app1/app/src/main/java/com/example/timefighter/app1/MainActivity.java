package com.example.timefighter.app1;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
    private TextView myMessage;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMessage = (TextView) findViewById(R.id.myMessage);
        this.gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        myMessage.setText("singleTapconfirmed");

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        myMessage.setText("onDoubleTapconfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        myMessage.setText("onDoubleTapEventconfirmed");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        myMessage.setText("onDownconfirmed");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        myMessage.setText("onShowPressconfirmed");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        myMessage.setText("onSingleTapUpconfirmed");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        myMessage.setText("onScrollconfirmed");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        myMessage.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        myMessage.setText("onFlingconfirmed");
        return false;
    }
}