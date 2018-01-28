package itan.com.bluetoothle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by itanbarpeled on 28/01/2018.
 */

public abstract class BluetoothActivity extends AppCompatActivity {


    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setToolbar();
    }


    protected abstract int getLayoutId();

    protected abstract int getTitleString();

    protected void onBackButtonClicked() {
        onBackPressed();
    }


    private void setToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonClicked();
            }
        });
        mToolbar.setTitle(getTitleString());
        mToolbar.setTitleTextColor(Color.WHITE);
    }

}

