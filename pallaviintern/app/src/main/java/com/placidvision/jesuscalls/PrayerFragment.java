package com.placidvision.jesuscalls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;






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
import android.widget.VideoView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;






//public class PrayerFragment extends Fragment implements SurfaceHolder.Callback {
public class PrayerFragment extends Fragment{
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
private MediaPlayer mp = null;
private SurfaceHolder holder;
        SurfaceView mSurfaceView=null;
    private static PrayerFragment prayerFragment;
    private final String URL = Links.VIDEO_CONF;
    private RecyclerView recyclerView;
    private PrayerAdapter adapter;
    private ArrayList<PrayerRoom> rooms;
    private static Context context;
    private static Boolean activityrunning=true;

private VideoView videoView;



    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiverrrrr receiverrrrr;
    private boolean isConnected = true;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prayer, container, false);
//        mp = new MediaPlayer();
//        mSurfaceView = (SurfaceView) getActivity().findViewById(R.id.surface);
//        holder = mSurfaceView .getHolder();
//        mSurfaceView.getHolder().addCallback(this);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);




//       videoView = (VideoView) getActivity().findViewById(R.id.videoView);
//        Uri uri = Uri.parse("android.resource://com.placidvision.jesuscalls/R.raw.videoground");
//        videoView.setVideoURI(uri);
//        videoView.start();
        videoBG = (VideoView) view.findViewById(R.id.videoVieww);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoground);
        videoBG.setVideoURI(uri);
        // Start the VideoView

//        videoBG.start();
//        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mMediaPlayer = mediaPlayer;
//                // We want our video to play over and over so we set looping to true.
//                mMediaPlayer.setLooping(true);
//                // We then seek to the current posistion if it has been set and play the video.
//                if (mCurrentVideoPosition != 0) {
//                    mMediaPlayer.seekTo(mCurrentVideoPosition);
//                    mMediaPlayer.start();
//                }
//            }
//        });



        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiverrrrr = new NetworkChangeReceiverrrrr();
        getActivity().registerReceiver(receiverrrrr, filter);





        rooms = new ArrayList<>();

        adapter = new PrayerAdapter(rooms);

        recyclerView = view.findViewById(R.id.prayers);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

//        JSONDownloader downloader = new JSONDownloader(URL);
//        downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
//            @Override
//            public void onJSONLoad(JSONArray array) {
//                rooms.addAll(new ArrayList<>(Arrays.asList(JSONParser.getPrayerRooms(array))));
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//            @Override
//            public void onConnectException() {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, R.string.check_internet, Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onJSONException() {
//
//            }
//        });
//        downloader.start();
        content();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });


    }

    public void content(){
        ArrayList<PrayerRoom> roomss = new ArrayList<>();


        JSONDownloader downloader = new JSONDownloader(URL);

        downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
            @Override
            public void onJSONLoad(final JSONArray array) {
                if (getActivity() != null){
//                getActivity().runOnUiThread(new Runnable()
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
//                        tree1=mapper.readTree(JSONParser.getCategories((array)));
                        roomss.addAll(new ArrayList<>(Arrays.asList(JSONParser.getPrayerRooms(array))));

                        if (roomss != null && roomss.size()!= rooms.size() ) {
//                           && !(categoryInfoss.equals(categoryInfos))
                            Log.v(LOG_TAG,"roomss "+ roomss.containsAll(rooms));
                            Log.v(LOG_TAG,"rooms "+ rooms.size());

                          rooms.clear();
                           rooms.addAll(roomss);
                            adapter.notifyDataSetChanged();
                            Log.v(LOG_TAG,"List changed in if");
                        }else if(roomss.size()==rooms.size()){
                          Boolean equal=true;
                            for (int i = 0; i <roomss.size() ; i++) {
                                    if(roomss.get(i).equals(rooms.get(i))==false) { equal=false;}
                            }

                            if(equal=false){Log.v(LOG_TAG,"roomss "+ roomss.containsAll(rooms) );
                            Log.v(LOG_TAG,"categoryinfos "+ rooms.size());

                            rooms.clear();
                            rooms.addAll(roomss);
                            adapter.notifyDataSetChanged();
                            Log.v(LOG_TAG,"List changed in else");} }

//                        }else if(!(categoryInfoss.containsAll(categoryInfos) && categoryInfos.containsAll(categoryInfoss))){
//                            Log.v(LOG_TAG,"cjnategoryinfoss "+ categoryInfoss.containsAll(categoryInfos) );
//                            Log.v(LOG_TAG,"categoryinfos "+ categoryInfos.size());
//
//                            categoryInfos.clear();
//                            categoryInfos.addAll(categoryInfoss);
//                            adapter.notifyDataSetChanged();
//                            Log.v(LOG_TAG,"List changed in else");
//
//                        }
                    }
                });}
            }

            @Override
            public void onConnectException() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, R.string.check_internet, Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onJSONException() {

            }
        });

        downloader.start();
        refresh(40000);
    }

    private void refresh(int milliseconds)
    {   if (activityrunning==true) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
//                handler.postDelayed(this,15000);
            }
        };
        handler.postDelayed(runnable, 40000);
        Log.v(LOG_TAG, "refreshed list");
//        Toast.makeText(context, "refreshed list", Toast.LENGTH_LONG).show();
    }
    Log.v(LOG_TAG,"activityrunning"+ activityrunning);
    }










    @Override
    public void onResume() {
        super.onResume();
//    videoView.start();
        activityrunning=true;
        content();
        videoBG.start();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiverrrrr,filter);

    }

    @Override
    public void onStop() {
        super.onStop();
        activityrunning=false;
        Log.v(LOG_TAG, "onStop");
        if (receiverrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrr);
            receiverrrrr = null;
        }
//        getActivity().unregisterReceiver(receiver);

    }
    @Override
    public void onPause() {
        activityrunning=false;
        super.onPause();
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
        if (receiverrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrr);
            receiverrrrr = null;
        }
    }

    @Override
    public void onDestroy() {
        activityrunning=false;

        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;

        if (receiverrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrr);
            receiverrrrr = null;
        }

//        getActivity().unregisterReceiver(receiver);

    }

//    @Override
//    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//        Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
//                + R.raw.videoground);
//
//        try{
//            mp.setDataSource(getActivity().getApplicationContext(),video);
//            mp.prepare();
//            mp.start();
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();}
//
//        //Get the dimensions of the video
//        int videoWidth = mp.getVideoWidth();
//        int videoHeight = mp.getVideoHeight();
//
//        //Get the width of the screen
//        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//
//        //Get the SurfaceView layout parameters
//        android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
//
//        //Set the width of the SurfaceView to the width of the screen
//        lp.width = screenWidth;
//
//        //Set the height of the SurfaceView to match the aspect ratio of the video
//        //be sure to cast these as floats otherwise the calculation will likely be 0
//        lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);
//
//        //Commit the layout parameters
//        mSurfaceView.setLayoutParams(lp);
//
//        //Start video
//        mp.setDisplay(holder);
//        mp.start();
//
//    }
//
//    @Override
//    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
//
//    }

    public class NetworkChangeReceiverrrrr extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);

        }
    }
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if(!isConnected){
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
//                            networkStatus.setText("Now you are connected to Internet!");
                            Toast.makeText(context, "Now you are connected to Internet!", Toast.LENGTH_LONG).show();

                            isConnected = true;
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            if (Build.VERSION.SDK_INT >= 26) {
                                ft.setReorderingAllowed(false);
                            }
                            ft.detach(this).attach(this).commit();
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                fragmentManager.beginTransaction().detach(this).commitNow();
//                                fragmentManager.beginTransaction().attach(this).commitNow();
//                            } else {
//                                fragmentManager.beginTransaction().detach(this).attach(this).commit();
//                            }
//                            getActivity().finish();
//                            getActivity().overridePendingTransition(0, 0);
//                            startActivity(getActivity().getIntent());
//                            getActivity().overridePendingTransition(0, 0);
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











    public static PrayerFragment getInstance(Context con) {
        context = con;
        if (prayerFragment == null)
            prayerFragment = new PrayerFragment();
        return prayerFragment;
    }

    private static void startRoom(String url, String name) {
        URL serverURL;
        try {
            serverURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        JitsiMeetConferenceOptions options
                = new JitsiMeetConferenceOptions.Builder()
                .setRoom(name)
                .build();
        JitsiMeetActivity.launch(context, options);
    }

    public static class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.MyViewHolder> {
        private ArrayList<PrayerRoom> rooms;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image, status;
            TextView textView;
            View v;
            public MyViewHolder(View v) {
                super(v);
                this.v = v;
                image = v.findViewById(R.id.room_image);
                textView = v.findViewById(R.id.room_name);
                status = v.findViewById(R.id.room_status);
            }
        }

        public PrayerAdapter(ArrayList<PrayerRoom> rooms) {
            this.rooms = rooms;
        }

        @Override
        public PrayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.room_item, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(rooms.get(position).getRoomName());
            Glide.with(context).load(rooms.get(position).getThumbnail()).placeholder(R.drawable.jc).into(holder.image);
            switch (rooms.get(position).getStatus()) {
                case PrayerRoom.OFFLINE:
                    holder.status.setImageResource(R.drawable.noprayerrr);
                    break;
                case PrayerRoom.ONLINE:
                    holder.status.setImageResource(R.drawable.yesprayer);
                    break;
            }
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rooms.get(position).getStatus().equals(PrayerRoom.ONLINE))
                        startRoom(rooms.get(position).getUrl(), rooms.get(position).getRoomName());
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                        builder.setCancelable(true)
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("The Room is Offline.");
                        alert.show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return rooms.size();
        }
    }

}
