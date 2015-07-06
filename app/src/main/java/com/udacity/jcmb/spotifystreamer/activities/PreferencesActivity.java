package com.udacity.jcmb.spotifystreamer.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.adapters.CountriesAdapter;
import com.udacity.jcmb.spotifystreamer.interfaces.OnCountrySelectedListener;
import com.udacity.jcmb.spotifystreamer.preferences.Prefs_;
import com.udacity.jcmb.spotifystreamer.widgets.CustomItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author Julio Mendoza on 6/29/15.
 */
@EActivity(R.layout.activty_preferences)
public class PreferencesActivity extends AppCompatActivity {

    @ViewById
    LinearLayout layoutRoot;

    @ViewById
    EditText etCountry;

    @ViewById
    RecyclerView rvCountries;

    @Pref
    Prefs_ prefs;

    @Bean
    CountriesAdapter adapter;

    @AfterViews
    void init()
    {
        OnCountrySelectedListener onCountrySelectedListener = new OnCountrySelectedListener() {
            @Override
            public void onCountrySelected(String name, String code) {
                prefs.countryCode().put(code);
                String text = name + " " + getResources().getString(R.string.country_selected);
                Snackbar snackbar = Snackbar.make(layoutRoot, text, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefs.countryCode().remove();
                    }
                });
                snackbar.show();
            }
        };
        adapter.setOnCountrySelectedListener(onCountrySelectedListener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvCountries.setLayoutManager(manager);
        rvCountries.setHasFixedSize(false);
        rvCountries.setLayoutManager(manager);
        rvCountries.setItemAnimator(new DefaultItemAnimator());
        rvCountries.addItemDecoration(new CustomItemDecoration(16));
        rvCountries.setAdapter(adapter);
    }

    @TextChange(R.id.etCountry)
    void search(TextView tv)
    {
        String text = tv.getText().toString().trim();

        if(!text.isEmpty())
        {
            adapter.filter(text);
        }
    }
}
