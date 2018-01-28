package itan.com.bluetoothle;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mPeripheralButton;
    private Button mCentralButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPeripheralButton = (Button) findViewById(R.id.button_role_peripheral);
        mCentralButton = (Button) findViewById(R.id.button_role_central);

        mPeripheralButton.setOnClickListener(this);
        mCentralButton.setOnClickListener(this);

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
}
