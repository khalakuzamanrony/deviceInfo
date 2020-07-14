package com.android.deviceinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CPUActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;
    ProcessBuilder processBuilder;
    String holder="";
    String[] DATA={"/system/bin/cat","/proc/cpuinfo"};
    InputStream inputStream;
    Process process;
    byte[] mByteArray;
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


        //-----Getting CPU Info----//
        mByteArray=new byte[1024];
        try {
            processBuilder=new ProcessBuilder(DATA);
            process=processBuilder.start();
            inputStream=process.getInputStream();
            while (inputStream.read(mByteArray)!=-1){
                holder=holder+new String(mByteArray);
            }
            inputStream.close();
        }catch (Exception e){

        }






        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Core 1",""+Collections.singletonList(holder) ));



        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }
}