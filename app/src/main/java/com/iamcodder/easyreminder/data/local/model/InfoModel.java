package com.iamcodder.easyreminder.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoModel implements Parcelable {
    public static final Creator<InfoModel> CREATOR = new Creator<InfoModel>() {
        @Override
        public InfoModel createFromParcel(Parcel in) {
            return new InfoModel(in);
        }

        @Override
        public InfoModel[] newArray(int size) {
            return new InfoModel[size];
        }
    };
    private String notificationTitle;
    private String notificationContent;
    private int calendarMinute;
    private int calendarHours;
    private int intentNumber;

    public InfoModel(String notificationTitle, String notificationContent, int calendarMinute, int calendarHours, int intentNumber) {
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.calendarMinute = calendarMinute;
        this.calendarHours = calendarHours;
        this.intentNumber = intentNumber;
    }

    protected InfoModel(Parcel in) {
        notificationTitle = in.readString();
        notificationContent = in.readString();
        calendarMinute = in.readInt();
        calendarHours = in.readInt();
        intentNumber = in.readInt();
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public int getCalendarMinute() {
        return calendarMinute;
    }

    public void setCalendarMinute(int calendarMinute) {
        this.calendarMinute = calendarMinute;
    }

    public int getCalendarHours() {
        return calendarHours;
    }

    public void setCalendarHours(int calendarHours) {
        this.calendarHours = calendarHours;
    }

    public int getIntentNumber() {
        return intentNumber;
    }

    public void setIntentNumber(int intentNumber) {
        this.intentNumber = intentNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(notificationTitle);
        dest.writeString(notificationContent);
        dest.writeInt(calendarMinute);
        dest.writeInt(calendarHours);
        dest.writeInt(intentNumber);
    }
}
