package com.placidvision.jesuscalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;

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

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private Toolbar bar;
    private String currentFragment;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private PrayerFragment prayerFragment;
    private LiveFragment liveFragment;
    private DonationFragment donationFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String[] languages;
    private String username;
    private static final String LOG_TAG = "CheckNetworkStatus";
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        initDrawerLayout();

        navigationView = drawerLayout.findViewById(R.id.nav_view);
        initNavView();

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        changeFragment(homeFragment);
    }
    public String getMyUsername() {
        return username;
    }

    void initNavView() {
        Spinner spinner = (Spinner) navigationView.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, getLanguageList()));
        spinner.setFocusableInTouchMode(true);
        Button logoutbutton=(Button)findViewById(R.id.logout_btn);
        logoutbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.commit();
                finishAffinity();
                Intent logoutintent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logoutintent);
            Log.v(LOG_TAG,"logout clicked");
                finish();
               Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_LONG).show();
            }
        });
//        spinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b)
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
//                else
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
//            }
//        });
    }

    String[] getLanguageList() {
        JSONDownloader downloader = new JSONDownloader(Links.getLanguagesListJSONURL(username));
        downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
            @Override
            public void onJSONLoad(JSONArray array) {
                languages = JSONParser.getLanguages(array);
            }

            @Override
            public void onConnectException() {

            }

            @Override
            public void onJSONException() {

            }
        });
        downloader.start();
        try { downloader.join(); } catch (InterruptedException e ) { e.printStackTrace();}
        return languages;
    }

    void initDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, bar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    void initializeFragments() {

        searchFragment = SearchFragment.getInstance(this);
        homeFragment = HomeFragment.getInstance(MainActivity.this);
        prayerFragment = PrayerFragment.getInstance(this);
        liveFragment = LiveFragment.getInstance(this);
        donationFragment = DonationFragment.getInstance();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        initializeFragments();
        switch (menuItem.getItemId()) {
//            case R.id.navigation_account:
//                break;
            case R.id.navigation_search:
                bar.setTitle(R.string.search);
                changeFragment(searchFragment);
                return true;
            case R.id.navigation_home:
                bar.setTitle(R.string.home);
                changeFragment(homeFragment);
                return true;
            case R.id.navigation_prayer:
                bar.setTitle(R.string.prayer);
                changeFragment(prayerFragment);
                return true;
            case R.id.navigation_live_tv:
                bar.setTitle(R.string.live_tv);
                changeFragment(liveFragment);
                return true;
            case R.id.navigation_donation:
                bar.setTitle(R.string.donation);
                changeFragment(donationFragment);
                return true;
//            case R.id.logout_btn:
//                Intent logoutintent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(logoutintent);
//                Log.v(LOG_TAG,"logout clicked");
////                SharedPreferences loginSharedPreferences;
////                loginSharedPreferences = getSharedPreferences(
////                       "MY", Context.MODE_PRIVATE);
////                SharedPreferences.Editor editor = loginSharedPreferences.edit();
////                editor.putString("UniqueId", "");
////                editor.commit();
//               finish();
//                Toast.makeText(this,"Logged out",Toast.LENGTH_LONG).show();
//                return true;
        }
        return false;
    }

    void clearStack() {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    boolean validateFragment(String name) {
        return name.equals(currentFragment);

    }

    void changeFragment(Fragment fragment) {
        if (!validateFragment(fragment.toString())) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            clearStack();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        currentFragment = fragment.toString();
    }
}