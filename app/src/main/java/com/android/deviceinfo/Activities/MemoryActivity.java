package com.android.deviceinfo.Activities;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;

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
import java.text.NumberFormat;
import java.util.ArrayList;

public class MemoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Memory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //----ADD VIEW----//
        MobileAds.initialize(this, "ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);

        //------Getting RAM info----//
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        float total_ram = memoryInfo.totalMem / (1024 * 1024);
        float avail_ram = memoryInfo.availMem / (1024 * 1024);
        float used_ram = total_ram - avail_ram;
        float avail_ram_p = avail_ram / total_ram * 100;
        float used_ram_p = used_ram / total_ram * 100;

        NumberFormat numberFormatPercent = NumberFormat.getNumberInstance();
        numberFormatPercent.setMaximumFractionDigits(1);
        numberFormatPercent.setMinimumFractionDigits(1);
        String avail_ram_pe = numberFormatPercent.format(avail_ram_p);

        NumberFormat numberFormatUPercent = NumberFormat.getNumberInstance();
        numberFormatUPercent.setMaximumFractionDigits(1);
        numberFormatUPercent.setMinimumFractionDigits(1);
        String used_ram_pe = numberFormatPercent.format(used_ram_p);


        //------Getting ROM info----//

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float size = stat.getBlockSizeLong();
        float totalBlocks = stat.getBlockCountLong();
        float avail_blocks = stat.getAvailableBlocksLong();
        float total_rom = (totalBlocks * size) / (1024 * 1024 * 1024);//value in GB
        float avail_rom = (avail_blocks * size) / (1024 * 1024 * 1024);//value in GB
        float used_rom = total_rom - avail_rom;//value in GB

        float avail_rom_p = (avail_rom / total_rom) * 100;//value in MB
        float used_rom_p = (used_rom / total_rom) * 100;//value in MB


        //------Getting HEAP info----//
        Runtime runtime = Runtime.getRuntime();
        long max = runtime.maxMemory() / (1024 * 1024);


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Total RAM", getNumberPre(total_ram, 2, 1) + " MB"));
        arrayList.add(new CommonModel("Available RAM", getNumberPre(avail_ram, 2, 1) + " MB (" + getNumberPre(avail_ram_p, 1, 1) + "%)"));
        arrayList.add(new CommonModel("Used RAM", getNumberPre(used_ram, 2, 1) + " MB  (" + getNumberPre(used_ram_p, 1, 1) + "%)"));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));


        arrayList.add(new CommonModel("Total ROM", getNumberPre(total_rom, 2, 1) + " GB"));
        arrayList.add(new CommonModel("Available ROM", getNumberPre(avail_rom, 2, 1) + " GB (" + getNumberPre(avail_rom_p, 1, 1) + "%)"));
        arrayList.add(new CommonModel("Used ROM", getNumberPre(used_rom, 2, 1) + " GB  (" + getNumberPre(used_rom_p, 1, 1) + "%)"));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));


        arrayList.add(new CommonModel("Heap Memory", max + " MB "));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private String getNumberPre(float total_rom, int max, int min) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(max);
        numberFormat.setMinimumFractionDigits(min);
        String formattedNum = numberFormat.format(total_rom);
        return formattedNum;
    }

}