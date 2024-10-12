package com.pg.crm.ui.weighment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.pg.crm.R;

import java.util.ArrayList;


public class BluetoothDevicesAdapter extends ArrayAdapter<BluetoothDevice> {
    public BluetoothDevicesAdapter(Context context, ArrayList<BluetoothDevice> devices) {
        super(context, 0, devices);
    }

    @SuppressLint("MissingPermission")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        BluetoothDevice device = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_bluetooth_device_item, parent, false);
        }

        // Lookup view for data population
        TextView deviceName = (TextView) convertView.findViewById(R.id.deviceName);
        TextView deviceMacAddr = (TextView) convertView.findViewById(R.id.deviceMacAddr);

        // Populate the data into the template view using the data object

        deviceName.setText(device.getName());
       deviceMacAddr.setText(device.getAlias());

        // Return the completed view
        return convertView;
    }
}