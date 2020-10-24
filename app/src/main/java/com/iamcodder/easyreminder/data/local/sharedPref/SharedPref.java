package com.iamcodder.easyreminder.data.local.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

class SharedPref {

    private final String SHARED_PREF_NAME = "Reminders";

    public SharedPreferences getSharedPreferences(Context mContext) {
        return mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public <T> void putSomeValue(SharedPreferences pref, String key, T value) {

        String getTypeName = value.getClass().getName();

        if (getTypeName.equals(String.class.getName())) {
            pref.edit().putString(key, (String) value).apply();
        } else if (getTypeName.equals(Integer.class.getName())) {
            pref.edit().putInt(key, (Integer) value).apply();
        } else if (getTypeName.equals(Boolean.class.getName())) {
            pref.edit().putBoolean(key, (Boolean) value).apply();
        } else if (getTypeName.equals(Float.class.getName())) {
            pref.edit().putFloat(key, (Float) value).apply();
        } else if (getTypeName.equals(Long.class.getName())) {
            pref.edit().putLong(key, (Long) value).apply();
        } else {
            new Throwable("Shared Preference Get Value Error");
        }
    }

    public <T> Object getSomeValue(SharedPreferences pref, String key, T variableType) {

        if (variableType.equals(String.class)) {
            return pref.getString(key, "");
        }
        if (variableType.equals(Integer.class)) {
            return pref.getInt(key, 0);
        } else if (variableType.equals(Boolean.class)) {
            return pref.getBoolean(key, false);
        } else if (variableType.equals(Float.class)) {
            return pref.getFloat(key, 0);
        } else if (variableType.equals(Long.class)) {
            return pref.getLong(key, 0);
        } else {
            new Throwable("Shared Preference Get Value Error");
            return false;
        }
    }

    public void deleteSomeValue(SharedPreferences pref, String key) {
        pref.edit().remove(key).apply();
    }

    public void clearAllValue(SharedPreferences pref) {
        pref.edit().clear().apply();
    }

}
