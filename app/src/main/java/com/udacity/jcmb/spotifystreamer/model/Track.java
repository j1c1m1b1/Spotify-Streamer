package com.udacity.jcmb.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Julio Mendoza on 6/22/15.
 */
public class Track implements Parcelable{

    public static final Parcelable.Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel parcel) {
            return new Track(parcel);
        }

        @Override
        public Track[] newArray(int i) {
            return new Track[i];
        }
    };
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

    public Track(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.albumName = parcel.readString();
        this.imageUrl = parcel.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(albumName);
        parcel.writeString(imageUrl);
    }
}
