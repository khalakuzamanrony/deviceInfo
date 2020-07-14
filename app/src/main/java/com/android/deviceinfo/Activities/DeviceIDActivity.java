package com.android.deviceinfo.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Adapter.CommonAdapter;
import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

public class DeviceIDActivity extends AppCompatActivity {
    private static final int REQ = 1;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;
    private TelephonyManager telephonyManager;
    private String imei, simSerial, simSubsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_i_d);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Device ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);


        //----ADD VIEW----//
        MobileAds.initialize(this,"ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //----Device id--//
        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //EMEI
  /*      telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, REQ);
            } else {
                imei = telephonyManager.getDeviceId();
                simSerial = telephonyManager.getSimSerialNumber();
                simSubsID = telephonyManager.getSubscriberId();
            }
        } else {
            imei = telephonyManager.getDeviceId();
            simSerial = telephonyManager.getSimSerialNumber();
            simSubsID = telephonyManager.getSubscriberId();
        }*/

        //IP address
        String ipAddress = "No Internet Connection";
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        boolean iS3gEnabled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        ipAddress = getMobileIP();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            {
                if (mMobile != null) {
                    ipAddress = iS3gEnabled + "";
                }
            }
        }
        //Wifi IP address
        String wifiMACAddress = "No wifi Connection";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo != null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        wifiMACAddress = getMobileIPwifiMAC();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            {
                if (mMobile != null) {
                    ipAddress = iS3gEnabled + "";
                }
            }
        }

        //Bluetooth
        String bt=android.provider.Settings.Secure.getString(this.getContentResolver(),"bluetooth_address");


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Phone Type", ""));
        arrayList.add(new CommonModel("Test Device ID", ""));
        arrayList.add(new CommonModel("Android Device ID", deviceid));
       // arrayList.add(new CommonModel("IMEI, MEID or ESN", imei));
        arrayList.add(new CommonModel("Hardware Serial Number", Build.SERIAL));
       // arrayList.add(new CommonModel("Sim card Serial Number", simSerial));
       // arrayList.add(new CommonModel("Sim Subscriber ID", simSubsID));
        arrayList.add(new CommonModel("IP Address", ipAddress));
        arrayList.add(new CommonModel("WiFi MAC Address", wifiMACAddress));
        arrayList.add(new CommonModel("Bluetooth MAC Address", bt));
        arrayList.add(new CommonModel("Build FringerPrint", Build.FINGERPRINT));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                    imei = telephonyManager.getDeviceId();
                    simSerial = telephonyManager.getSimSerialNumber();
                    simSubsID = telephonyManager.getSubscriberId();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String getMobileIP() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces) {
                List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    private String getMobileIPwifiMAC() {
        WifiManager wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info=wifiManager.getConnectionInfo();
        int ip=info.getIpAddress();
        return Formatter.formatIpAddress(ip);

    }
}