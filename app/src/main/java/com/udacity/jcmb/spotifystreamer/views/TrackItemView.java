package com.udacity.jcmb.spotifystreamer.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.model.Track;
import com.udacity.jcmb.spotifystreamer.utils.Utils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author Julio Mendoza on 6/22/15.
 */
@EViewGroup(R.layout.item_view_track)
public class TrackItemView extends CardView {

    @ViewById
    ImageView ivTrack;

    @ViewById
    TextView tvTrackName;

    @ViewById
    TextView tvAlbumName;

    public TrackItemView(Context context) {
        super(context);
        init();
    }

    public TrackItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrackItemView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void bind(Track track)
    {
        tvTrackName.setText(track.getName());
        tvAlbumName.setText(track.getAlbumName());
        Glide.with(getContext().getApplicationContext()).load(track.getImageUrl())
                .fitCenter().centerCrop().into(ivTrack);
    }
}
