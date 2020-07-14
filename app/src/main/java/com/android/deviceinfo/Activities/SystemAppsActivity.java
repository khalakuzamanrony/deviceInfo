package com.android.deviceinfo.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Interface.ItemClickListener;
import com.android.deviceinfo.R;

import java.util.ArrayList;
import java.util.List;

public class SystemAppsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<SApps> arrayList = new ArrayList<>();
    private SAppsAdapter sAppsAdapter;
    private TextView totalAppCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_apps);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("System Apps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //-----Initializations----//
        recyclerView = findViewById(R.id.total_sapps_rv);
        recyclerView.setHasFixedSize(true);
        totalAppCount = findViewById(R.id.total_s_apps);

        getInstalledApps();
       sAppsAdapter=new SAppsAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(sAppsAdapter);

    }

    private void getInstalledApps() {
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (isSytemApp(p)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                String appPName = p.applicationInfo.packageName;
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String version = p.versionName;

                arrayList.add(new SApps(appName, appPName, version, icon));
                //Log.e("APPS", appName + " " + appPName + " " + version + " " + icon);
            }
            totalAppCount.setText("Total Installed Apps : " + arrayList.size());
        }
    }

    private boolean isSytemApp(PackageInfo p) {
        return (p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public class SApps {
        private String name, packagename, version;
        private Drawable icon;

        public SApps() {
        }

        public SApps(String name, String packagename, String version, Drawable icon) {
            this.name = name;
            this.packagename = packagename;
            this.version = version;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackagename() {
            return packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }
    public  class SAppsAdapter extends RecyclerView.Adapter<SAppsAdapter.ViewHolder>{
private Context context;
private ArrayList<SApps> arrayList2;

        public SAppsAdapter(Context context, ArrayList<SApps> arrayList2) {
            this.context = context;
            this.arrayList2 = arrayList2;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final SApps apps = arrayList2.get(position);
            holder.name.setText(apps.getName());
            holder.packageName.setText(apps.getPackagename());
            holder.version.setText(apps.getVersion());
            holder.icon.setImageDrawable(apps.getIcon());
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void OnclickItem(View view, final int position) {
                    String[] options = {"Open App", "App Info"};
                    AlertDialog.Builder adb = new AlertDialog.Builder(SystemAppsActivity.this);
                    adb.setTitle("Choose");
                    adb.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent intent = getPackageManager().getLaunchIntentForPackage(apps.getPackagename());
                                if (intent != null) {
                                    startActivity(intent);
                                }
                            } else if (which == 1) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package: "+apps.getPackagename()));
                                startActivity(intent);
                            }
                        }
                    });
                    adb.create().show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView icon;
            private TextView name, packageName, version;
            ItemClickListener itemClickListener;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                packageName = itemView.findViewById(R.id.package_name);
                version = itemView.findViewById(R.id.version);
                icon = itemView.findViewById(R.id.icon);
                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                this.itemClickListener.OnclickItem(v, getLayoutPosition());
            }

            public void setItemClickListener(ItemClickListener itemClickListener) {
                this.itemClickListener = itemClickListener;
            }
        }
    }

}