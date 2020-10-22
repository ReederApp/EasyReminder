package com.iamcodder.easyreminder.data.local.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private final SharedPref sharedPref;
    private final SharedPreferences sharedPreferences;

    public SharedPrefHelper(Context mContext) {
        sharedPref = new SharedPref();
        sharedPreferences = sharedPref.getSharedPreferences(mContext);
    }

    //Example setting value
    public void setValue(String key, Object value) {
        sharedPref.putSomeValue(sharedPreferences, key, value);
    }

    //Example getting value
    public <T> Object getValue(String key, T classType) {
        return sharedPref.getSomeValue(sharedPreferences, key, classType);
    }

    public void clearAllShared() {
        sharedPref.clearAllValue(sharedPreferences);
    }

}
