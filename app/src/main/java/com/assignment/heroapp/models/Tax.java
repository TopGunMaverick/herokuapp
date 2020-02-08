package com.assignment.heroapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax implements Parcelable {
    public Tax(String name, Float value) {
        this.name = name;
        this.value = value;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private float value;

    protected Tax(Parcel in) {
        name = in.readString();
        value = in.readFloat();
    }

    public static final Creator<Tax> CREATOR = new Creator<Tax>() {
        @Override
        public Tax createFromParcel(Parcel in) {
            return new Tax(in);
        }

        @Override
        public Tax[] newArray(int size) {
            return new Tax[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(value);
    }
}
