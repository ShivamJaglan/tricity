package com.placidvision.jesuscalls;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class VideoInfo implements Serializable {
    private int id;
    private String name, description, thumbnailURL, previewURL, videoURL;
    private Bitmap bitmap;

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
       VideoInfo videoInfo = (VideoInfo) obj;
        return id == videoInfo.id &&
                name == videoInfo.name && description == videoInfo.description && thumbnailURL == videoInfo.thumbnailURL && previewURL == videoInfo.previewURL && videoURL == videoInfo.videoURL && bitmap.sameAs(videoInfo.bitmap);}

    @Override
    public int hashCode() {
        return this.id;
    }

    public VideoInfo(int id, String name, String description, String thumbnailURL, String previewURL, String videoURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.previewURL = previewURL;
        this.videoURL = videoURL;
    }
    public VideoInfo(String name, String description, String thumbnailURL, String previewURL, String videoURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.previewURL = previewURL;
        this.videoURL = videoURL;
    }
    public VideoInfo(int id, String name, String description, String thumbnailURL, String videoURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.videoURL = videoURL;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
