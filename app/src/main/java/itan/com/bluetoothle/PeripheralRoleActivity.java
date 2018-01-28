package itan.com.bluetoothle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;


/**
 This activity represents the Peripheral/Server role.
 Bluetooth communication flow:
    1. advertise [peripheral]
    2. scan [central]
    3. connect [central]
    4. notify [peripheral]
    5. receive [central]
 */
public class PeripheralRoleActivity extends BluetoothActivity implements View.OnClickListener {


    private Button mNotifyButton;
    private Switch mSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNotifyButton = (Button) findViewById(R.id.button_notify);
        mSwitch = (Switch) findViewById(R.id.advertise_switch);

        mNotifyButton.setOnClickListener(this);
        mSwitch.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_peripheral_role;
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.button_notify:
                onBackPressed();
                break;

            case R.id.advertise_switch:
                Switch switchToggle = (Switch) view;
                if (switchToggle.isChecked()) {
                    startAdvertising();
                } else {
                    stopAdvertising();
                }
                break;

        }
    }


    @Override
    protected int getTitleString() {
        return R.string.central_screen;
    }


    /**
     * Starts BLE Advertising by starting {@code AdvertiserService}.
     */
    private void startAdvertising() {
        // TODO - maybe bindService? what happens when closing app?
        startService(getServiceIntent(this));
    }


    /**
     * Stops BLE Advertising by stopping {@code AdvertiserService}.
     */
    private void stopAdvertising() {
        stopService(getServiceIntent(this));
        mSwitch.setChecked(false);
    }


    /**
     * Returns Intent addressed to the {@code AdvertiserService} class.
     */
    private static Intent getServiceIntent(Context context) {
        return new Intent(context, AdvertiserService.class);
    }

}
