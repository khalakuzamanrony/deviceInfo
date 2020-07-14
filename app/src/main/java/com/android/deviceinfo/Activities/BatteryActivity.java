package com.android.deviceinfo.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class BatteryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;
    String charging_status, battery_condition, power_source, battery_tech;
    int temp_f, source, voltage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);


        //----ADD VIEW----//
        MobileAds.initialize(this,"ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            GetData(intent);
        }
    };

    private void GetData(Intent intent) {
        arrayList.clear();
        //get %
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        //condition
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        if (health == BatteryManager.BATTERY_HEALTH_COLD) {
            battery_condition = "Cold";
        }
        if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
            battery_condition = "Dead";
        }
        if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
            battery_condition = "Good";
        }
        if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
            battery_condition = "Over Voltage";
        }
        if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
            battery_condition = "Over Heat";
        }
        if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
            battery_condition = "Unknown";
        }
        if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
            battery_condition = "Unspecified Failure";
        }
        //get temp

        int temp_c = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)/10;
        temp_f = (int) (temp_c * 1.8 + 32);

        //Power Source
        source = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if (source == BatteryManager.BATTERY_PLUGGED_USB) {
            power_source = "USB";
        }
        if (source == BatteryManager.BATTERY_PLUGGED_AC) {
            power_source = "AC";
        }
        if (source == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
            power_source = "Wireless";
        }
        if (source != BatteryManager.BATTERY_PLUGGED_WIRELESS && source != BatteryManager.BATTERY_PLUGGED_USB && source != BatteryManager.BATTERY_PLUGGED_AC) {
            power_source = "Battery";
        }

        //Status
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            charging_status = "Charging";
        }
        if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
            charging_status = "Not Charging";
        }
        if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            charging_status = "Discharging";
        }
        if (status == BatteryManager.BATTERY_STATUS_FULL) {
            charging_status = "Full";
        }
        if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
            charging_status = "Unknown";
        }

        //Battery technology
        battery_tech = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        //battery voltage
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Condition", battery_condition));
        arrayList.add(new CommonModel("Level", level + " %"));
        arrayList.add(new CommonModel("Temperature", temp_f + " F /"+temp_c+" C"));
        arrayList.add(new CommonModel("Power Source", power_source + ""));
        arrayList.add(new CommonModel("Status", charging_status));
        arrayList.add(new CommonModel("Battery Type", battery_tech));
        arrayList.add(new CommonModel("Voltage", voltage + " mV"));
        arrayList.add(new CommonModel("Battery Capacity", getBatteryCapacity()+" mAh"));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    public Double getBatteryCapacity() {

        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(getApplicationContext());

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;
    }
}