package itan.com.bluetoothle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

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

                } else {

                }
                break;

        }
    }


    @Override
    protected int getTitleString() {
        return R.string.central_screen;
    }

}
