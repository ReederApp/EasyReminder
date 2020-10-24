package com.iamcodder.easyreminder.data.local.repository;

import android.content.Context;
import android.widget.Toast;

import com.iamcodder.easyreminder.data.local.model.InfoModel;
import com.iamcodder.easyreminder.data.local.sharedPref.SharedPrefHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class MainRepository {

    private final SharedPrefHelper sharedPrefHelper;

    public MainRepository(SharedPrefHelper sharedPrefHelper) {
        this.sharedPrefHelper = sharedPrefHelper;
    }

    public ArrayList getSharedData(Context mContext) {
        String alarms = (String) sharedPrefHelper.getValue("alarms", String.class);
        String[] alarmsArray = alarms.split(",");
        if (alarmsArray[0].equals("")) {
            Toast.makeText(mContext.getApplicationContext(), "Alarm yok", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        } else {
            ArrayList<InfoModel> modelList = new ArrayList<>();
            for (String s : alarmsArray) {
                String title = (String) sharedPrefHelper.getValue(s + "title", String.class);
                String content = (String) sharedPrefHelper.getValue(s + "content", String.class);
                int hours = (int) sharedPrefHelper.getValue(s + "hours", Integer.class);
                int minutes = (int) sharedPrefHelper.getValue(s + "minutes", Integer.class);
                InfoModel tempModel = new InfoModel(title, content, minutes, hours, Integer.parseInt(s));
                modelList.add(tempModel);
            }
            return modelList;
        }
    }

    public void setSharedData(int intentNumber, String title, String content, Calendar calendar) {
        String alarms = (String) sharedPrefHelper.getValue("alarms", String.class);
        if (alarms != null && !alarms.isEmpty()) {
            alarms = alarms + "," + intentNumber;
        } else alarms = intentNumber + "";
        sharedPrefHelper.setValue("alarms", alarms);
        sharedPrefHelper.setValue(intentNumber + "title", title);
        sharedPrefHelper.setValue(intentNumber + "content", content);
        sharedPrefHelper.setValue(intentNumber + "hours", calendar.get(Calendar.HOUR_OF_DAY));
        sharedPrefHelper.setValue(intentNumber + "minutes", calendar.get(Calendar.MINUTE));
    }

    public void deleteSharedData(InfoModel infoModel) {
        String value = (String) sharedPrefHelper.getValue("alarms", String.class);
        String[] alarmsList = value.split(",");
        String resultString = "";
        if (!alarmsList[0].equals("")) {
            for (String s : alarmsList) {
                String intentNumber = "" + infoModel.getIntentNumber();
                if (!s.equals(intentNumber)) {
                    resultString = s + "," + resultString;
                }
            }
        }

        sharedPrefHelper.setValue("alarms", resultString.toString());
        sharedPrefHelper.deleteValue(infoModel.getIntentNumber() + "title");
        sharedPrefHelper.deleteValue(infoModel.getIntentNumber() + "content");
        sharedPrefHelper.deleteValue(infoModel.getIntentNumber() + "hours");
        sharedPrefHelper.deleteValue(infoModel.getIntentNumber() + "minutes");
    }

}
