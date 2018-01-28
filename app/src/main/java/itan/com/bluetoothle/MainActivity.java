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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private static final int REQUEST_ENABLE_BT = 1;


    private Button mPeripheralButton;
    private Button mCentralButton;

    private BluetoothAdapter mBluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPeripheralButton = (Button) findViewById(R.id.button_role_peripheral);
        mCentralButton = (Button) findViewById(R.id.button_role_central);

        mPeripheralButton.setOnClickListener(this);
        mCentralButton.setOnClickListener(this);

        if (savedInstanceState == null) {
            initBT();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {

                    // Bluetooth is now Enabled, are Bluetooth Advertisements supported on
                    // this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        enableNavigation();

                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // User declined to enable Bluetooth, exit the app.
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initBT() {

        BluetoothManager bluetoothService = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE));

        if (bluetoothService != null) {

            mBluetoothAdapter = bluetoothService.getAdapter();

            // Is Bluetooth supported on this device?
            if (mBluetoothAdapter != null) {

                // Is Bluetooth turned on?
                if (mBluetoothAdapter.isEnabled()) {

                    // Are Bluetooth Advertisements supported on this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        // Everything is supported and enabled.
                        enableNavigation();

                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // Prompt user to turn on Bluetooth (logic continues in onActivityResult()).
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
            } else {

                // Bluetooth is not supported.
                showErrorText(R.string.bt_not_supported);
            }

        }
    }


    @Override
    public void onClick(View view) {

        Intent intent = null;

        switch(view.getId()) {

            case R.id.button_role_peripheral:
                intent = new Intent(this, PeripheralRoleActivity.class);
                break;

            case R.id.button_role_central:
                intent = new Intent(this, CentralRoleActivity.class);
                break;

        }

        if (intent != null) {
            startActivity(intent);
        }
    }


    private void enableNavigation() {
        mPeripheralButton.setEnabled(true);
        mCentralButton.setEnabled(true);
    }


    private void showErrorText(int string) {
        Snackbar snackbar = Snackbar.make(mPeripheralButton, string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
