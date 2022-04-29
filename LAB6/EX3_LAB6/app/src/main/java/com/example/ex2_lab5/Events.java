package com.example.ex2_lab5;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {
    private String title;
    private String room;
    private String time;
    private Boolean checked;
    private int id;

    public Events(String title, String room, String time) {
        this.title = title;
        this.room = room;
        this.time = time;
        this.checked = false;
    }

    public Events(int id, String title, String room, String time) {
        this.id = id;
        this.title = title;
        this.room = room;
        this.time = time;
        this.checked = false;
    }

    public Events(int id, String title, String room, String time, Boolean checked) {
        this.id = id;
        this.title = title;
        this.room = room;
        this.time = time;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<Events> getCREATOR() {
        return CREATOR;
    }

    protected Events(Parcel in) {
        title = in.readString();
        room = in.readString();
        time = in.readString();
        byte tmpChecked = in.readByte();
        checked = tmpChecked == 0 ? null : tmpChecked == 1;
        id = in.readInt();
    }

    public static final Creator<Events> CREATOR = new Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(room);
        parcel.writeString(time);
        parcel.writeByte((byte) (checked == null ? 0 : checked ? 1 : 2));
        parcel.writeInt(id);
    }
}
