package com.udacity.jcmb.spotifystreamer.model;

/**
 * @author Julio Mendoza on 6/29/15.
 */
public class Country {

    String name;

    String code;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
