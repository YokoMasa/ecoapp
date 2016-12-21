package com.ecoapp.app.maslab.ecoapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by masato on 2016/12/07.
 */

public class Texts {

    public static final int JAPANESE = 0;
    public static final int ENGLISH = 1;
    public static final String TEXT_JP_PATH = "text-jp";
    public static final String TEXT_EN_PATH = "text-en";
    public static int loadedLang;
    private static Map<String,String> texts = new HashMap<String,String>();

    public static String getText(String key){
        return texts.get(key);
    }

    public static void loadTexts(Context context, int language){
        texts.clear();
        loadedLang = language;
        AssetManager assetManager = context.getAssets();
        String path = null;
        InputStream is = null;
        switch(language){
            default:
            case JAPANESE:
                path = TEXT_JP_PATH;
                break;
            case ENGLISH:
                path = TEXT_EN_PATH;
                break;
        }
        BufferedReader bf = null;
        try{
            is = assetManager.open(path);
            bf = new BufferedReader(new InputStreamReader(is));
        }catch(IOException ioe){
            ioe.printStackTrace();
            Toast.makeText(context,"Failed to load texts file.",Toast.LENGTH_SHORT);
        }
        if(bf == null){
            return;
        }
        read(bf,context);
    }

    private static void read(BufferedReader bf,Context context){
        try{
            String line = bf.readLine();
            while(line != null){
                String[] keyAndText = line.split("=",2);
                if(keyAndText[0] != null && keyAndText[1] != null){
                    texts.put(keyAndText[0],keyAndText[1]);
                }
                line = bf.readLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            Toast.makeText(context,"Failed to load texts file.",Toast.LENGTH_SHORT);
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
