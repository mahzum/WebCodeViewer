package com.a000webhostapp.fakhry.webcodeviewer;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.content.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by Bro on 22/10/2017.
 */

class AsyncTaskLoaderWeb extends android.support.v4.content.AsyncTaskLoader<String> {

    String url;
    String result = null;
    boolean cancel = false;

    public AsyncTaskLoaderWeb(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading(){
        if (result != null || cancel){
            deliverResult(result);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        String reString = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL link = new URL(url);
            connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(12000);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                if (is == null){
                    return "Error";
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line+"\n");
                }
                reString = sb.toString();
            }
            else {
                return "ERROR RESPONSE CODE" + connection.getResponseCode();
            }

        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
        finally {
            try {
                if(is != null){
                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return reString;
    }
    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        cancel = true;
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        result = data;
    }
}
