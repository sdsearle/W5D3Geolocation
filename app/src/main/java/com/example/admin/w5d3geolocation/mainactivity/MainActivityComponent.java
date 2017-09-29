package com.example.admin.w5d3geolocation.mainactivity;

import com.example.admin.w5d3geolocation.view.mainactivity.MainActivity;

import dagger.Component;

/**
 * Created by admin on 9/26/2017.
 */

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    //inject the activity reference for the object
    void inject(MainActivity mainActivity);

}
