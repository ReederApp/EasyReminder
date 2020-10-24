package com.iamcodder.easyreminder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.interfaces.SendData;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsViewHolder> {

    private List<InfoModel> infoModelList;
    private SendData sendData;

    public AlarmsAdapter(List<InfoModel> infoModelList, SendData sendData) {
        this.infoModelList = infoModelList;
        this.sendData = sendData;
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
        holder.setIconRemoveClick(sendData, position, infoModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return infoModelList.size();
    }

    public void addNewItem(InfoModel infoModel) {
        this.infoModelList.add(infoModel);
        this.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.infoModelList.remove(position);
        this.notifyDataSetChanged();
    }
}
