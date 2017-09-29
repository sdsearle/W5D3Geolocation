package com.example.admin.w5d3geolocation.view.mainactivity;

import android.location.Location;
import android.util.Log;

import com.example.admin.w5d3geolocation.Address;
import com.example.admin.w5d3geolocation.RetrofitHelper;
import com.example.admin.w5d3geolocation.model.GeocodeResponse;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 9/26/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;
    private List<Address> addresses = new ArrayList<>();


    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getlocation() {

    }

    @Override
    public void callLatLng(double latitude, double longitude, Observer<Response<GeocodeResponse>> latLngObserver) {
        String latLng = latitude + "," + longitude;
        final Observable<Response<GeocodeResponse>> geocodeResponseCall = RetrofitHelper.createLatLngCall(latLng);
        geocodeResponseCall.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(latLngObserver);

        view.updateCurrentLatLng(latLng, latLngObserver);


    }

    @Override
    public void getAddressLocation(String street, String state, String zip) {
        String address = street + ", " + state + " " + zip;

        final Observable<Response<GeocodeResponse>> geocodeResponseAddress = RetrofitHelper.createAddressCall(address);
        Observer<Response<GeocodeResponse>> addressObserver = new Observer<Response<GeocodeResponse>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull Response<GeocodeResponse> geocodeResponse) {
                if (geocodeResponse != null) {
                    if (geocodeResponse.body().getResults().size() > 0) {
                        com.example.admin.w5d3geolocation.model.Location latLng = geocodeResponse.body().getResults().get(0).getGeometry().getLocation();
                        String s = "Lat: " + latLng.getLat() + ", Lng: ";
                        s += latLng.getLng();
                        view.setFoundLatLng(s, latLng);
                    } else
                        Log.d(TAG, "onNext: " + geocodeResponse.raw().request().url().toString());
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        geocodeResponseAddress.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(addressObserver);

    }

    @Override
    public void addAddress(String street, String zip, String state) {
        /*Address tmpAddress = new Address(street,zip,state, latLng);
        //addresses.add(tmpAddress);
        view.updateAdressesView(tmpAddress);*/
    }


}
