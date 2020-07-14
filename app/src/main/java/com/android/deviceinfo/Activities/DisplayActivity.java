package com.android.deviceinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;
    private String ori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);

        //-----Display ----//
        DisplayMetrics displayMetrics=getApplicationContext().getResources().getDisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w=displayMetrics.widthPixels;
        int h=displayMetrics.heightPixels;
        String resolution=String.valueOf(w)+"x "+String.valueOf(h)+" pixels";


        //-----Density----//
        String density=displayMetrics.densityDpi+" dpi";
        float d=displayMetrics.densityDpi;



        //-----Physical Size ----//
        double x= Math.pow(displayMetrics.widthPixels/displayMetrics.xdpi,2);
        double y= Math.pow(displayMetrics.heightPixels/displayMetrics.ydpi,2);
        double screenInch=Math.sqrt(x+y);
        NumberFormat numberFormat=NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String inch=numberFormat.format(screenInch)+" Inch";
        //String s=String.format("%.2f",screenInch+"inch");




         //-----Refresh Rate ----//
        Display display=((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float rrate=display.getRefreshRate();
        NumberFormat numberFormat2=NumberFormat.getNumberInstance();
        numberFormat2.setMinimumFractionDigits(2);
        numberFormat2.setMaximumFractionDigits(2);
        String refreshRate=numberFormat2.format(rrate)+" Hz";


        //-----Orientation ----//
        int orientation= this.getResources().getConfiguration().orientation;
        if (orientation==1){
             ori="Vertical";
        }else {
             ori="Horizontal";
        }

        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Resolution", resolution));
        arrayList.add(new CommonModel("Density", density));
        arrayList.add(new CommonModel("Physical Size", inch));
        arrayList.add(new CommonModel("Logical Size", ""));
        arrayList.add(new CommonModel("Refresh Rate", refreshRate));
        arrayList.add(new CommonModel("Orientation", ori));



        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }
}