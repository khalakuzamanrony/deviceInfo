package com.android.deviceinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.deviceinfo.Model.CommonModel;
import com.android.deviceinfo.R;

import java.util.ArrayList;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonModel> arrayList;

    public CommonAdapter(Context context, ArrayList<CommonModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commom_property_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommonModel commonModel = arrayList.get(position);
        holder.title.setText(commonModel.getTitle());
        holder.description.setText(commonModel.getDesc());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.common_title);
            description = itemView.findViewById(R.id.common_description);
        }
    }
}
