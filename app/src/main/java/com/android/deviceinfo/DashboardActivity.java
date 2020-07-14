package com.android.deviceinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Adapter.MainRVAdapter;
import com.android.deviceinfo.Model.Model;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ArrayList<Model> arrayList;
    private RecyclerView main_rv;
    private GridLayoutManager gridLayoutManager;
    private MainRVAdapter adapter;
    private TextView deviceName, deviceAndroidVersionName;
    private ImageView androidVersionImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //----TOOLBAR----//
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----COLLAPSING TOOLBAR----//
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getTitle());

        //----APPBAR  LAYOUT----//
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        //----RecyclerView----//
        main_rv = findViewById(R.id.main_rv);
        main_rv.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        main_rv.setLayoutManager(gridLayoutManager);
        deviceName = findViewById(R.id.device_name);
        deviceAndroidVersionName = findViewById(R.id.device_android_version);
        androidVersionImage = findViewById(R.id.device_icon);

        //----DATA TO RV----//
        arrayList = new ArrayList<>();
        arrayList.add(new Model("General", R.drawable.one));
        arrayList.add(new Model("DeviceID", R.drawable.one));
        arrayList.add(new Model("Display", R.drawable.one));
        arrayList.add(new Model("Battery", R.drawable.one));
        arrayList.add(new Model("User Apps", R.drawable.one));
        arrayList.add(new Model("System Apps", R.drawable.one));
        arrayList.add(new Model("Memory", R.drawable.one));
        arrayList.add(new Model("CPU", R.drawable.one));
        arrayList.add(new Model("Sensors", R.drawable.one));
        arrayList.add(new Model("SIM", R.drawable.one));


        adapter = new MainRVAdapter(getApplicationContext(), arrayList);
        main_rv.setAdapter(adapter);

        //----Setting DATA to TOP----//
        String manufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String androidVersionName = Build.VERSION.RELEASE;
        String androidVersionCode = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();

        deviceName.setText(manufacturer + " " + deviceModel);
        deviceAndroidVersionName.setText(androidVersionName + " " + androidVersionCode);

        //Setting image
        try {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
                androidVersionImage.setImageResource(R.drawable.three);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                androidVersionImage.setImageResource(R.drawable.one);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}