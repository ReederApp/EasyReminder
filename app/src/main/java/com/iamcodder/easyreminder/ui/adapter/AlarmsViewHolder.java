package com.iamcodder.easyreminder.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.data.local.model.InfoModel;

class AlarmsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtHours;
    private TextView txtTitle;
    private TextView txtContent;

    public AlarmsViewHolder(@NonNull View itemView) {
        super(itemView);
        txtHours = itemView.findViewById(R.id.cardItemTxtHours);
        txtTitle = itemView.findViewById(R.id.cardItemTxtTitle);
        txtContent = itemView.findViewById(R.id.cardItemTxtContent);
    }

    public void setData(InfoModel infoModel) {
        String hours = infoModel.getCalendarHours() + ":" + infoModel.getCalendarMinute();
        txtHours.setText(hours);
        txtTitle.setText(infoModel.getNotificationTitle());
        txtContent.setText(infoModel.getNotificationContent());
    }

}
