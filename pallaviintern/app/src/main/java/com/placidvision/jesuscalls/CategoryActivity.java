package com.placidvision.jesuscalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

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





public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private CategoryAdapter adapter;
    private ArrayList<VideoInfo> videos;




    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiverr receiverr;
    private boolean isConnected = true;
    private TextView networkStatus;
    private String username;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Bundle bundle = getIntent().getExtras();
    username=bundle.getString("username");


        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiverr = new NetworkChangeReceiverr();
        registerReceiver(receiverr, filter);

        networkStatus = (TextView) findViewById(R.id.networkStatus);




        videos = new ArrayList<>();
        videos.clear();

        adapter = new CategoryAdapter(videos);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(bundle.getString("category_name", "Category"));

        recyclerView = findViewById(R.id.category_videos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 50, false));
        recyclerView.setAdapter(adapter);

        int id = bundle.getInt("id", -1);


        if (id != -1) {
            downloadAndUpdate(id);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }







    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
       registerReceiver(receiverr,filter);

    }
//
    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
        if (receiverr != null) {
            unregisterReceiver(receiverr);
            receiverr = null;
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if (receiverr != null) {
            unregisterReceiver(receiverr);
            receiverr = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        if (receiverr != null) {
            unregisterReceiver(receiverr);
            receiverr = null;
        }

//       unregisterReceiver(receiverr);

    }
    public class NetworkChangeReceiverr extends BroadcastReceiver {

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














    void downloadAndUpdate(int id) {
        JSONDownloader downloader = new JSONDownloader(Links.getCategoryJSONURL(username, id), true);
        downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
            @Override
            public void onJSONLoad(JSONArray array) {
                try {
                    VideoInfo[] videoInfos = JSONParser.getVideos(array.getJSONObject(0).getJSONArray(id + ""), true);
                    videos.addAll(new ArrayList<>(Arrays.asList(videoInfos)));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) { e.printStackTrace(); }

            }

            @Override
            public void onConnectException() {

            }

            @Override
            public void onJSONException() {

            }
        });
        downloader.start();
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
        private ArrayList<VideoInfo> videos;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;

            public MyViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.thumbnail_iv);
            }
        }

        public CategoryAdapter(ArrayList<VideoInfo> videos) {
            this.videos= videos;
        }

        @Override
        public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (videos.get(position) != null)
            Glide.with(getApplicationContext()).load(videos.get(position).getThumbnailURL()).placeholder(R.drawable.jc).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                    intent.putExtra("video", videos.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}