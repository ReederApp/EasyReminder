package com.iamcodder.easyreminder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.data.local.model.InfoModel;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsViewHolder> {

    private List<InfoModel> infoModelList;

    public AlarmsAdapter(List<InfoModel> infoModelList) {
        this.infoModelList = infoModelList;
    }

    @NonNull
    @Override
    public AlarmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_item, parent, false);
        return new AlarmsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmsViewHolder holder, int position) {
        holder.setData(infoModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return infoModelList.size();
    }

    public void updateUi(InfoModel infoModel) {
        this.infoModelList.add(infoModel);
        this.notifyDataSetChanged();
    }
}
