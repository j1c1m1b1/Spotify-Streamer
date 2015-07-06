package com.udacity.jcmb.spotifystreamer.model;

/**
 * @author Julio Mendoza on 6/18/15.
 */
public class Artist {

    private String id;

    private String name;

    private String imageUrl;

    public Artist(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
