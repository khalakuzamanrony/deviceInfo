package com.android.deviceinfo.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CPUActivity extends AppCompatActivity {
    ProcessBuilder processBuilder;
    String holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process;
    byte[] mByteArray;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;

    public static Map<String, String> getCpuInfoMap() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Scanner s = new Scanner(new File("/proc/cpuinfo"));
            while (s.hasNextLine()) {
                String[] vals = s.nextLine().split(": ");
                if (vals.length > 1)
                    map.put(vals[0].trim(), vals[1].trim());
            }
        } catch (Exception e) {
            Log.e("getCpuInfoMap", Log.getStackTraceString(e));
        }
        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_u);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CPU");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);


        //----ADD VIEW----//
        MobileAds.initialize(this, "ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //-----Getting CPU Info----//
        /*getCpuInfoMap();
        Log.d("getCpuInfoMap test", getCpuInfoMap().toString());*/
        mByteArray = new byte[1024];
        try {
            processBuilder = new ProcessBuilder(DATA);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            while (inputStream.read(mByteArray) != -1) {
                holder = holder + new String(mByteArray);
            }
            inputStream.close();
        } catch (Exception e) {

        }


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("", "" + Collections.singletonList(holder)));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);

        recyclerView.setAdapter(adapter);
    }
}