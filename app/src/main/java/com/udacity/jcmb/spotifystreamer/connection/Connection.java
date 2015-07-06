package com.udacity.jcmb.spotifystreamer.connection;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.udacity.jcmb.spotifystreamer.interfaces.ConnectionEventsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Julio Mendoza on 6/18/15.
 */
public class Connection {

    private static final String SEARCH_ITEM = "https://api.spotify.com/v1/search?q=";
    private static final String TOP_TRACKS = "https://api.spotify.com/v1/artists/";
    private static final String UTF8 = "utf-8";


    private static OkHttpClient client = new OkHttpClient();

    public static void searchItem(String item,
                                  final ConnectionEventsListener connectionEventsListener)
    {
        try
        {
            String encodedItem = URLEncoder.encode(item, UTF8);
            String url = String.format("%s%s&type=artist", SEARCH_ITEM, encodedItem);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    connectionEventsListener.onFail();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseString = response.body().string();
                    Log.d("Response", "" + responseString);
                    JSONObject json;
                    try {
                        json = new JSONObject(responseString);
                        connectionEventsListener.onSuccess(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        connectionEventsListener.onFail();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getArtistTopTracks(String artistId, String countryCode,
                                          final ConnectionEventsListener connectionEventsListener)
    {
        String url = String.format("%s%s/top-tracks?country=%s", TOP_TRACKS, artistId, countryCode);
        Log.d("URL", "" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                connectionEventsListener.onFail();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseString = response.body().string();
                JSONObject json;
                try {
                    json = new JSONObject(responseString);
                    connectionEventsListener.onSuccess(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                    connectionEventsListener.onFail();
                }
            }
        });
    }
}
