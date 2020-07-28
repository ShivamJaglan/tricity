//package com.placidvision.jesuscalls;
////README
////this project was done by yashwant kr singh, under Placid-vison
////this application allows users to play hls stream on android phone
////we can select video bitrate (video quality ) according to users internet speed.
//// the user need to enter http url alone with stream name
//// Example - http://10.18.10.121/mystreamname
//// if user doesn't pass the url perimeter ,the appplication plays a hls stream which is pre defined in the app!!
//
//import android.app.AlertDialog;
//import android.content.pm.ActivityInfo;
//import android.net.Uri;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Pair;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
//import com.google.android.exoplayer2.source.ads.AdsMediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
////import com.google.android.exoplayer2.ui.TrackSelectionView;
//import com.google.android.exoplayer2.ui.TrackSelectionView;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayer;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.source.hls.HlsMediaSource;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//
//
//
//
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.fragment.app.FragmentManager;
//
//
//
//
//
//
//public class VideoStreamActivity extends AppCompatActivity {
//    private PlayerView playerView;
//    private SimpleExoPlayer player;
//    private View loading;
//    private ImageButton changeQuality;
//    private TrackGroupArray trackGroups;
//    private TrackSelector trackSelector;
//    private  MappingTrackSelector selector;
//    private  TrackSelection.Factory videoTrackSelectionFactory;
//    String space= "\n";
//    Uri URL;
//    private DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//
//
//
//
//
//    private static final String LOG_TAG = "CheckNetworkStatus";
//    private NetworkChangeReceiiveerr receiiveerr;
//    private boolean isConnected = true;
//    private ImaAdsLoader adsLoader;
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        receiiveerr = new NetworkChangeReceiiveerr();
//        registerReceiver(receiiveerr, filter);
//
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //will hide the status bar
//        setContentView(R.layout.activity_video_stream);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //will rotate the screen
//        Bundle b = getIntent().getExtras();
//        final String address =  b.getString("url", "");
//
//
//        URL = Uri.parse(address);
//        initializePlayer(bandwidthMeter);
//
//
//        // Create an AdsLoader with the ad tag url.
////        adsLoader = new ImaAdsLoader(this, Uri.parse(getString(R.string.ad_tag_url)));
//        // Toast.makeText(getBaseContext(), "Player launched!", Toast.LENGTH_LONG).show();
//
//        // Toast.makeText(getBaseContext(), "Player launched!", Toast.LENGTH_LONG).show();
//        player.addListener(new Player.EventListener() {
//
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//                Log.d("_____________________ ", String.valueOf(space));
//
//            }
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                switch (playbackState) {
//                    case Player.STATE_READY:
//                        loading.setVisibility(View.GONE);
//                        break;
//                    case Player.STATE_BUFFERING:
//                        loading.setVisibility(View.VISIBLE);
//                        break;
//                }
//                if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
//                        !playWhenReady) {
//
//                    playerView.setKeepScreenOn(false);
//                } else {
//                    // This prevents the screen from getting dim/lock
//                    playerView.setKeepScreenOn(true);
//                }
//            }
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//
//            }
//
//
//            @Override
//            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//                Toast.makeText(getBaseContext(), "Error Occurred While Fetching the Url,Please check the Url ", Toast.LENGTH_LONG).show();
//                player.stop();
//                player.setPlayWhenReady(true);
//                initializePlayer(bandwidthMeter);
//            }
//            @Override
//            public void onPositionDiscontinuity(int reason) {
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//            }
//
//            @Override
//            public void onSeekProcessed() {
//            }
//
//        });
//        player.setPlayWhenReady(true); //run file/link when ready to play.
//    }
//
//
//
//
//
//
//
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        Log.v(LOG_TAG, "onResume");
////        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
////        registerReceiver(receiiveerr,filter);
////
////    }
//    //
////    @Override
////    public void onStop() {
////        super.onStop();
////        Log.v(LOG_TAG, "onStop");
////        if (receiiveerr != null) {
////            unregisterReceiver(receiiveerr);
////            receiiveerr = null;
////        }
////
////    }
////    @Override
////    public void onPause() {
////        super.onPause();
////        if (receiiveerr != null) {
////            unregisterReceiver(receiiveerr);
////            receiiveerr = null;
////        }
////    }
//
////    @Override
////    public void onDestroy() {
////        Log.v(LOG_TAG, "onDestory");
////        super.onDestroy();
////        if (receiiveerr != null) {
////            unregisterReceiver(receiiveerr);
////            receiiveerr = null;
////        }
////
//////       unregisterReceiver(receiverr);
////
////    }
//    public class NetworkChangeReceiiveerr extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(final Context context, final Intent intent) {
//
//            Log.v(LOG_TAG, "Receieved notification about network status");
//            isNetworkAvailablee(context);
//
//        }
//    }
//    private boolean isNetworkAvailablee(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null) {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//
//                        Log.v(LOG_TAG, "" + isConnected);
//                        if(!isConnected){
//                            Log.v(LOG_TAG, "Now you are connected to Internet!");
////                            networkStatus.setText("Now you are connected to Internet!");
//                            Toast.makeText(context, "Now you are connected to Internet!", Toast.LENGTH_LONG).show();
//
//                            isConnected = true;
////                            FragmentTransaction ft = getFragmentManager().beginTransaction();
////                            if (Build.VERSION.SDK_INT >= 26) {
////                                ft.setReorderingAllowed(false);
////                            }
////                            ft.detach(this).attach(this).commit();
////                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                                fragmentManager.beginTransaction().detach(this).commitNow();
////                                fragmentManager.beginTransaction().attach(this).commitNow();
////                            } else {
////                                fragmentManager.beginTransaction().detach(this).attach(this).commit();
////                            }
//                            finish();
//                            startActivity(getIntent());
//
////                            finish();
////                           overridePendingTransition(0, 0);
////                            startActivity(getIntent());
////                            overridePendingTransition(0, 0);
//                            //do your processing here ---
//                            //if you need to post any data to the server or get status
//                            //update from the server
//                        }
//                        return true;
//                    }
//                }
//            }
//        }
//        Log.v(LOG_TAG, "You are not connected to Internet!");
////        networkStatus.setText("You are not connected to Internet!");
//        Toast.makeText(context, "You are not connected to Internet!", Toast.LENGTH_LONG).show();
//        isConnected = false;
//        return false;
//    }
//
//
//
//
//
//
//
//
//
//    private void initializePlayer(DefaultBandwidthMeter bandwidthMeter) {
//        loading = findViewById(R.id.loading);
//        changeQuality = findViewById(R.id.change_quality);
//        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
////        player = new SimpleExoPlayer.Builder(this).build();
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//        selector = (MappingTrackSelector) trackSelector;
//        playerView = findViewById(R.id.player_view);
//
//        playerView.setUseController(true);//set to true or false to see controllers
//        playerView.requestFocus();
//        playerView.setPlayer(player);
////        if (player!=null)
////        { adsLoader.setPlayer(player);}
//
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "StreamTest"), bandwidthMeter);
//        MediaSource videoSource = new HlsMediaSource(URL, dataSourceFactory, 1, null, null);
//
//        // Create the AdsMediaSource using the AdsLoader and the MediaSource.
////        AdsMediaSource adsMediaSource =
////                new AdsMediaSource(videoSource, dataSourceFactory, adsLoader, playerView);
//        player.prepare(videoSource);
////        player.prepare(adsMediaSource);
////        player.setPlayWhenReady(false);
//
//        changeQuality.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                change_Quality();
//            }
//        });
//    }
//
//
//
//    private void change_Quality() {
//
//        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = selector.getCurrentMappedTrackInfo();
//        if (mappedTrackInfo != null) {
//            int rendererIndex = 0;
//            // int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
//            // boolean allowAdaptiveSelections =
//            ///         rendererType == C.TRACK_TYPE_VIDEO
//            //                 || (rendererType == C.TRACK_TYPE_AUDIO
//            //                && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//            //                 == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
//            Pair<AlertDialog, TrackSelectionView> dialogPair =
//                    TrackSelectionView.getDialog(VideoStreamActivity.this, "Available Quality", (DefaultTrackSelector) selector, rendererIndex);
//            // dialogPair.second.setShowDisableOption(true);
//            //dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
//            dialogPair.first.show();
//        }
//    }
////
////    private void hideSystemUi() {
////        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
////                | View.SYSTEM_UI_FLAG_FULLSCREEN
////                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
////                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
////    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        player.release();
////        player.release();
//        Log.v(LOG_TAG, "onStop");
//        if (receiiveerr != null) {
//            unregisterReceiver(receiiveerr);
//            receiiveerr = null;
//        }
//
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
////        if (Util.SDK_INT > 23) {
////            initializePlayer(bandwidthMeter);
////            if (playerView != null) {
////                playerView.onResume();
////            }
//
//
////        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        if (Util.SDK_INT <= 23 || player == null) {
////            initializePlayer(bandwidthMeter);
////            if (playerView != null) {
////                playerView.onResume();
////            }
////        }
//
//        Log.v(LOG_TAG, "onResume");
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(receiiveerr,filter);
//
////        hideSystemUi();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
////        if (Util.SDK_INT <= 23) {
////            if (playerView != null) {
////                playerView.onPause();
////            }
////            player.release();
////        }
//
//        if (receiiveerr != null) {
//            unregisterReceiver(receiiveerr);
//            receiiveerr = null;
//        }
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        player.release();
////        player.release();
////        if (Util.SDK_INT > 23) {
////            if (playerView != null) {
////                playerView.onPause();
////            }
////            player.release();
////        }
////        adsLoader.release();
//
//        Log.v(LOG_TAG, "onDestory");
//        super.onDestroy();
//        if (receiiveerr != null) {
//            unregisterReceiver(receiiveerr);
//            receiiveerr = null;
//        }
//    }
//}
package com.placidvision.jesuscalls;
//README
//this project was done by yashwant kr singh, under Placid-vison
//this application allows users to play hls stream on android phone
//we can select video bitrate (video quality ) according to users internet speed.
// the user need to enter http url alone with stream name
// Example - http://10.18.10.121/mystreamname
// if user doesn't pass the url perimeter ,the appplication plays a hls stream which is pre defined in the app!!

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoStreamActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private View loading;
    private ImageButton changeQuality;
    private TrackGroupArray trackGroups;
    private TrackSelector trackSelector;
    private  MappingTrackSelector selector;
    private  TrackSelection.Factory videoTrackSelectionFactory;
    String space= "\n";
    Uri URL;
    private DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

private ImaSdkFactory mSdkFactory;
    private ImaAdsLoader adsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //will hide the status bar
        setContentView(R.layout.activity_video_stream);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //will rotate the screen
        Bundle b = getIntent().getExtras();
        final String address =  b.getString("url", "");

        URL = Uri.parse(address);
        adsLoader = new ImaAdsLoader(this, Uri.parse(getString(R.string.ad_tag_url)));
        initializePlayer(bandwidthMeter);
        this.player=player;
        // Toast.makeText(getBaseContext(), "Player launched!", Toast.LENGTH_LONG).show();
        // Create an AdsLoader with the ad tag url.

        player.addListener(new Player.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.d("_____________________ ", String.valueOf(space));

            }
            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_READY:
                        loading.setVisibility(View.GONE);
                        break;
                    case Player.STATE_BUFFERING:
                        loading.setVisibility(View.VISIBLE);
                        break;
                }
                if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                        !playWhenReady) {

                    playerView.setKeepScreenOn(false);
                } else {
                    // This prevents the screen from getting dim/lock
                    playerView.setKeepScreenOn(true);
                }
            }
            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }


            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(getBaseContext(), "Error Occurred While Fetching the Url,Please check the Url ", Toast.LENGTH_LONG).show();
                player.stop();
                player.setPlayWhenReady(true);
                initializePlayer(bandwidthMeter);
            }
            @Override
            public void onPositionDiscontinuity(int reason) {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            @Override
            public void onSeekProcessed() {
            }

        });
        player.setPlayWhenReady(true); //run file/link when ready to play.
    }

    private void initializePlayer(DefaultBandwidthMeter bandwidthMeter) {
        loading = findViewById(R.id.loading);
        changeQuality = findViewById(R.id.change_quality);
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();



        selector = (MappingTrackSelector) trackSelector;
        playerView = findViewById(R.id.player_view);
 if(adsLoader!=null){
     Log.v("adsloader","not null");
 }else{
     Log.v("adsloader","null");
 }

        playerView.setUseController(true);//set to true or false to see controllers
        playerView.requestFocus();
        playerView.setPlayer(player);
        adsLoader.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "StreamTest"), bandwidthMeter);
        HlsMediaSource.Factory mediaSourceFactory=new HlsMediaSource.Factory(dataSourceFactory);
//        ProgressiveMediaSource.Factory mediaSourceFactory =
//                new ProgressiveMediaSource.Factory(dataSourceFactory);

        // Create the MediaSource for the content you wish to play.
//        MediaSource mediaSource =
//                mediaSourceFactory.createMediaSource(URL);

      HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(URL);
//       MediaSource videosourse= new HlsMediaSource
        AdsMediaSource adsMediaSource =
              new AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView);
        player.prepare(adsMediaSource);
//        player.setPlayWhenReady(false);
//        player.prepare(videoSource);
        changeQuality.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                change_Quality();
            }
        });
    }



    private void change_Quality() {

        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = selector.getCurrentMappedTrackInfo();
//        if (mappedTrackInfo != null) {
//            int rendererIndex = 0;
//            // int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
//            // boolean allowAdaptiveSelections =
//            ///         rendererType == C.TRACK_TYPE_VIDEO
//            //                 || (rendererType == C.TRACK_TYPE_AUDIO
//            //                && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//            //                 == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
//            Pair<AlertDialog, TrackSelectionView> dialogPair =
//                    TrackSelectionView.getDialog(VideoStreamActivity.this, "Available Quality", (DefaultTrackSelector) selector, rendererIndex);
//            // dialogPair.second.setShowDisableOption(true);
//            //dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
//            dialogPair.first.show();
//        }
    }
//
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
          initializePlayer(bandwidthMeter);
            if (playerView != null) {
                playerView.onResume();
            }


       }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer(bandwidthMeter);
            if (playerView != null) {
                playerView.onResume();
            }
        }
//        hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            player.release();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            player.release();
        }
        adsLoader.release();
    }
//    public void VideoPlayerController(Context context, SimpleExoPlayer videoPlayer,
//                                      ViewGroup adUiContainer) {
//
//        ImaSdkSettings imaSdkSettings = new ImaSdkSettings() {
//            @Override
//            public String getPpid() {
//                return null;
//            }
//
//            @Override
//            public void setPpid(String s) {
//
//            }
//
//            @Override
//            public int getMaxRedirects() {
//                return 0;
//            }
//
//            @Override
//            public void setMaxRedirects(int i) {
//
//            }
//
//            @Override
//            public String getLanguage() {
//                return null;
//            }
//
//            @Override
//            public void setLanguage(String s) {
//
//            }
//
//            @Override
//            public boolean doesRestrictToCustomPlayer() {
//                return false;
//            }
//
//            @Override
//            public void setRestrictToCustomPlayer(boolean b) {
//
//            }
//
//            @Override
//            public String getPlayerType() {
//                return null;
//            }
//
//            @Override
//            public void setPlayerType(String s) {
//
//            }
//
//            @Override
//            public String getPlayerVersion() {
//                return null;
//            }
//
//            @Override
//            public void setPlayerVersion(String s) {
//
//            }
//
//            @Override
//            public boolean getAutoPlayAdBreaks() {
//                return false;
//            }
//
//            @Override
//            public void setAutoPlayAdBreaks(boolean b) {
//
//            }
//
//            @Override
//            public void setDebugMode(boolean b) {
//
//            }
//
//            @Override
//            public boolean isDebugMode() {
//                return false;
//            }
//
//            @Override
//            public String toString() {
//                return null;
//            }
//        };
//        // Tell the SDK you want to control ad break playback.
//        imaSdkSettings.setAutoPlayAdBreaks(false);
//        mSdkFactory = ImaSdkFactory.getInstance();
//        adsLoader = mSdkFactory.createAdsLoader(context, imaSdkSettings);
//
//    }
//
//    @Override
//    public void onAdEvent(AdEvent adEvent) {
//
//        switch (adEvent.getType()) {
//            // Listen for the AD_BREAK_READY event.
//            case AD_BREAK_READY:
//                // Tell the SDK to play ads when you're ready. To skip this ad break,
//                // simply return from this handler without calling mAdsManager.start().
//                mAdsManager.start();
//                break;
//
//        }
//    }

    }
