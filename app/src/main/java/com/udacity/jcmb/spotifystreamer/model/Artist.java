package com.udacity.jcmb.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Julio Mendoza on 6/18/15.
 */
public class Artist implements Parcelable {

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int i) {
            return new Artist[i];
        }

    };
    private String id;
    private String name;
    private String imageUrl;

    public Artist(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Artist(Parcel in)
    {
        this.id = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
    }
}
