package itan.com.bluetoothle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 This activity represents the Central/Client role.
 Bluetooth communication flow:
    1. advertise [peripheral]
    2. scan [central]
    3. connect [central]
    4. notify [peripheral]
    5. receive [central]
 */
public class CentralRoleActivity extends BluetoothActivity implements View.OnClickListener {

    private BluetoothAdapter mBluetoothAdapter;

    private Button mScanButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScanButton = (Button) findViewById(R.id.button_scan);
        mScanButton.setOnClickListener(this);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_central_role;
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.button_scan:
                onBackPressed();
                break;


        }
    }


    @Override
    protected int getTitleString() {
        return R.string.peripheral_screen;
    }


    private void enableScan() {

    }


}
