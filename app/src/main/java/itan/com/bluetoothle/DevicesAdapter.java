package itan.com.bluetoothle;

import android.bluetooth.le.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itanbarpeled on 28/01/2018.
 */

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder>  {


    private ArrayList<ScanResult> mArrayList;


    public DevicesAdapter() {
        mArrayList = new ArrayList<>();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mDeviceNameView;
        TextView mDeviceNameAddressView;

        ViewHolder(View view) {

            super(view);
            mDeviceNameView = (TextView) view.findViewById(R.id.device_name);
            mDeviceNameAddressView = (TextView) view.findViewById(R.id.device_address);
        }

    }

    public DevicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_device_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ScanResult scanResult = mArrayList.get(position);
        String deviceName = scanResult.getDevice().getName();
        String deviceAddress = scanResult.getDevice().getAddress();

        if (TextUtils.isEmpty(deviceName)) {
            holder.mDeviceNameView.setText("");
        } else {
            holder.mDeviceNameView.setText(deviceName);
        }

        if (TextUtils.isEmpty(deviceAddress)) {
            holder.mDeviceNameAddressView.setText("");
        } else {
            holder.mDeviceNameAddressView.setText(deviceAddress);
        }

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public void add(ScanResult scanResult) {
        add(scanResult, true);
    }

    /**
     * Add a ScanResult item to the adapter if a result from that device isn't already present.
     * Otherwise updates the existing position with the new ScanResult.
     */
    public void add(ScanResult scanResult, boolean notify) {

        if (scanResult == null) {
            return;
        }

        int existingPosition = getPosition(scanResult.getDevice().getAddress());

        if (existingPosition >= 0) {
            // Device is already in list, update its record.
            mArrayList.set(existingPosition, scanResult);
        } else {
            // Add new Device's ScanResult to list.
            mArrayList.add(scanResult);
        }

        if (notify) {
            notifyDataSetChanged();
        }

    }


    public void add(List<ScanResult> scanResults) {
        if (scanResults != null) {
            for (ScanResult scanResult : scanResults) {
                add(scanResult, false);
            }
            notifyDataSetChanged();
        }
    }


    /**
     * Search the adapter for an existing device address and return it, otherwise return -1.
     */
    private int getPosition(String address) {
        int position = -1;
        for (int i = 0; i < mArrayList.size(); i++) {
            if (mArrayList.get(i).getDevice().getAddress().equals(address)) {
                position = i;
                break;
            }
        }
        return position;
    }



}
