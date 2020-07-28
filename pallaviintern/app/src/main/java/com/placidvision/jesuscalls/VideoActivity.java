package com.placidvision.jesuscalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;





public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView videoImage;
    private Toolbar toolbar;
    private TextView name, description;
    private Button playVideoButton, playPreviewButton;
    private VideoStreamActivity videoStream;
    private VideoInfo videoInfo;






    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiveerr receiveerr;
    private boolean isConnected = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);



        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiveerr = new NetworkChangeReceiveerr();
        registerReceiver(receiveerr, filter);




        toolbar = findViewById(R.id.toolbar);
        videoImage = findViewById(R.id.video_thumbnail);
        name = findViewById(R.id.video_name);
        description = findViewById(R.id.video_description);
        playVideoButton = findViewById(R.id.play_video_btn);
        playPreviewButton = findViewById(R.id.play_preview_btn);

        videoInfo = (VideoInfo) getIntent().getSerializableExtra("video");

        Glide.with(getApplicationContext()).load(videoInfo.getThumbnailURL()).placeholder(R.drawable.jc).into(videoImage);
        name.setText(videoInfo.getName());
        description.setText(videoInfo.getDescription());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(videoInfo.getName());


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        playVideoButton.setOnClickListener(this);
        playPreviewButton.setOnClickListener(this);

//        if(videoInfo.getPreviewURL() == null)
//            playPreviewButton.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), VideoStreamActivity.class);

        switch (view.getId()) {
            case R.id.play_video_btn:
                intent.putExtra("url", videoInfo.getVideoURL());
                startActivity(intent);
                break;
            case R.id.play_preview_btn:
                intent.putExtra("url", videoInfo.getPreviewURL());
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiveerr,filter);

    }
    //
    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
        if (receiveerr != null) {
            unregisterReceiver(receiveerr);
            receiveerr = null;
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if (receiveerr != null) {
            unregisterReceiver(receiveerr);
            receiveerr = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        if (receiveerr != null) {
            unregisterReceiver(receiveerr);
            receiveerr = null;
        }

//       unregisterReceiver(receiverr);

    }
    public class NetworkChangeReceiveerr extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailablee(context);

        }
    }
    private boolean isNetworkAvailablee(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        Log.v(LOG_TAG, "" + isConnected);
                        if(!isConnected){
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
//                            networkStatus.setText("Now you are connected to Internet!");
                            Toast.makeText(context, "Now you are connected to Internet!", Toast.LENGTH_LONG).show();

                            isConnected = true;
//                            FragmentTransaction ft = getFragmentManager().beginTransaction();
//                            if (Build.VERSION.SDK_INT >= 26) {
//                                ft.setReorderingAllowed(false);
//                            }
//                            ft.detach(this).attach(this).commit();
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                fragmentManager.beginTransaction().detach(this).commitNow();
//                                fragmentManager.beginTransaction().attach(this).commitNow();
//                            } else {
//                                fragmentManager.beginTransaction().detach(this).attach(this).commit();
//                            }
                            finish();
                            startActivity(getIntent());

//                            finish();
//                           overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);
                            //do your processing here ---
                            //if you need to post any data to the server or get status
                            //update from the server
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
//        networkStatus.setText("You are not connected to Internet!");
        Toast.makeText(context, "You are not connected to Internet!", Toast.LENGTH_LONG).show();
        isConnected = false;
        return false;
    }


}