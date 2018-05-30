package com.tech.gigabyte.batterylavel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    //Getting Battery Percentages/Laval
    private void getBatteryPercentage() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                //Integer field containing the current battery level
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

                //Integer containing the maximum battery level.
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (currentLevel >= 0 && scale > 0) {
                    level = (currentLevel * 100) / scale;
                }
                textView.setText("" + level + "%");

                //Unregister a previously registered BroadcastReceiver
                context.unregisterReceiver(this);
            }
        };
        //This is a sticky broadcast containing the charging state, level, and other information about the battery.
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the activity content from a layout resource
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        getBatteryPercentage();
    }

}