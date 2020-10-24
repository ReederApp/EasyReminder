package com.iamcodder.easyreminder.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamcodder.easyreminder.R;
import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.interfaces.SendData;

class AlarmsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtHours;
    private TextView txtTitle;
    private TextView txtContent;
    private ImageView imgRemove;

    public AlarmsViewHolder(@NonNull View itemView) {
        super(itemView);
        txtHours = itemView.findViewById(R.id.cardItemTxtHours);
        txtTitle = itemView.findViewById(R.id.cardItemTxtTitle);
        txtContent = itemView.findViewById(R.id.cardItemTxtContent);
        imgRemove = itemView.findViewById(R.id.cardItemRemoveIcon);
    }

    public void setData(InfoModel infoModel) {
        String hours = infoModel.getCalendarHours() + ":" + infoModel.getCalendarMinute();
        txtHours.setText(hours);
        txtTitle.setText(infoModel.getNotificationTitle());
        txtContent.setText(infoModel.getNotificationContent());
    }

    public void setIconRemoveClick(SendData sendData, int position, InfoModel infoModel) {
        imgRemove.setOnClickListener(v -> {
            sendData.sendAlarmInfo(position, infoModel);
        });
    }
}
