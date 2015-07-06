package com.udacity.jcmb.spotifystreamer.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Julio Mendoza on 6/29/15.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface Prefs {

    @DefaultString("US")
    String countryCode();
}
