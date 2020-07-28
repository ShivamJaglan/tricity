package com.placidvision.jesuscalls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText uEditText, pEditText;
    private TextView rTextView;
    private Button loginButton;
    private String username, password, message;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /*
         * Check if we successfully logged in before.
         * If we did, redirect to home page
         */


        initAnim();

        uEditText = findViewById(R.id.username_login);
        pEditText = findViewById(R.id.password_login);

        rTextView = findViewById(R.id.create_account_btn);
        rTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    JSONDownloader downloader = new JSONDownloader(Links.getLoginJSONURL(username, password));
                    downloader.setOnJSONLoadListener(new OnJSONLoadListener() {
                        @Override
                        public void onJSONLoad(JSONArray array) {
                            try {
                                message = array.getJSONObject(0).getString("message");
                                showToast(message);
                            } catch (JSONException e) { e.printStackTrace(); }
                        }

                        @Override
                        public void onConnectException() {
                            showToast(getResources().getString(R.string.check_internet));
                        }

                        @Override
                        public void onJSONException() {

                        }
                    });
                    downloader.start();
                    try { downloader.join(); } catch (InterruptedException e) { e.printStackTrace(); };
                    if (message != null)
                    if (message.equals("successful")) {
                        //make SharedPreferences object
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    boolean validateInput() {
        username = uEditText.getText().toString();
        password = pEditText.getText().toString();

        if (username.isEmpty()) {
            uEditText.setError("Username is required");
            return false;
        }
        if (password.isEmpty()) {
            pEditText.setError("Password is required");
            return false;
        }
        if (password.length() > 0 && password.length() < 8) {
            pEditText.setError("Password must contain minimum of 8 characters");
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    void initAnim() {
        imageView = findViewById(R.id.jc_anim_iv);

        startAnimation(500);
    }

    void startAnimation(int delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatedVectorDrawable avdc = (AnimatedVectorDrawable) getDrawable(R.drawable.jc_anim);
                imageView.setImageDrawable(avdc);
                avdc.start();
            }
        }, delayTime);

    }
}