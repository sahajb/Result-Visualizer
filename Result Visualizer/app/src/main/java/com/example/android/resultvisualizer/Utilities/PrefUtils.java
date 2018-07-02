package com.example.android.resultvisualizer.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class PrefUtils {

    private PrefUtils(){
    }

    synchronized public static void setHighlight(Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("highlight",!getHighlight(context));
        editor.apply();
    }

    synchronized public static void setTouch(Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("touch",!getTouch(context));
        editor.apply();
    }

    private static boolean getHighlight(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("highlight",true);
    }

    private static boolean getTouch(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("touch",true);
    }

}
