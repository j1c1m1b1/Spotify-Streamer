package com.udacity.jcmb.spotifystreamer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.application.SpotifyStreamer;
import com.udacity.jcmb.spotifystreamer.interfaces.AnimatedAdapter;
import com.udacity.jcmb.spotifystreamer.model.Artist;
import com.udacity.jcmb.spotifystreamer.views.ArtistItemView;
import com.udacity.jcmb.spotifystreamer.views.ArtistItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * @author Julio Mendoza on 6/18/15.
 */
@EBean
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder>
        implements AnimatedAdapter
{
    @RootContext
    Context context;

    private int lastPosition = -1;

    @App
    SpotifyStreamer spotifyStreamer;

    private ArrayList<Artist> artists;

    public void setArtists(ArrayList<Artist> artists)
    {
        this.artists = artists;
    }

    @AfterInject
    void init()
    {
        artists = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ArtistItemView view = ArtistItemView_.build(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Artist artist = artists.get(i);
        viewHolder.bind(artist);
        setAnimation(viewHolder.view, i);
    }

    @Override
    public int getItemCount() {
        return artists == null ? 0 : artists.size();
    }

    @Override
    public void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ArtistItemView view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = (ArtistItemView)itemView;
        }

        public void bind(Artist artist)
        {
            view.bind(artist);
        }
    }


}
