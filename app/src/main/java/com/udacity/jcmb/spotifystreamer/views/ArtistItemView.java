package com.udacity.jcmb.spotifystreamer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.activities.TopTracksActivity_;
import com.udacity.jcmb.spotifystreamer.model.Artist;
import com.udacity.jcmb.spotifystreamer.utils.Utils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.ExecutionException;

/**
 * @author Julio Mendoza on 6/18/15.
 */
@EViewGroup(R.layout.item_view_artist)
public class ArtistItemView extends CardView {

    @ViewById
    ImageView ivArtist;

    @ViewById
    TextView tvArtistName;

    private int color;

    public ArtistItemView(Context context) {
        super(context);
        init();
    }

    public ArtistItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArtistItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setPreventCornerOverlap(false);
        setUseCompatPadding(true);
        setCardElevation(Utils.convertDpToPixel(8f, getContext()));
        AbsListView.LayoutParams params =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setBackgroundColor(getContext().getResources().getColor(R.color.alpha_black));
    }

    public void bind(final Artist artist)
    {

        String imageUrl = artist.getImageUrl();
        if(imageUrl != null)
        {
            Context context = getContext().getApplicationContext();
            Glide.with(context).load(imageUrl).fitCenter().into(ivArtist);
            loadBitmap(imageUrl, context);
        }

        String name = artist.getName();
        tvArtistName.setText(name);

        final GestureDetector detector = new GestureDetector(getContext(),
                new CustomGestureDetector(artist));

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    @Background
    void loadBitmap(String imageUrl, Context context)
    {
        try {
            Bitmap resource = Glide.with(context)
                    .load(imageUrl).asBitmap().into(120,120).get();
            color = Utils.getAverageColorOfImage(resource);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        private Artist artist;

        public CustomGestureDetector(Artist artist)
        {
            this.artist = artist;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            int x = (int) e.getRawX();

            int y = (int) e.getRawY();

            TopTracksActivity_.intent(getContext()).artistId(artist.getId())
                    .artistName(artist.getName())
                    .imageUrl(artist.getImageUrl())
                    .x(x)
                    .y(y)
                    .color(color)
                    .start();
            return super.onSingleTapUp(e);
        }
    }
}
