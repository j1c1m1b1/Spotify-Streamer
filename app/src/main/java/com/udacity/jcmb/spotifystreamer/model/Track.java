package com.udacity.jcmb.spotifystreamer.model;

/**
 * @author Julio Mendoza on 6/22/15.
 */
public class Track {

    private String id;

    private String name;

    private String albumName;

    private String imageUrl;

    public Track(String id, String name, String albumName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.albumName = albumName;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
