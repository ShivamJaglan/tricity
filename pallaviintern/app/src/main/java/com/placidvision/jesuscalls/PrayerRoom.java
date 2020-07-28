package com.placidvision.jesuscalls;

import androidx.annotation.Nullable;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class PrayerRoom {
    public static final String OFFLINE = "Offline";
    public static final String ONLINE = "Online";
    private String url, roomName, roomNumber, thumbnail, status;

    public PrayerRoom(String url, String roomName, String roomNumber, String thumbnail, String status) {
        this.url = url;
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.thumbnail = thumbnail;
        this.status = status;
    }
    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrayerRoom prayerRoom  = (PrayerRoom) obj;
        return url == prayerRoom.url &&
                roomName == prayerRoom.roomName && roomNumber==prayerRoom.roomNumber && thumbnail==prayerRoom.thumbnail && status==prayerRoom.status;
//                    videoInfos.equals(categoryInfo.videoInfos);

    }

    @Override
    public int hashCode() {
        return parseInt(roomNumber);
    }

    public String getUrl() {
        return url;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getStatus() {
        return status;
    }
}
