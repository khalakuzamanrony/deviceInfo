package com.android.deviceinfo.Activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import java.util.List;

public class SensorsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView t_sensors;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;

    public static String SensortypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "ACCELEROMETER";
            case Sensor.TYPE_TEMPERATURE:
                return "TEMPERATURE";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "AMBIENT_TEMPERATURE";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "GAME_ROTATION_VECTOR";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "GEOMAGNETIC_ROTATION_VECTOR";
            case Sensor.TYPE_GRAVITY:
                return "GRAVITY";
            case Sensor.TYPE_GYROSCOPE:
                return "GYROSCOPE";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "GYROSCOPE_UNCALIBRATED";
            case Sensor.TYPE_HEART_BEAT:
                return "HEART BEAT";
            case Sensor.TYPE_HEART_RATE:
                return "HEAT RATE";
            case Sensor.TYPE_LIGHT:
                return "LIGHT";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "LINEAR_ACCELERATION";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "MAGNETIC_FIELD";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "MAGNETIC_FIELD_UNCALIBRATED";
            case Sensor.TYPE_ORIENTATION:
                return "ORIENTATION";
            case Sensor.TYPE_PRESSURE:
                return "PRESSURE";
            case Sensor.TYPE_PROXIMITY:
                return "PROXIMITY";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "RELATIVE_HUMIDITY";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "ROTATION_VECTOR";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "SIGNIFICANT_MOTION";
            case Sensor.TYPE_STEP_COUNTER:
                return "STEP_COUNTER";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            default:
                return "Unknown";

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sensors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        t_sensors = findViewById(R.id.total_sensors);
        recyclerView.setHasFixedSize(true);


        //----ADD VIEW----//
        MobileAds.initialize(this, "ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);


        //-----Sending Values to Adapter----///
        for (int i = 0; i < sensors.size(); i++) {
            Sensor pos = sensors.get(i);
            arrayList.add(new CommonModel(pos.getName(),
                    "Type : " + SensortypeToString(pos.getType()) + "\n" +
                            "Vendor : " + pos.getVendor() + "\n" +
                            "Version : " + pos.getVersion() + "\n" +
                            "Resolution : " + pos.getResolution() + "\n" +
                            "Power : " + pos.getPower() + " mAh\n" +
                            "Max Power Range : " + pos.getMaximumRange() + "\n" +
                            "Max Delay : " + pos.getMaxDelay() + "\n" +
                            "Min Delay : " + pos.getMinDelay() + "\n"
            ));
        }
        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        t_sensors.setText("Total Sensors: " + arrayList.size());
        recyclerView.setAdapter(adapter);
    }
}