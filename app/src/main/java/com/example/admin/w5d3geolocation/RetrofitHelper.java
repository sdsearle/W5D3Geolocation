package com.example.admin.w5d3geolocation;

import com.example.admin.w5d3geolocation.model.GeocodeResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 9/27/2017.
 */

public class RetrofitHelper {
public static final String BASE_URL = "https://maps.googleapis.com/";

    public static Retrofit create(){
        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Observable<Response<GeocodeResponse>> createAddressCall(String address) {
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService.getGeocodeAddressResponse(address, "AIzaSyDhDPnYEyKvooGoUPT1vhgq5-hMzY-AbWQ");
    }

    public static Observable<Response<GeocodeResponse>> createLatLngCall(String latLng) {
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService.getGeocodeLatLngResponse(latLng, "AIzaSyDhDPnYEyKvooGoUPT1vhgq5-hMzY-AbWQ");
    }

    interface ApiService{

        @GET("maps/api/geocode/json")
        Observable<Response<GeocodeResponse>>  getGeocodeAddressResponse(
                @Query("address") String address, @Query("key") String key);

        @GET("maps/api/geocode/json")
        Observable<Response<GeocodeResponse>>getGeocodeLatLngResponse(
                @Query("latlng") String address, @Query("key") String key);

    }
}
