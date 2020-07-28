package com.placidvision.jesuscalls;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class CategoryInfo {
    private int id;
    private String name;
    private VideoInfo[] videoInfos;

    @Override
    public boolean equals(@Nullable Object obj) {

            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CategoryInfo categoryInfo = (CategoryInfo) obj;
            return id == categoryInfo.id &&
                    name == categoryInfo.name && Arrays.equals(videoInfos,categoryInfo.videoInfos);
//                    videoInfos.equals(categoryInfo.videoInfos);

        }

    @Override
    public int hashCode() {
        return this.id;
    }

    public CategoryInfo(int id, String name, VideoInfo[] videoInfos) {
        this.id = id;
        this.name = name;
        this.videoInfos = videoInfos;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VideoInfo[] getVideoInfos() {
        return videoInfos;
    }
}
