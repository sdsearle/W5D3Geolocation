package com.example.admin.w5d3geolocation.view.maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.admin.w5d3geolocation.Address;
import com.example.admin.w5d3geolocation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Address address = (Address) getIntent().getParcelableExtra("address");
        List<Address> addresses = getIntent().getParcelableArrayListExtra("list");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if(address != null) {
            LatLng latLng = new LatLng(address.getLat(), address.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getStreet()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }else{
            for (Address a:
                    addresses) {
                LatLng latLng = new LatLng(a.getLat(), a.getLng());
                mMap.addMarker(new MarkerOptions().position(latLng).title(a.getStreet()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }
}
