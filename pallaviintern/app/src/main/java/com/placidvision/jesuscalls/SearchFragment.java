package com.placidvision.jesuscalls;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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







public class SearchFragment extends Fragment {
    private static SearchFragment searchFragment;
    private RecyclerView recyclerView;
    private EditText editText;
    private static Context context;
    private ArrayList<VideoInfo> videoInfos;
    private SearchAdapter adapter;



    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiverrrrrr receiverrrrrr;
    private boolean isConnected = true;
private String username;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        videoInfos = new ArrayList<>();
        MainActivity mainActivity=(MainActivity)getActivity();
        username=mainActivity.getMyUsername();





        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiverrrrrr = new NetworkChangeReceiverrrrrr();
        getActivity().registerReceiver(receiverrrrrr, filter);






        recyclerView = view.findViewById(R.id.search_results);
        adapter = new SearchAdapter(videoInfos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        editText = view.findViewById(R.id.search_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    videoInfos.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    JSONDownloader downloader = new JSONDownloader(getSearchUrl(charSequence.toString(), username));
                    downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
                        @Override
                        public void onJSONLoad(JSONArray array) {
                            videoInfos.clear();
                            videoInfos.addAll(new ArrayList<VideoInfo>(Arrays.asList(JSONParser.getSearchVideos(array))));
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
                            videoInfos.clear();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    downloader.start();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return view;
    }









    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiverrrrrr,filter);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
        if (receiverrrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrrr);
            receiverrrrrr = null;
        }
//        getActivity().unregisterReceiver(receiver);

    }
    @Override
    public void onPause() {
        super.onPause();
        if (receiverrrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrrr);
            receiverrrrrr = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        if (receiverrrrrr != null) {
            getActivity().unregisterReceiver(receiverrrrrr);
            receiverrrrrr = null;
        }

//        getActivity().unregisterReceiver(receiver);

    }
    public class NetworkChangeReceiverrrrrr extends BroadcastReceiver {

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













    public static SearchFragment getInstance(Context con) {
        context = con;
        if (searchFragment == null)
            searchFragment = new SearchFragment();
        return searchFragment;
    }

    private String getSearchUrl(String search, String username) {
        return Links.getSearchResultsJSONURL(search, username);
    }

    public static class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
        private ArrayList<VideoInfo> videos;

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;

            public MyViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.thumbnail_iv);
            }
        }

        public SearchAdapter(ArrayList<VideoInfo> videos) {
            this.videos = videos;
        }

        @Override
        public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(context).load(videos.get(position).getThumbnailURL()).placeholder(R.drawable.jc).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("video", videos.get(position));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }
    }

}
