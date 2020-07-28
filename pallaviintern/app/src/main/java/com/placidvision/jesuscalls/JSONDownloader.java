package com.placidvision.jesuscalls;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

class JSONDownloader extends Thread {
    private String jURL;
    private boolean forObject;
    private OnJSONLoadListener onJSONLoadListener;

    JSONDownloader(String jURL) {
        this.jURL = jURL;
    }

    JSONDownloader(String jURL, boolean forObject) {
        this.jURL = jURL;
        this.forObject = forObject;
    }

    void setOnJSONLoadListener (OnJSONLoadListener onJSONLoadListener) {
        this.onJSONLoadListener = onJSONLoadListener;
    }

    @Override
    public void run() {
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(jURL);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            JSONArray array;
            if (forObject) {
                JSONObject object = new JSONObject(stringBuffer.toString());
                array = new JSONArray();
                array.put(0, (Object) object);

            } else {
                array = new JSONArray(stringBuffer.toString());
            }
            onJSONLoadListener.onJSONLoad(array);
        } catch (JSONException ex) {
            ex.printStackTrace();
            onJSONLoadListener.onJSONException();
        } catch (SocketException ex) {
            ex.printStackTrace();
            onJSONLoadListener.onConnectException();
        } catch (IOException ex) {
            Log.e("App", "IO", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


interface OnJSONLoadListener {
    void onJSONLoad(JSONArray array);
    void onConnectException();
    void onJSONException();
}