package com.example.timefighter.trial1;

public class item {
    private String Title;
    private String Description;
    private String Category;
    private int Thumbnail;

    public item(String title, String description, String category, int thumbnail) {
        Title = title;
        Description = description;
        Category = category;
        Thumbnail = thumbnail;
    }

    public item()
    {

    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }

    public int getThumbnail() {
        return Thumbnail;
    }
}
