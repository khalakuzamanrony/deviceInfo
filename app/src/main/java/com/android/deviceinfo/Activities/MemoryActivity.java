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
        float total_rom = (totalBlocks * size) / (1024 * 1024);//value in GB
        float avail_rom = (avail_blocks * size) / (1024 * 1024);//value in GB
        float used_rom = total_rom - avail_rom;//value in MB

        float avail_rom_p = (avail_rom / total_rom) * 100;//value in MB
        float used_rom_p = (used_rom / total_rom) * 100;//value in MB


        NumberFormat numberFormat_avail_romp = NumberFormat.getNumberInstance();
        numberFormat_avail_romp.setMaximumFractionDigits(1);
        numberFormat_avail_romp.setMinimumFractionDigits(1);
        String avail_rom_pe = numberFormatPercent.format(avail_rom_p);

        NumberFormat numberFormat_used_romp = NumberFormat.getNumberInstance();
        numberFormat_used_romp.setMaximumFractionDigits(1);
        numberFormat_used_romp.setMinimumFractionDigits(1);
        String used_rom_pe = numberFormatPercent.format(used_rom_p);


        //------Getting HEAP info----//
        Runtime runtime = Runtime.getRuntime();
        long max = runtime.maxMemory() / (1024 * 1024);


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Total RAM", total_ram + " MB"));
        arrayList.add(new CommonModel("Available RAM", avail_ram + " MB (" + avail_ram_pe + "%)"));
        arrayList.add(new CommonModel("Used RAM", used_ram + " MB  (" + used_ram_pe + "%)"));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));

        arrayList.add(new CommonModel("Total ROM", total_rom + " MB"));
        arrayList.add(new CommonModel("Available ROM", avail_rom + " MB (" + avail_rom_pe + "%)"));
        arrayList.add(new CommonModel("Used ROM", used_rom + " MB  (" + used_rom_pe + "%)"));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));
        arrayList.add(new CommonModel("", ""));

        arrayList.add(new CommonModel("Heap Memory", max + " MB "));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

}