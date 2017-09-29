package com.example.admin.w5d3geolocation.view.mainactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.w5d3geolocation.Address;
import com.example.admin.w5d3geolocation.R;
import com.example.admin.w5d3geolocation.mainactivity.DaggerMainActivityComponent;
import com.example.admin.w5d3geolocation.model.GeocodeResponse;
import com.example.admin.w5d3geolocation.view.maps.MapsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity implements MainActivityContract.View, DiagLatLngFrag.DialogListner {


    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private static final int MY_PERMISSIONS_REQUEST_GEOLOCATION = 101;
    private static final String TAG = "TAG";
    @Inject
    MainActivityPresenter presenter;
    @BindView(R.id.tvCurrentLocation)
    TextView tvCurrentLocation;
    @BindView(R.id.tvCurrentAddress)
    TextView tvCurrentAddress;
    @BindView(R.id.etStreet)
    EditText etStreet;
    @BindView(R.id.spnrState)
    Spinner spnrState;
    @BindView(R.id.etZip)
    EditText etZip;
    @BindView(R.id.btnGetLocation)
    Button btnGetLocation;
    @BindView(R.id.btnAllLocation)
    Button btnAllLocation;
    @BindView(R.id.rvAddresses)
    RecyclerView rvAddresses;
    private FusedLocationProviderClient mFusedLocationClient;


    //Move Observer to the presenter
    private Observer<Response<GeocodeResponse>> currentAddressObserver = new Observer<Response<GeocodeResponse>>() {
        @Override
        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

        }

        @Override
        public void onNext(@io.reactivex.annotations.NonNull Response<GeocodeResponse> geocodeResponse) {
            //tvCurrentAddress.setText(geocodeResponse.getResults().get(0).getFormattedAddress());
            if(geocodeResponse.body().getResults().size() > 0) {
                String address = geocodeResponse.body().getResults().get(0).getFormattedAddress();
                tvCurrentAddress.setText(address);
            }
            else
                Log.d(TAG, "onNext: " + geocodeResponse.raw().request().url().toString());
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private String street;
    private String zip;
    private String state;
    private AddressesRecyclerViewAdapter arva;
    private DiagLatLngFrag diagLatLngFrag;
    private com.example.admin.w5d3geolocation.model.Location latLng;
    private List<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainActivityComponent.create().inject(this);

        presenter.attachView(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_GEOLOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            geLocation();
        }

        RecyclerView rv = findViewById(R.id.rvAddresses);
        arva = new AddressesRecyclerViewAdapter(new ArrayList<Address>(), this);
        rv.setAdapter(arva);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(itemAnimator);

    }

    private void geLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "onSuccess: " + location.toString());
                        presenter.callLatLng(location.getLatitude(), location.getLongitude(), currentAddressObserver);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                    }
                });

        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100,this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GEOLOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    geLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "Need the Location", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void updateCurrentLatLng(String latLng, Observer<Response<GeocodeResponse>> latLngObserver) {
        tvCurrentLocation.setText(latLng);
    }

    @Override
    public void setFoundLatLng(String foundLatLng, com.example.admin.w5d3geolocation.model.Location lng) {

        latLng = lng;

        diagLatLngFrag = DiagLatLngFrag.newInstance(foundLatLng);

        FragmentManager fm = getSupportFragmentManager();

        diagLatLngFrag.show(fm,"latLngTag");
    }

    @Override
    public void updateAdressesView(Address address) {


    }

    @OnClick({R.id.btnGetLocation, R.id.btnAllLocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGetLocation:
                street = etStreet.getText().toString();
                zip = etZip.getText().toString();
                state = spnrState.getSelectedItem().toString();
                Log.d(TAG, "onViewClicked: " + state);
                presenter.getAddressLocation(street, state, zip);
                break;
            case R.id.btnAllLocation:
                if(arva.getItemCount() > 0) {
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) arva.getItems());
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public void onAddListner() {
        Log.d(TAG, "onAddListner: ");
        arva.addItem(new Address(street,zip,state,latLng));
        addresses.add(new Address(street,zip,state,latLng));
    }

    @Override
    public void onShowMap() {
        Address tmp = new Address(street,zip,state,latLng);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("address", tmp);
        startActivity(intent);
    }
}
