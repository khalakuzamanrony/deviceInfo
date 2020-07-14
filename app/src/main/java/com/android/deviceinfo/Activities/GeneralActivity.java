package com.android.deviceinfo.Activities;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.uptimeMillis;
import static java.lang.System.getProperty;

public class GeneralActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("General");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.general_rv);
        recyclerView.setHasFixedSize(true);

        //--Getting Values--//
        //Uptime
       // long uptimemill = uptimeMillis();
        long uptimemill= SystemClock.elapsedRealtime();

        String uptime = String.format("%02d : %02d : %02d",
                TimeUnit.MILLISECONDS.toHours(uptimemill),
                TimeUnit.MILLISECONDS.toMinutes(uptimemill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptimemill)),
                TimeUnit.MILLISECONDS.toSeconds(uptimemill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptimemill)));

        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Model", Build.MODEL));
        arrayList.add(new CommonModel("Manufacturer", Build.MANUFACTURER));
        arrayList.add(new CommonModel("Device", Build.DEVICE));
        arrayList.add(new CommonModel("GPU", ""));
        arrayList.add(new CommonModel("Board", Build.BOARD));
        arrayList.add(new CommonModel("Hardware", Build.HARDWARE));
        arrayList.add(new CommonModel("Brand", Build.BRAND));
        arrayList.add(new CommonModel("Android Version", Build.VERSION.RELEASE));
        arrayList.add(new CommonModel("OS Name", Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName()));
        arrayList.add(new CommonModel("API Level", Build.VERSION.SDK_INT+""));
        arrayList.add(new CommonModel("BootLoader", Build.BOOTLOADER));
        arrayList.add(new CommonModel("Build Number", Build.VERSION.SDK_INT+""));
        arrayList.add(new CommonModel("Radio Version", Build.getRadioVersion()));
        arrayList.add(new CommonModel("Kernel", getProperty("os.arch")));
        arrayList.add(new CommonModel("Android Runtime", "ART "+getProperty("java.vm.version")));
        arrayList.add(new CommonModel("Up Time", uptime));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

}