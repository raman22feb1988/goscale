package com.example.goscale;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bookmark implements Parcelable {
    ArrayList<String> imdbId;
    ArrayList<String> poster;
    ArrayList<String> title;
    ArrayList<String> year;

    public ArrayList<String> getImdbId() {
        return imdbId;
    }

    public void setImdbId(ArrayList<String> imdbId) {
        this.imdbId = imdbId;
    }

    public ArrayList<String> getPoster() {
        return poster;
    }

    public void setPoster(ArrayList<String> poster) {
        this.poster = poster;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public ArrayList<String> getYear() {
        return year;
    }

    public void setYear(ArrayList<String> year) {
        this.year = year;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    ArrayList<String> type;

    protected Bookmark(Parcel in) {
        ArrayList<String> imdbId = new ArrayList<>();
        in.readStringList(imdbId);
        ArrayList<String> poster = new ArrayList<>();
        in.readStringList(poster);
        ArrayList<String> title = new ArrayList<>();
        in.readStringList(title);
        ArrayList<String> year = new ArrayList<>();
        in.readStringList(year);
        ArrayList<String> type = new ArrayList<>();
        in.readStringList(type);
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    public Bookmark() {
    }

    public static Creator<Bookmark> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(imdbId);
        dest.writeStringList(poster);
        dest.writeStringList(title);
        dest.writeStringList(year);
        dest.writeStringList(type);
    }
}