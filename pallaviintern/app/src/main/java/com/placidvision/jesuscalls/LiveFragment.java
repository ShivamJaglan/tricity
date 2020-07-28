package com.placidvision.jesuscalls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

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

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;







public class LiveFragment extends Fragment {
    private static LiveFragment LiveFragment;
//    private final String URL = Links.getLiveTVJSONURL("test", 1);
private String URL;
    private RecyclerView recyclerView;
    private TextView textView;
    private LiveAdapter adapter;
    private ArrayList<VideoInfo> videos;
    private static Context context;


    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiverrrr receiverrrr;
    private boolean isConnected = true;
private String username;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_tv, container, false);

        MainActivity mainActivity=(MainActivity)getActivity();
        username=mainActivity.getMyUsername();

URL=Links.getLiveTVJSONURL(username, 1);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiverrrr = new NetworkChangeReceiverrrr();
        getActivity().registerReceiver(receiverrrr, filter);





        videos = new ArrayList<>();

        adapter = new LiveAdapter(videos);

        recyclerView = view.findViewById(R.id.live_rooms);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);

        textView = view.findViewById(R.id.live_tv_status);

        JSONDownloader downloader = new JSONDownloader(URL);
        downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
            @Override
            public void onJSONLoad(JSONArray array) {
                try {
                    videos.addAll(new ArrayList<>(Arrays.asList(JSONParser.getVideos(array.getJSONObject(0).getJSONArray("type"), true))));
                    Log.d("JSON", videos.get(0).getName());
                } catch (Exception e) { e.printStackTrace(); }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
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
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(R.string.live_tv_is_not_available);
                    }
                });
            }
        });
        downloader.start();
        return view;
    }






    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiverrrr,filter);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
        if (receiverrrr != null) {
            getActivity().unregisterReceiver(receiverrrr);
            receiverrrr = null;
        }
//        getActivity().unregisterReceiver(receiver);

    }
    @Override
    public void onPause() {
        super.onPause();
        if (receiverrrr != null) {
            getActivity().unregisterReceiver(receiverrrr);
            receiverrrr = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        if (receiverrrr != null) {
            getActivity().unregisterReceiver(receiverrrr);
            receiverrrr = null;
        }

//        getActivity().unregisterReceiver(receiver);

    }
    public class NetworkChangeReceiverrrr extends BroadcastReceiver {

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









    public static LiveFragment getInstance(Context con) {
        context = con;
        if (LiveFragment == null)
            LiveFragment = new LiveFragment();
        return LiveFragment;
    }


    public static class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.MyViewHolder> {
        private ArrayList<VideoInfo> videos;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            View v;
            public MyViewHolder(View v) {
                super(v);
                this.v = v;
                image = v.findViewById(R.id.thumbnail_iv);
            }
        }

        public LiveAdapter(ArrayList<VideoInfo> videos) {
            this.videos = videos;
        }

        @Override
        public LiveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (videos.get(position) != null) {
                Glide.with(context).load(videos.get(position).getThumbnailURL()).placeholder(R.drawable.jc).into(holder.image);
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video", videos.get(position));
                        context.startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }
    }

}
