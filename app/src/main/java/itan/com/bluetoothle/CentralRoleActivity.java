package itan.com.bluetoothle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 This activity represents the Central/Client role.
 Bluetooth communication flow:
    1. advertise [peripheral]
    2. scan [central]
    3. connect [central]
    4. notify [peripheral]
    5. receive [central]
 */
public class CentralRoleActivity extends BluetoothActivity implements View.OnClickListener, DevicesAdapter.DevicesAdapterListener {


    /**
     * Stops scanning after 30 seconds.
     */
    private static final long SCAN_PERIOD = 30000;

    private RecyclerView mDevicesRecycler;
    private DevicesAdapter mDevicesAdapter;
    private Button mScanButton;

    private ScanCallback mScanCallback;

    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScanButton = (Button) findViewById(R.id.button_scan);
        mScanButton.setOnClickListener(this);

        mDevicesRecycler = (RecyclerView) findViewById(R.id.devices_recycler_view);
        mDevicesRecycler.setHasFixedSize(true);
        mDevicesRecycler.setLayoutManager(new LinearLayoutManager(this));

        mDevicesAdapter = new DevicesAdapter(this);
        mDevicesRecycler.setAdapter(mDevicesAdapter);

        mHandler = new Handler(Looper.getMainLooper());

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_central_role;
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.button_scan:
                startBLEScan();
                break;

        }
    }


    @Override
    protected int getTitleString() {
        return R.string.central_screen;
    }


    /*
    start Bluetooth Low Energy scan
     */
    private void startBLEScan() {

        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();

        /*
        better to request each time as BluetoothAdapter state might change (connection lost, etc...)
         */
        if (bluetoothAdapter != null) {

            BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

            if (bluetoothLeScanner != null) {

                if (mScanCallback == null) {
                    Log.d(MainActivity.TAG, "Starting Scanning");

                    // Will stop the scanning after a set time.
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScanning();
                        }
                    }, SCAN_PERIOD);

                    // Kick off a new scan.
                    mScanCallback = new SampleScanCallback();
                    bluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);

                    String toastText =
                                    getString(R.string.scan_start_toast) + " "
                                    + TimeUnit.SECONDS.convert(SCAN_PERIOD, TimeUnit.MILLISECONDS) + " "
                                    + getString(R.string.seconds);

                    showMsgText(toastText);

                } else {
                    showMsgText(R.string.already_scanning);
                }

                return;
            }
        }

        showMsgText(R.string.error_unknown);
    }

    /**
     * Return a List of {@link ScanFilter} objects to filter by Service UUID.
     */
    private List<ScanFilter> buildScanFilters() {

        List<ScanFilter> scanFilters = new ArrayList<>();

        ScanFilter.Builder builder = new ScanFilter.Builder();
        // Comment out the below line to see all BLE devices around you
        //builder.setServiceUuid(Constants.SERVICE_UUID);
        scanFilters.add(builder.build());

        return scanFilters;
    }

    /**
     * Return a {@link ScanSettings} object set to use low power (to preserve battery life).
     */
    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }


    /**
     * Stop scanning for BLE Advertisements.
     */
    public void stopScanning() {

        Log.d(MainActivity.TAG, "Stopping Scanning");

        /*
        better to request each time as BluetoothAdapter state might change (connection lost, etc...)
         */
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();

        if (bluetoothAdapter != null) {

            BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

            if (bluetoothLeScanner != null) {

                // Stop the scan, wipe the callback.
                bluetoothLeScanner.stopScan(mScanCallback);
                mScanCallback = null;

                // Even if no new results, update 'last seen' times.
                mDevicesAdapter.notifyDataSetChanged();

                return;
            }
        }

        showMsgText(R.string.error_unknown);
    }


    @Override
    public void onDeviceItemClick(String deviceName, String deviceAddress) {

        //stopScanning();

        Intent intent = new Intent(this, DeviceConnectActivity.class);
        intent.putExtra(DeviceConnectActivity.EXTRAS_DEVICE_NAME, deviceName);
        intent.putExtra(DeviceConnectActivity.EXTRAS_DEVICE_ADDRESS, deviceAddress);
        startActivity(intent);
    }


    /**
     * Custom ScanCallback object - adds to adapter on success, displays error on failure.
     */
    private class SampleScanCallback extends ScanCallback {

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            mDevicesAdapter.add(results);
            logResults(results);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            mDevicesAdapter.add(result);
            logResults(result);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            showMsgText("Scan failed with error: " + errorCode);
        }


        private void logResults(List<ScanResult> results) {
            if (results != null) {
                for (ScanResult result : results) {
                    logResults(result);
                }
            }
        }

        private void logResults(ScanResult result) {
            if (result != null) {
                BluetoothDevice device = result.getDevice();
                if (device != null) {
                    Log.v(MainActivity.TAG, device.getName() + " " + device.getAddress());
                    return;
                }
            }
            Log.e(MainActivity.TAG, "error SampleScanCallback");
        }
    }


}
