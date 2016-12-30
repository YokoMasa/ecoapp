package com.ecoapp.app.maslab.ecoapp.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.ecoapp.app.maslab.ecoapp.Texts;

/**
 * Created by masato on 2016/12/30.
 */

public class SettingPrefUtil {

    private static final String PREF_NAME = "settings";
    private static final String LEAVES = "leaves";
    private static final String SOUND = "sound";
    private static final String LANG = "lang";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    public static void saveLeaves(Context context,int count){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(LEAVES,count);
        editor.commit();
    }

    public static int getLeaves(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(LEAVES,0);
    }

    public static void setSoundOnOff(Context context,boolean onOff){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(SOUND,onOff);
        editor.commit();
    }

    public static boolean isSoundOn(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(SOUND,true);
    }

    public static void setLang(Context context,int lang){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(LANG,lang);
        editor.commit();
    }

    public static int getLang(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(LANG, Texts.JAPANESE);
    }
}
