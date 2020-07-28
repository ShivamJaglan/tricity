package com.placidvision.jesuscalls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.placidvision.jesuscalls.viewpager.CustomViewPager;

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
import androidx.fragment.app.FragmentManager;

import static java.lang.Boolean.TRUE;


public class HomeFragment extends Fragment {
    private static Activity context;
//    private final String JSON_URL = Links.getMainPageJSONURL("text", 1);
//    private final String JSON_URL_CAROUSEL = Links.getMainPageSlideShowJSONURL("test");
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<CategoryInfo> categoryInfos;
    private  VideoInfo[] slideVideos;
    private static HomeFragment homeFragment;
    private CategoryAdapter adapter;
    private Boolean POSTITION=true;
    private static Boolean activityrunning=true;


    private static final String LOG_TAG = "CheckNetworkStatus";
    private static NetworkChangeReceiver receiver;
    private boolean isConnected = true;
    private TextView networkStatus;
    private CategoryInfo pseudoobj;
    private String username;
    private  String JSON_URL;
    private  String JSON_URL_CAROUSEL;

//    private ObjectMapper mapper= new ObjectMapper();
//  private JsonNode tree1;
//private JsonNode tree2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home, container, false);



    MainActivity mainActivity=(MainActivity)getActivity();
    username=mainActivity.getMyUsername();
    JSON_URL=Links.getMainPageJSONURL(username, 1);
    JSON_URL_CAROUSEL=Links.getMainPageSlideShowJSONURL(username);
Log.v(LOG_TAG,"JSON URL IS"+ JSON_URL);
Log.v(LOG_TAG,"json carousel url"+JSON_URL_CAROUSEL);



        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        getActivity().registerReceiver(receiver, filter);

        networkStatus = (TextView) view.findViewById(R.id.networkStatus);

        pseudoobj= new CategoryInfo(-1,"pseudo",slideVideos);




        categoryInfos = new ArrayList<>();
        categoryInfos.add(pseudoobj);
        adapter = new CategoryAdapter(categoryInfos);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

//        JSONDownloader task = new JSONDownloader(JSON_URL);
//
//        task.setOnJSONLoadListener(new OnJSONLoadListener() {
//            @Override
//            public void onJSONLoad(final JSONArray array) {
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        categoryInfos.addAll(Arrays.asList(JSONParser.getCategories(array)));
//                        progressBar.setVisibility(View.GONE);
//                        if (categoryInfos != null) {
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onConnectException() {
//                showToast(getResources().getString(R.string.check_internet));
//            }
//
//            @Override
//            public void onJSONException() {
//
//            }
//        });
//
//        task.start();
      content();
        return view;
    }
    public void content(){
        ArrayList<CategoryInfo> categoryInfoss = new ArrayList<>();

        categoryInfoss.add(pseudoobj);
        JSONDownloader task = new JSONDownloader(JSON_URL);

        task.setOnJSONLoadListener(new OnJSONLoadListener() {
            @Override
            public void onJSONLoad(final JSONArray array) {
                if (getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        tree1=mapper.readTree(JSONParser.getCategories((array)));
                        categoryInfoss.addAll(Arrays.asList(JSONParser.getCategories(array)));
                        progressBar.setVisibility(View.GONE);
                        if (categoryInfoss != null && categoryInfoss.size()!= categoryInfos.size() ) {
//                           && !(categoryInfoss.equals(categoryInfos))
                            Log.v(LOG_TAG,"cjnategoryinfoss "+ categoryInfoss.containsAll(categoryInfos));
                            Log.v(LOG_TAG,"categoryinfos "+ categoryInfos.size());

                           categoryInfos.clear();
                           categoryInfos.addAll(categoryInfoss);
                            adapter.notifyDataSetChanged();
                            Log.v(LOG_TAG,"List changed in if");
                        }else if(categoryInfoss.size()==categoryInfos.size()){
                          Boolean equal=true;
                            for (int i = 0; i <categoryInfoss.size() ; i++) {
                                    if(categoryInfoss.get(i).equals(categoryInfos.get(i))==false) { equal=false;}
                            }

                            if(equal=false){Log.v(LOG_TAG,"cjnategoryinfoss "+ categoryInfoss.containsAll(categoryInfos) );
                            Log.v(LOG_TAG,"categoryinfos "+ categoryInfos.size());

                            categoryInfos.clear();
                            categoryInfos.addAll(categoryInfoss);
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
                showToast(getResources().getString(R.string.check_internet));
            }

            @Override
            public void onJSONException() {

            }
        });

        task.start();
        refresh(40000);
    }
//    private void refresh(int milliseconds)
//    {   if (activityrunning==true) {
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                content();
////                handler.postDelayed(this,15000);
//            }
//        };
//        handler.postDelayed(runnable, 15000);
//        Log.v(LOG_TAG, "refreshed list");
//        Toast.makeText(context, "refreshed list", Toast.LENGTH_LONG).show();
//    }
//        Log.v(LOG_TAG,"activityrunning"+ activityrunning);
//    }
private void refresh(int milliseconds){
    if (activityrunning==true) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                content();
                Log.v(LOG_TAG, "refreshed list");
//                Toast.makeText(context, "refreshed list", Toast.LENGTH_LONG).show();
            }
        }, 40000);

    }
    Log.v(LOG_TAG,"activityrunning"+ activityrunning);
}




    public static HomeFragment getInstance(Activity con) {
        context = con;
        if (homeFragment == null)
                homeFragment = new HomeFragment();
        return homeFragment;
    }

    void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activityrunning=true;
        content();
        Log.v(LOG_TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiver,filter);

    }

    @Override
    public void onStop() {
        super.onStop();
        activityrunning=false;
        Log.v(LOG_TAG, "onStop");
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }
//        getActivity().unregisterReceiver(receiver);

    }
    @Override
    public void onPause() {
        super.onPause();
        activityrunning=false;
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
        activityrunning=false;
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }

//        getActivity().unregisterReceiver(receiver);

    }
    public class NetworkChangeReceiver extends BroadcastReceiver {

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
                            networkStatus.setText("Now you are connected to Internet!");
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
        networkStatus.setText("You are not connected to Internet!");
        Toast.makeText(context, "You are not connected to Internet!", Toast.LENGTH_LONG).show();
        isConnected = false;
        return false;
    }







    public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<CategoryInfo> categoryInfos;
        private static final int VIEW_TYPE_CAROUSEL = 0;
        private static final int VIEW_TYPE_CATEGORY = 1;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public Button cTextView;
            public RecyclerView vLayout;

            public MyViewHolder(View view) {
                super(view);
                cTextView = view.findViewById(R.id.category_name);
                vLayout = view.findViewById(R.id.video_list);
            }
        }

        public class SlideViewHolder extends RecyclerView.ViewHolder {
            private CustomViewPager viewPager;
            private LinearLayout dotLayout;
            private ImageButton leftIb, rightIb;

            public SlideViewHolder(View view) {
                super(view);
                viewPager = view.findViewById(R.id.view_pager);
                dotLayout = view.findViewById(R.id.dot_indicator);
                leftIb = view.findViewById(R.id.left_ib);
                rightIb = view.findViewById(R.id.right_ib);
            }
        }

        public CategoryAdapter(ArrayList<CategoryInfo> categoryInfos) {
            this.categoryInfos = categoryInfos;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
            if (viewType == VIEW_TYPE_CATEGORY) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category, parent, false);
                return new MyViewHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_show, parent,false);
                return new SlideViewHolder(v);
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (getItemViewType(position) == VIEW_TYPE_CAROUSEL) {
                SlideViewHolder holder1 = (SlideViewHolder) holder;
                if (slideVideos == null) {
                    JSONDownloader task = new JSONDownloader(JSON_URL_CAROUSEL);
                    task.setOnJSONLoadListener(new OnJSONLoadListener() {
                        @Override
                        public void onJSONLoad(JSONArray array) {
                            slideVideos = JSONParser.getVideos(array, false);
                        }

                        @Override
                        public void onConnectException() {

                        }

                        @Override
                        public void onJSONException() {

                        }
                    });
                    task.start();
                    try { task.join(); } catch (InterruptedException e) { e.printStackTrace(); }
                }

                final CustomViewPager viewPager = ((SlideViewHolder) holder).viewPager;
                final LinearLayout dotLayout = ((SlideViewHolder) holder).dotLayout;
                ImageButton leftIB = ((SlideViewHolder) holder).leftIb;
                ImageButton rightIB = ((SlideViewHolder) holder).rightIb;

                leftIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.previous();
                    }
                });

                rightIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.next();
                    }
                });

                viewPager.setArrayAdapter(new ArrayList<VideoInfo>(Arrays.asList(slideVideos)));
                viewPager.setDotLayout(dotLayout);
                viewPager.setCurrentItem(8000, false);

            } else {

//                if(POSTITION==true) {
//                    POSTITION=false;
//                    MyViewHolder holder1 = (MyViewHolder) holder;
//                    holder1.cTextView.setText(categoryInfos.get(0).getName());
//                    holder1.vLayout.setLayoutManager(new LinearLayoutManager(
//                            holder1.vLayout.getContext(),
//                            RecyclerView.HORIZONTAL,
//                            false));
//                    holder1.vLayout.setAdapter(new VideoAdapter(categoryInfos.get(0).getVideoInfos()));
//
//                    holder1.cTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            int id = categoryInfos.get(0).getId();
////                        Timber.d("ID", id);
////                        onDestroy();
////                        getActivity().unregisterReceiver(receiver);
//                            Intent intent = new Intent(context, CategoryActivity.class);
//                            intent.putExtra("category_name", categoryInfos.get(0).getName());
//                            intent.putExtra("id", id);
//                            startActivity(intent);
//                        }
//                    });
//                }
                MyViewHolder holder1 = (MyViewHolder) holder;
                holder1.cTextView.setText(categoryInfos.get(position).getName());
                holder1.vLayout.setLayoutManager(new LinearLayoutManager(
                        holder1.vLayout.getContext(),
                        RecyclerView.HORIZONTAL,
                        false));
                holder1.vLayout.setAdapter(new VideoAdapter(categoryInfos.get(position).getVideoInfos()));

                holder1.cTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = categoryInfos.get(position).getId();
//                        Timber.d("ID", id);
//                        onDestroy();
//                        getActivity().unregisterReceiver(receiver);
                        Intent intent = new Intent(context, CategoryActivity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("category_name", categoryInfos.get(position).getName());
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

            }
        }



        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return VIEW_TYPE_CAROUSEL;
            return VIEW_TYPE_CATEGORY;
        }


        @Override
        public int getItemCount() {
            return categoryInfos.size();
        }
    }

    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
        private VideoInfo[] videoInfos;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public MyViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.thumbnail_iv);
            }
        }

        public VideoAdapter(VideoInfo[] videoInfos) {
            this.videoInfos = videoInfos;
        }

        @Override
        public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(context).load(videoInfos[position].getThumbnailURL()).placeholder(R.drawable.jc).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("video", videoInfos[position]);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return videoInfos.length;
        }
    }

}
