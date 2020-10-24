package com.iamcodder.easyreminder.interfaces;

public interface SendData {
    void sendText(String title, String content);

    void sendDate(int year, int month, int day);

    void sendTime(int hour, int minute);
}
