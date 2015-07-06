package com.udacity.jcmb.spotifystreamer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.jcmb.spotifystreamer.application.SpotifyStreamer;
import com.udacity.jcmb.spotifystreamer.interfaces.OnCountrySelectedListener;
import com.udacity.jcmb.spotifystreamer.model.Country;
import com.udacity.jcmb.spotifystreamer.views.CountryItemView;
import com.udacity.jcmb.spotifystreamer.views.CountryItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * @author Julio Mendoza on 6/29/15.
 */
@EBean
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>{

    @App
    SpotifyStreamer spotifyStreamer;

    @RootContext
    Context context;

    private ArrayList<Country> allCountries;

    private ArrayList<Country> filter;

    private OnCountrySelectedListener onCountrySelectedListener;

    @AfterInject
    void init()
    {
        allCountries = spotifyStreamer.getCountries();
        filter = new ArrayList<>();
        filter.addAll(allCountries);
    }

    public void filter(String query)
    {
        filter.clear();
        for(Country country: allCountries)
        {
            if(country.getName().contains(query))
            {
                filter.add(country);
            }
        }
        if(filter.isEmpty())
        {
            filter.addAll(allCountries);
        }
        notifyDataSetChanged();
    }

    public void setOnCountrySelectedListener(OnCountrySelectedListener onCountrySelectedListener) {
        this.onCountrySelectedListener = onCountrySelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CountryItemView view = CountryItemView_.build(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = filter.get(position);
        holder.bind(country, onCountrySelectedListener);
    }

    @Override
    public int getItemCount() {
        return filter == null ? 0: filter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CountryItemView view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = (CountryItemView) itemView;
        }

        public void bind(Country country, OnCountrySelectedListener onCountrySelectedListener)
        {
            view.bind(country, onCountrySelectedListener);
        }
    }
}
