package com.iamcodder.easyreminder.interfaces;

import com.iamcodder.easyreminder.data.local.model.InfoModel;

public interface SendData {
    void sendDialogData(String title, String content);

    void sendDate(int year, int month, int day);

    void sendTime(int hour, int minute);

    void sendAlarmInfo(int position, InfoModel infoModel);
}
