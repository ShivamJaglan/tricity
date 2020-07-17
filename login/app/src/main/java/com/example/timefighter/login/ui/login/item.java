package com.example.timefighter.login.ui.login;

public class item {
    private String Title;
    private String Image_url;
    private long Price;

    public long getPrice() {
        return Price;
    }

    public item(String title, String image_url, Long price) {
        Title = title;
        Price=price;
        Image_url = image_url;
    }

    public item()
    {

    }

    public String getTitle() {
        return Title;
    }



    public String getImage_url() {
        return Image_url;
    }


}
