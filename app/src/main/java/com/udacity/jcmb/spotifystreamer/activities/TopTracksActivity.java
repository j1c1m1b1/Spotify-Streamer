package com.udacity.jcmb.spotifystreamer.activities;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.adapters.TracksAdapter;
import com.udacity.jcmb.spotifystreamer.connection.Connection;
import com.udacity.jcmb.spotifystreamer.connection.ContentResolver;
import com.udacity.jcmb.spotifystreamer.interfaces.ConnectionEventsListener;
import com.udacity.jcmb.spotifystreamer.model.Track;
import com.udacity.jcmb.spotifystreamer.preferences.Prefs_;
import com.udacity.jcmb.spotifystreamer.utils.BlurUtils;
import com.udacity.jcmb.spotifystreamer.utils.Utils;
import com.udacity.jcmb.spotifystreamer.widgets.CustomItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.codetail.animation.SupportAnimator;

/**
 * @author Julio Mendoza on 6/22/15.
 */
@EActivity(R.layout.activity_top_tracks)
public class TopTracksActivity extends AppCompatActivity {

    @ViewById
    CoordinatorLayout coordinator;

    @ViewById
    ImageView ivArtistBackground;

    @ViewById
    CircleImageView ivArtist;

    @ViewById
    CollapsingToolbarLayout collapsingToolbarLayout;

    @ViewById
    Toolbar toolbar;

    @ViewById
    RecyclerView rvTracks;

    @Extra
    String imageUrl;

    @Extra
    String artistName;

    @Extra
    String artistId;

    @Extra
    int x;

    @Extra
    int y;

    @Extra
    int color;

    @Pref
    Prefs_ prefs;

    @Bean
    TracksAdapter adapter;

    @AfterViews
    void init()
    {
        setSupportActionBar(toolbar);
        toolbar.setTitle(artistName);
        collapsingToolbarLayout.setContentScrimColor(color);
        Glide.with(this).load(imageUrl).centerCrop().into(ivArtist);
        ivArtistBackground.setAlpha(0.4f);
        blurImage();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        collapsingToolbarLayout.setTitle(toolbar.getTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTracks.setLayoutManager(layoutManager);
        rvTracks.setHasFixedSize(false);
        rvTracks.setItemAnimator(new DefaultItemAnimator());
        rvTracks.addItemDecoration(new CustomItemDecoration(16));
        rvTracks.setAdapter(adapter);

        createCircularReveal();
    }

    @Background
    void blurImage()
    {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(this).load(imageUrl).asBitmap().into(640, 480).get();
            Bitmap blurred = BlurUtils.fastblur(bitmap, 5);
            applyBlurred(blurred);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @UiThread
    void applyBlurred(Bitmap bitmap)
    {
        ivArtistBackground.setImageBitmap(bitmap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTracks();
    }

    void createCircularReveal()
    {
        coordinator.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom)
            {

                if(x == 0)
                {
                    Point size = Utils.getScreenDimensions(TopTracksActivity.this);
                    x = size.x / 2;
                    y = size.y / 2;
                }


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    Animator anim = ViewAnimationUtils.createCircularReveal(coordinator, x, y,
                            0, coordinator.getWidth());
                    anim.setDuration(500);
                    coordinator.setVisibility(View.VISIBLE);
                    anim.start();
                }
                else
                {
                    SupportAnimator anim = io.codetail.animation.ViewAnimationUtils.
                            createCircularReveal(coordinator, x, y, 0, coordinator.getWidth());
                    anim.setDuration(500);
                    coordinator.setVisibility(View.VISIBLE);
                    anim.start();
                }

                coordinator.removeOnLayoutChangeListener(this);
            }
        });
    }

    private void getTracks()
    {
        ConnectionEventsListener connectionEventsListener = new ConnectionEventsListener() {
            @Override
            public void onSuccess(JSONObject response) {
                parseResponse(response);
            }

            @Override
            public void onFail() {
                Snackbar.make(coordinator, R.string.server_error, Snackbar.LENGTH_LONG).show();
            }
        };

        String countryCode = prefs.countryCode().get();
        Connection.getArtistTopTracks(artistId, countryCode, connectionEventsListener);
    }

    @UiThread
    void parseResponse(JSONObject response)
    {
        ArrayList<Track> tracks = ContentResolver.parseTracks(response);
        adapter.setTracks(tracks);
        adapter.notifyDataSetChanged();
    }
}
