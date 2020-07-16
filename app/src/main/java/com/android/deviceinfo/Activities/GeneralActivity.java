package com.android.deviceinfo.Activities;

import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static java.lang.System.getProperty;

public class GeneralActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CommonModel> arrayList = new ArrayList<>();
    private CommonAdapter adapter;

    public static boolean isRooted2() {

        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        } else {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            } else {
                file = new File("/system/xbin/su");
                return file.exists();
            }
        }
    }

    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

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


        //----ADD VIEW----//
        MobileAds.initialize(this, "ca-app-pub-3385204674971318~5484098769");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //--Getting Values--//
        //Uptime
        // long uptimemill = uptimeMillis();
        long uptimemill = SystemClock.elapsedRealtime();

        String uptime = String.format("%02d : %02d : %02d",
                TimeUnit.MILLISECONDS.toHours(uptimemill),
                TimeUnit.MILLISECONDS.toMinutes(uptimemill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptimemill)),
                TimeUnit.MILLISECONDS.toSeconds(uptimemill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptimemill)));

        boolean root = isDeviceRooted();
        String r;
        if (root == true) {
            r = "Yes";
        } else {
            r = "No";
        }


        //-----Sending Values to Adapter----///
        arrayList.add(new CommonModel("Model", Build.MODEL));
        arrayList.add(new CommonModel("Manufacturer", Build.MANUFACTURER));
        arrayList.add(new CommonModel("Device", Build.DEVICE));
        arrayList.add(new CommonModel("GPU", ""));
        arrayList.add(new CommonModel("Board", Build.BOARD));
        arrayList.add(new CommonModel("Hardware", Build.HARDWARE));
        arrayList.add(new CommonModel("Brand", Build.BRAND));
        arrayList.add(new CommonModel("Root Access", r));
        arrayList.add(new CommonModel("Android Version", Build.VERSION.RELEASE));
        arrayList.add(new CommonModel("OS Name", Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName()));
        arrayList.add(new CommonModel("API Level", Build.VERSION.SDK_INT + ""));
        arrayList.add(new CommonModel("BootLoader", Build.BOOTLOADER));
        arrayList.add(new CommonModel("Build Number", Build.VERSION.SDK_INT + ""));
        arrayList.add(new CommonModel("Radio Version", Build.getRadioVersion()));
        arrayList.add(new CommonModel("Kernel", getProperty("os.arch")));
        arrayList.add(new CommonModel("Android Runtime", "ART " + getProperty("java.vm.version")));
        arrayList.add(new CommonModel("Up Time", uptime));


        adapter = new CommonAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    public class GPU implements GLSurfaceView.Renderer {
        public final String renderer = null;
        Random aleatorio = new Random();

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            float r = aleatorio.nextFloat();
            float g = aleatorio.nextFloat();
            float b = aleatorio.nextFloat();
            gl.glClearColor(r, g, b, 1.0f);

            Log.d("GL", "GL_RENDERER = " + gl.glGetString(GL10.GL_RENDERER));
            Log.d("GL", "GL_VENDOR = " + gl.glGetString(GL10.GL_VENDOR));
            Log.d("GL", "GL_VERSION = " + gl.glGetString(GL10.GL_VERSION));
            Log.i("GL", "GL_EXTENSIONS = " + gl.glGetString(GL10.GL_EXTENSIONS));

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}