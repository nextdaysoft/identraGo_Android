package com.project.identranaccess.database;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.identranaccess.model.RegisterDataModel;

public class Utility {


    public static void addPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static String getPreferences(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getString(key, value);
    }


    public static void addPreferences(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static int getPreferences(Context context, String key, int defaut) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getInt(key, defaut);
    }


    public static void addPreferences(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public static Boolean getPreferences(Context context, String key, boolean defaut) {
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaut);
    }


    public static void addUserId(Context context, String uid, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putString(uid, value);
        editor.apply();
        Log.e(TAG, "addUserId: "+uid);
        Log.e(TAG, "addUserId: "+value);
    }

    public static String getUserId(Context context, String uid){
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getString(uid, "");

    }

    public static void addObject(Context context, String object, RegisterDataModel registerDataModel) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE).edit();
        editor.putString(object, registerDataModel.getUserId());
        editor.apply();
    }
    public static String getObject(Context context, String object){
        SharedPreferences prefs = context.getSharedPreferences("Preferences_", Context.MODE_PRIVATE);
        return prefs.getString(object, "");

    }
}
