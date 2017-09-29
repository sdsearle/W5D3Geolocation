package com.example.admin.w5d3geolocation.view.mainactivity;

import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.w5d3geolocation.R;

/**
 * Created by admin on 9/28/2017.
 */

public class DiagLatLngFrag extends DialogFragment {

    private static DiagLatLngFrag diagLatLngFrag;
    private DialogListner dl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dl = (DialogListner) context;
    }

    public static DiagLatLngFrag newInstance(String s){
        diagLatLngFrag = new DiagLatLngFrag();
        Bundle b = new Bundle();
        b.putString("LatLng",s);
        diagLatLngFrag.setArguments(b);


        return diagLatLngFrag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.diag_lat_lng, null);
        builder.setView(view);
        final String latLng = getArguments().getString("LatLng");
        TextView tvLatLng = view.findViewById(R.id.tvLatLng);
        tvLatLng.setText(latLng);
        Button btnAddAddress = view.findViewById(R.id.btnAddtoList);
        Button btnMaps = view.findViewById(R.id.btnGoToMap);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagLatLngFrag.dismiss();
                dl.onAddListner();
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagLatLngFrag.dismiss();
                dl.onShowMap();
            }
        });

        return builder.create();
    }

    public interface DialogListner{
        void onAddListner();
        void onShowMap();
    }

}
