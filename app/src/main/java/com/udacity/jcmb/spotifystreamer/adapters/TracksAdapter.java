package com.udacity.jcmb.spotifystreamer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.interfaces.AnimatedAdapter;
import com.udacity.jcmb.spotifystreamer.model.Track;
import com.udacity.jcmb.spotifystreamer.views.TrackItemView;
import com.udacity.jcmb.spotifystreamer.views.TrackItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * @author Julio Mendoza on 6/22/15.
 */
@EBean
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> implements AnimatedAdapter
{

    @RootContext
    Context context;

    private ArrayList<Track> tracks;

    private int lastPosition = -1;

    @AfterInject
    void init()
    {
        tracks = new ArrayList<>();
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrackItemView trackItemView = TrackItemView_.build(context);
        return new ViewHolder(trackItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.bind(track);
        setAnimation(holder.trackItemView, position);
    }

    @Override
    public int getItemCount() {
        return tracks == null ? 0 : tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TrackItemView trackItemView;
        public ViewHolder(View itemView) {
            super(itemView);
            trackItemView = (TrackItemView)itemView;
        }

        public void bind(Track track)
        {
            trackItemView.bind(track);
        }
    }

    @Override
    public void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
