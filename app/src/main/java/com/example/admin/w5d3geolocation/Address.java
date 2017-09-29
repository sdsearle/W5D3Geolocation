package com.example.admin.w5d3geolocation;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.w5d3geolocation.model.Location;

import java.io.Serializable;

/**
 * Created by admin on 9/28/2017.
 */

public class Address implements Parcelable {
    String street, zip, state;
    Double lat, lng;

    public Address(String street, String zip, String state, Location latLng) {
        this.street = street;
        this.zip = zip;
        this.state = state;
        lat = latLng.getLat();
        lng = latLng.getLng();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.street);
        dest.writeString(this.zip);
        dest.writeString(this.state);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
    }

    protected Address(Parcel in) {
        this.street = in.readString();
        this.zip = in.readString();
        this.state = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
