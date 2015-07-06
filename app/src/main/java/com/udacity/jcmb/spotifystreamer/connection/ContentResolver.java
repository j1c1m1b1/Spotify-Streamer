package com.udacity.jcmb.spotifystreamer.connection;

import com.udacity.jcmb.spotifystreamer.model.Artist;
import com.udacity.jcmb.spotifystreamer.model.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Julio Mendoza on 6/18/15.
 */
public class ContentResolver {

    private static final String ITEMS = "items";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IMAGES = "images";
    private static final String URL = "url";
    private static final String ARTISTS = "artists";
    private static final String TRACKS = "tracks";
    private static final String ALBUM = "album";

    public static ArrayList<Artist> parseArtists(JSONObject jsonObject)
    {
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            JSONObject artistsObject = jsonObject.getJSONObject(ARTISTS);
            JSONArray items = artistsObject.getJSONArray(ITEMS);
            JSONObject item, image;
            JSONArray images;
            String id, name, imageUrl = null;
            Artist artist;
            for(int i = 0; i < items.length(); i ++)
            {
                item = items.getJSONObject(i);
                id = item.getString(ID);
                name = item.getString(NAME);
                images = item.getJSONArray(IMAGES);
                if(images.length() > 0)
                {
                    image = images.getJSONObject(1);
                    imageUrl = image.getString(URL);
                }
                artist = new Artist(id,name,imageUrl);
                artists.add(artist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artists;
    }

    /*
    {
"disc_number":3,
"duration_ms":260973,
"explicit":false,
"external_ids":{
"isrc":"USRC16901355"
},
"external_urls":{
"spotify":"https://open.spotify.com/track/6fgjU6IfBOXHI3OKtndEeE"
},
"href":"https://api.spotify.com/v1/tracks/6fgjU6IfBOXHI3OKtndEeE",
"id":"6fgjU6IfBOXHI3OKtndEeE",
"name":"Suspicious Minds",
"popularity":70,
"preview_url":"https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1",
"track_number":19,
"type":"track",
"uri":"spotify:track:6fgjU6IfBOXHI3OKtndEeE"
}
     */

    public static ArrayList<Track> parseTracks(JSONObject response)
    {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            JSONArray tracksArray = response.getJSONArray(TRACKS);
            JSONObject trackObject, albumObject, imageObject;
            JSONArray images;
            String name, imageUrl = "", id, albumName;
            Track track;
            for(int i = 0; i < tracksArray.length(); i ++)
            {
                trackObject = tracksArray.getJSONObject(i);
                id = trackObject.getString(ID);
                name = trackObject.getString(NAME);
                albumObject = trackObject.getJSONObject(ALBUM);
                images = albumObject.getJSONArray(IMAGES);
                albumName = albumObject.getString(NAME);
                if(images.length() > 0)
                {
                    imageObject = images.getJSONObject(0);
                    imageUrl = imageObject.getString(URL);
                }
                track = new Track(id, name, albumName, imageUrl);
                tracks.add(track);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tracks;
    }

}
