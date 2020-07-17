package com.example.timefighter.login.trial;



import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Upload{

    public String name;
    public String url;
    public Long pricenew;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Long getPricenew() {
        return pricenew;
    }

    public Upload(String name, String url, Long pricenew) {
        this.name = name;
        this.url= url;
        this.pricenew=pricenew;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
