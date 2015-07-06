package com.udacity.jcmb.spotifystreamer.application;

import android.app.Application;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.model.Artist;
import com.udacity.jcmb.spotifystreamer.model.Country;
import com.udacity.jcmb.spotifystreamer.utils.Utils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Julio Mendoza on 6/18/15.
 */
@EApplication
public class SpotifyStreamer extends Application {

    private static final String CODE = "code";
    private static final String NAME = "name";

    private ArrayList<Artist> artists;

    private ArrayList<Country> countries;

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        countries = new ArrayList<>();
        loadCountries();
    }

    @Background
    void loadCountries()
    {
        String json = Utils.readFilesFromRaw(this, R.raw.country_codes);

        try {
            JSONObject countryJson;
            JSONArray codes = new JSONArray(json);
            String code, name;
            Country country;
            for(int i = 0; i < codes.length(); i ++)
            {
                countryJson = codes.getJSONObject(i);
                code = countryJson.getString(CODE);
                name = countryJson.getString(NAME);

                country = new Country(name, code);
                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
