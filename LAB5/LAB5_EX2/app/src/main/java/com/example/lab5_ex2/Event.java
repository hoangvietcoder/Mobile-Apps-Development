package com.example.lab5_ex2;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String name;
    private String place;
    private String date;
    private String time;
    private boolean checked;

    public Event(String name, String place, String date, String time) {
        this.name = name;
        this.place = place;
        this.date = date;
        this.time = time;
    }

    protected Event(Parcel in) {
        name = in.readString();
        place = in.readString();
        date = in.readString();
        time = in.readString();
        checked = in.readByte() != 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(place);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeByte((byte) (checked ? 1 : 0));
    }
}
