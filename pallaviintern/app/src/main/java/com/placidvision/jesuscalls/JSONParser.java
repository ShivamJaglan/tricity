package com.placidvision.jesuscalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    
    public static CategoryInfo[] getCategories(JSONArray array) {
        CategoryInfo[] categoryInfos = new CategoryInfo[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            try { categoryInfos[i] = getCategoryInfo(array.getJSONObject(i)); }
            catch (JSONException e) { e.printStackTrace(); }
        }
        return categoryInfos;
    }

    public static CategoryInfo getCategoryInfo(JSONObject object) {
        CategoryInfo categoryInfo = null;
        try {
            categoryInfo = new CategoryInfo(
                    object.getInt("categoryId"),
                    object.getString("categoryName"),
                    getVideos(object.getJSONArray("type"), true));
        } catch (JSONException e) { e.printStackTrace(); }
        return categoryInfo;
    }

    public static VideoInfo[] getVideos(JSONArray array, boolean isIdPresent) {
        VideoInfo[] videoInfos = new VideoInfo[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            try { videoInfos[i] = getVideoInfo(array.getJSONObject(i), isIdPresent); }
            catch (JSONException e) { e.printStackTrace(); }
        }
        return videoInfos;
    }

    public static VideoInfo[] getSearchVideos(JSONArray array) {
        VideoInfo[] videoInfos = new VideoInfo[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            try { videoInfos[i] = getVideoInfo(array.getJSONArray(i).getJSONObject(0), true); }
            catch (JSONException e) { e.printStackTrace(); }
        }
        return videoInfos;
    }

    public static VideoInfo getVideoInfo(JSONObject object, boolean isIdPresent) {
        VideoInfo videoInfo = null;
        try {
            if (isIdPresent)
                videoInfo =  new VideoInfo(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getString("description"),
                        object.getString("thumbnail"),
                        object.getString("preview"),
                        object.getString("filePath"));
            else
                videoInfo =  new VideoInfo(
                        object.getString("name"),
                        object.getString("description"),
                        object.getString("thumbnail"),
                        object.getString("preview"),
                        object.getString("filePath"));

        } catch (JSONException e) {
            e.printStackTrace();
            try {
                videoInfo = new VideoInfo(
                        object.getInt("id"),
                        object.getString("name"),
                        object.getString("description"),
                        object.getString("thumbnail"),
                        object.getString("Preview"),
                        object.getString("filePath"));
            } catch (JSONException exe) {
                try {
                    videoInfo = new VideoInfo(
                            object.getInt("id"),
                            object.getString("name"),
                            object.getString("description"),
                            object.getString("thumbnail"),
                            object.getString("filePath"));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return videoInfo;
    }

    public static PrayerRoom[] getPrayerRooms(JSONArray array) {
        int length = array.length();
        PrayerRoom[] rooms = new PrayerRoom[length];
        try {
            for (int i = 0; i < length; ++i) {
                JSONObject object = array.getJSONObject(i).getJSONArray("type").getJSONObject(0);
                rooms[i] = getPrayerRoom(object);
            }
        } catch (JSONException e) { e.printStackTrace();}
        return rooms;
    }

    public static PrayerRoom getPrayerRoom(JSONObject object) {
        PrayerRoom prayerRoom = null;
        try {
            prayerRoom = new PrayerRoom(
                    object.getString("URL"),
                    object.getString("name"),
                    object.getString("room-number"),
                    object.getString("thumbnail"),
                    object.getString("Status"));
        } catch (JSONException e) { e.printStackTrace(); }
        return prayerRoom;
    }

    public static String[] getLanguages(JSONArray array) {
        String[] languages = new String[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            try {
                languages[i] = array.getJSONObject(i).getString("Language");
            } catch (JSONException e) { e.printStackTrace();}
        }
        return languages;
    }

}
