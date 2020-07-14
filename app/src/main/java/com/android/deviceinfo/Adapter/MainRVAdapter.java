package com.android.deviceinfo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Activities.BatteryActivity;
import com.android.deviceinfo.Activities.CPUActivity;
import com.android.deviceinfo.Activities.DeviceIDActivity;
import com.android.deviceinfo.Activities.DisplayActivity;
import com.android.deviceinfo.Activities.GeneralActivity;
import com.android.deviceinfo.Activities.MemoryActivity;
import com.android.deviceinfo.Activities.SIMActivity;
import com.android.deviceinfo.Activities.SensorsActivity;
import com.android.deviceinfo.Activities.SystemAppsActivity;
import com.android.deviceinfo.Activities.UserAppsActivity;
import com.android.deviceinfo.Interface.ItemClickListener;
import com.android.deviceinfo.Model.Model;
import com.android.deviceinfo.R;

import java.util.ArrayList;

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Model> arrayList;

    public MainRVAdapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model model = arrayList.get(position);
        holder.title.setText(model.getName());
        holder.icon.setImageResource(model.getIcon());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnclickItem(View view, int position) {
                if (arrayList.get(position).getName().equals("General")) {
                    context.startActivity(new Intent(context, GeneralActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("DeviceID")) {
                    context.startActivity(new Intent(context, DeviceIDActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("Display")) {
                    context.startActivity(new Intent(context, DisplayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("Battery")) {
                    context.startActivity(new Intent(context, BatteryActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("User Apps")) {
                    context.startActivity(new Intent(context, UserAppsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("System Apps")) {
                    context.startActivity(new Intent(context, SystemAppsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("Memory")) {
                    context.startActivity(new Intent(context, MemoryActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("CPU")) {
                    context.startActivity(new Intent(context, CPUActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("Sensors")) {
                    context.startActivity(new Intent(context, SensorsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (arrayList.get(position).getName().equals("SIM")) {
                    context.startActivity(new Intent(context, SIMActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView icon;
        private TextView title;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.main_image);
            title = itemView.findViewById(R.id.main_title);
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
