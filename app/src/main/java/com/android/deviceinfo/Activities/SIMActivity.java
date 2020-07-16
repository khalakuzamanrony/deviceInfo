package com.android.deviceinfo.Activities;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

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

public class SIMActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;
    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_i_m);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sensors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----Initializations----//
        recyclerView = findViewById(R.id.deviceid_rv);
        recyclerView.setHasFixedSize(true);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        getSimInfo();

        //----ADD VIEW----//
        MobileAds.initialize(this, "ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void getSimInfo() {
        int ss = tm.getSimState();
        String simState = "";
        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "Network Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Ready";
                break;
            case TelephonyManager.SIM_STATE_NOT_READY:
                simState = "Not Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO Error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "Perm Disabled";
                break;
        }

        //-----Service Provider---//
        String service_provider = tm.getSimOperatorName();
        //-----Mobileoperator name---//
        String operatorname = tm.getNetworkOperatorName();
        //String simID=tm.getSimSerialNumber();
        // String imei=tm.getDeviceId();
        //String simSubsId=tm.getSubscriberId();
        String SoftV = tm.getDeviceSoftwareVersion();
        String country = tm.getNetworkCountryIso();
        String mcc = tm.getSimOperator();
        String vmt = tm.getVoiceMailAlphaTag();
        boolean roaming = tm.isNetworkRoaming();


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Sim State", simState));
        arrayList.add(new CommonModel("Service Provider", service_provider));
        arrayList.add(new CommonModel("Operator Name ", operatorname));
        //arrayList.add(new CommonModel("ICCID ", simID));
        // arrayList.add(new CommonModel("IMEI ", imei));
        //arrayList.add(new CommonModel("IMSI ",simSubsId ));
        arrayList.add(new CommonModel("Device Software Version ", SoftV));
        arrayList.add(new CommonModel("Countery Code ", country));
        arrayList.add(new CommonModel("CC + MNC ", mcc));
        arrayList.add(new CommonModel("Voicemail ", vmt));
        arrayList.add(new CommonModel("Roaming ", roaming + ""));


    }
}