package com.udacity.jcmb.spotifystreamer.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.jcmb.spotifystreamer.R;
import com.udacity.jcmb.spotifystreamer.interfaces.OnCountrySelectedListener;
import com.udacity.jcmb.spotifystreamer.model.Country;
import com.udacity.jcmb.spotifystreamer.utils.Utils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author Julio Mendoza on 6/29/15.
 */
@EViewGroup(R.layout.item_view_country)
public class CountryItemView extends CardView{

    @ViewById
    TextView tvCountry;

    public CountryItemView(Context context) {
        super(context);
        init();
    }

    public CountryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        params.height = Utils.getListItemPreferredHeight(getContext());
        setBackgroundColor(getContext().getResources().getColor(R.color.alpha_black));
        setLayoutParams(params);
    }

    public void bind(final Country country, final OnCountrySelectedListener onCountrySelectedListener)
    {
        tvCountry.setText(country.getName());

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCountrySelectedListener.onCountrySelected(country.getName(), country.getCode());
            }
        });
    }
}
