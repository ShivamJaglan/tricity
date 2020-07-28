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

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText fName, lName, uName, email, cEmail, pwd, cPwd;
    private String firstName, lastName, username, emailAddress, confirmEmailAddress, password, confirmPassword, message;
    private Button signUp;
    private TextView login;
    public static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initAnim();

        fName = findViewById(R.id.f_name);
        lName = findViewById(R.id.l_name);
        uName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        cEmail = findViewById(R.id.confirm_email);
        pwd = findViewById(R.id.password);
        cPwd = findViewById(R.id.confirm_password);

        signUp = findViewById(R.id.sign_up);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    JSONDownloader downloader = new JSONDownloader(Links.getRegisterJSONURL(firstName, lastName, username, emailAddress, confirmEmailAddress, password, confirmPassword));
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
                        public void onJSONException() { }
                    });
                    downloader.start();
                    try { downloader.join(); } catch (InterruptedException e) { e.printStackTrace(); }
                    if (message != null)
                        if (message.equals("successful")) {
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("logged", "logged");
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        firstName = fName.getText().toString();
        lastName = lName.getText().toString();
        username = uName.getText().toString();
        emailAddress = email.getText().toString();
        confirmEmailAddress = cEmail.getText().toString();
        password = pwd.getText().toString();
        confirmPassword = cPwd.getText().toString();

        if (firstName.isEmpty()) {
            fName.setError("First Name is Required");
            return false;
        }
        if (lastName.isEmpty()) {
            lName.setError("Last Name is Required");
            return false;
        }
        if (username.isEmpty()) {
            uName.setError("username is Required");
            return false;
        }
        if (emailAddress.isEmpty()) {
            email.setError("Email is Required");
            return false;
        }
        if (confirmEmailAddress.isEmpty()) {
            cEmail.setError("Confirm Email is Required");
            return false;
        }
        if (password.isEmpty()) {
            pwd.setError("password is Required");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            cPwd.setError("Confirm Password is Required");
            return false;
        }
        if (!emailAddress.equals(confirmEmailAddress)) {
            cEmail.setError("Should be same as the previous entry");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            cPwd.setError("Should be same as the previous entry");
            return false;
        }
        return true;
    }

    void initAnim() {
        imageView = findViewById(R.id.jc_anim_iv);

        startAnimation(0);
    }

    void startAnimation(int delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatedVectorDrawable avdc = (AnimatedVectorDrawable) getDrawable(R.drawable.jc_anim);
                imageView.setImageDrawable(avdc);
//                avdc.start();
            }
        }, delayTime);

    }
}