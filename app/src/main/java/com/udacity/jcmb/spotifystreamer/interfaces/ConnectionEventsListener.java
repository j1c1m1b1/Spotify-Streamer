package com.udacity.jcmb.spotifystreamer.interfaces;

import org.json.JSONObject;

/**
 * @author Julio Mendoza on 6/18/15.
 */
public interface ConnectionEventsListener {

    void onSuccess(JSONObject response);

    void onFail();
}
