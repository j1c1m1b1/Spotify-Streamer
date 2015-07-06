package com.udacity.jcmb.spotifystreamer.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.adapters.ArtistsAdapter;
import com.udacity.jcmb.spotifystreamer.application.SpotifyStreamer;
import com.udacity.jcmb.spotifystreamer.connection.Connection;
import com.udacity.jcmb.spotifystreamer.connection.ContentResolver;
import com.udacity.jcmb.spotifystreamer.interfaces.ConnectionEventsListener;
import com.udacity.jcmb.spotifystreamer.model.Artist;
import com.udacity.jcmb.spotifystreamer.utils.AnimationUtils;
import com.udacity.jcmb.spotifystreamer.widgets.CustomItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.ArrayList;

@EActivity(R.layout.activity_search_activty)
public class SearchActivity extends AppCompatActivity {

    @App
    SpotifyStreamer spotifyStreamer;

    @OptionsMenuItem
    MenuItem search;

    @OptionsMenuItem
    MenuItem settings;

    @ViewById
    RecyclerView rvArtists;

    @ViewById
    LinearLayout layoutLoading;

    @ViewById
    FrameLayout layoutRoot;

    @ViewById
    ProgressBar pbLoading;

    @ViewById
    ImageView ivError;

    @ViewById
    TextView tvLoading;

    @ViewById
    TextView tvIntro;

    @ViewById
    LinearLayout layoutIntro;

    @Bean
    ArtistsAdapter adapter;

    boolean first = true;

    @AfterViews
    void init()
    {
        handleIntent(getIntent());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(manager);
        rvArtists.setHasFixedSize(false);
        rvArtists.setLayoutManager(manager);
        rvArtists.setItemAnimator(new DefaultItemAnimator());
        rvArtists.addItemDecoration(new CustomItemDecoration(16));
        rvArtists.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && first)
        {
            AnimationUtils.appear(layoutIntro, this);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AnimationUtils.bounce(tvIntro, SearchActivity.this);
                }
            }, 5000);
            first = false;
        }
    }

    /**
     * @see <a href="https://developer.android.com/training/search/setup.html">
     *     Setting Up the Search Interface</a>
     * @param menu The Menu
     * @return <b>true</b> to allow the OS to handle the menu creation.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_activty, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @OptionsItem
    boolean search()
    {
        return true;
    }

    @OptionsItem
    boolean settings()
    {
        PreferencesActivity_.intent(this).start();
        return true;
    }



    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            first = false;
            layoutIntro.setVisibility(View.GONE);
            rvArtists.setVisibility(View.VISIBLE);
            layoutLoading.setVisibility(View.VISIBLE);
            ivError.setVisibility(View.GONE);
            tvLoading.setText(R.string.loading_content);
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle(query);
            //use the query to search your data somehow
            ConnectionEventsListener connectionEventsListener = new ConnectionEventsListener() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    updateAdapter(jsonObject);
                }

                @Override
                public void onFail() {
                    reportError();
                }
            };
            Connection.searchItem(query, connectionEventsListener);
        }
    }

    @UiThread
    void reportError()
    {
        AnimationUtils.shrink(pbLoading);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.grow(ivError);
                tvLoading.setText(R.string.connection_failed);
            }
        }, 500);
    }

    @UiThread
    void updateAdapter(JSONObject jsonObject)
    {
        layoutLoading.setVisibility(View.GONE);
        ArrayList<Artist> artists = ContentResolver.parseArtists(jsonObject);
        spotifyStreamer.setArtists(artists);
        adapter.setArtists(artists);
        adapter.notifyDataSetChanged();
        if(artists.isEmpty())
        {
            Snackbar.make(layoutRoot, R.string.no_artists_found, Snackbar.LENGTH_LONG).show();
        }
    }

}
