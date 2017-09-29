package com.example.admin.w5d3geolocation.view.mainactivity;

import com.example.admin.w5d3geolocation.Address;
import com.example.admin.w5d3geolocation.BasePresenter;
import com.example.admin.w5d3geolocation.BaseView;
import com.example.admin.w5d3geolocation.model.GeocodeResponse;
import com.example.admin.w5d3geolocation.model.Location;

import java.util.List;

import io.reactivex.Observer;
import retrofit2.Response;

/**
 * Created by admin on 9/26/2017.
 */

public interface MainActivityContract {
    interface  View extends BaseView {

        void updateCurrentLatLng(String latLng, Observer<Response<GeocodeResponse>> latLngObserver);

        void setFoundLatLng(String latLng, Location lng);

        void updateAdressesView(Address addresses);
    }

    interface Presenter extends BasePresenter<View> {

        void getlocation();

        void callLatLng(double latitude, double longitude, Observer<Response<GeocodeResponse>> latLngObserver);

        void getAddressLocation(String street, String state, String zip);

        void addAddress(String street, String zip, String state);
    }

}
