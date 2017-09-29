package com.example.admin.w5d3geolocation.view.mainactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.w5d3geolocation.Address;
import com.example.admin.w5d3geolocation.R;

import java.util.List;

/**
 * Created by admin on 9/28/2017.
 */

public class AddressesRecyclerViewAdapter extends RecyclerView.Adapter<AddressesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "AdapterTag";
    private final List<Address> mValues;
    //private final OnListFragmentInteractionListener mListener;
    Context context;

    public AddressesRecyclerViewAdapter(List<Address> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_address_item, parent, false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAddress.setText(mValues.get(position).getStreet());
        holder.mState.setText(mValues.get(position).getState());
        holder.mZip.setText(mValues.get(position).getZip());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAddress;
        public final TextView mState;
        public final TextView mZip;
        public Address mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAddress = view.findViewById(R.id.tvAddress);
            mState = view.findViewById(R.id.tvState);
            mZip = view.findViewById(R.id.tvZip);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mState.getText() + "'";
        }
    }

    public void addItem(Address entry){
        mValues.add(entry);
        notifyItemInserted(mValues.size() - 1);
    }

    public List<Address> getItems(){
        return mValues;
    }

}
