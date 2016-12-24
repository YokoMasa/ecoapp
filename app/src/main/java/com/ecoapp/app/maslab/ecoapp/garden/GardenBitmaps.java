package com.ecoapp.app.maslab.ecoapp.garden;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by masato on 2016/12/11.
 */

public class GardenBitmaps {

    private static Map<String,Bitmap> bitmaps = new HashMap<String,Bitmap>();
    private static int loadedTheme;
    private static float minimize;
    private static boolean isLoaded;

    public static int getItemCount(){
        return bitmaps.size()/2;
    }

    public static Bitmap getBitmap(String key){
        if(isLoaded) {
            return bitmaps.get(key);
        }else{
            return null;
        }
    }

    public static void loadBitmaps(Context context, int theme){
        bitmaps.clear();
        loadedTheme = theme;
        AssetManager assetManager = context.getAssets();
        String themeDirName = "theme" + Integer.toString(theme);
        int themeItemCount = 0;
        InputStream is = null;

        try{
            String[] itemFileName = assetManager.list(themeDirName);
            themeItemCount = itemFileName.length;
            //Log.i("info",Integer.toString(themeItemCount));
            for(int i = 0;i<themeItemCount;i++){
                String path = themeDirName + "/" + itemFileName[i];
                Log.i("info",path);
                is = assetManager.open(path);
                Bitmap raw = BitmapFactory.decodeStream(is);
                resizeBitmap(raw,i);
                if(is != null){
                    is.close();
                }
            }
        }catch(IOException ioe){
            Toast.makeText(context,"Failed to load Bitmaps",Toast.LENGTH_SHORT).show();
            ioe.printStackTrace();
            isLoaded = false;
        }finally {
            if(is != null){
                try{
                    is.close();
                    //assetManager.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        isLoaded = true;
    }

    private static void resizeBitmap(Bitmap raw,int count){
        String bitmapKey = null;
        if((count + 1) < 10){
            bitmapKey = "0" + Integer.toString(count + 1);
        }else{
            bitmapKey = Integer.toString(count + 1);
        }
        //Log.i("info",bitmapKey);
        String iconKey = "icon" + bitmapKey;
        int rawWidth = raw.getWidth();
        int rawHeight = raw.getHeight();
        int dstWidth,dstHeight;

        if(rawHeight < rawWidth){
            dstWidth = SizeManager.AMCBitmapSize;
            dstHeight = dstWidth * rawHeight/rawWidth;
        }else{
            dstHeight = SizeManager.AMCBitmapSize;
            dstWidth = dstHeight * rawWidth/rawHeight;
        }
        Bitmap icon = Bitmap.createScaledBitmap(raw,dstWidth,dstHeight,false);

        bitmaps.put(iconKey,icon);
        if(count == 0) {
            if (rawHeight < rawWidth) {
                dstWidth = SizeManager.gardenItemSize;
                dstHeight = dstWidth * rawHeight / rawWidth;
                minimize = (float)dstWidth / (float)rawWidth;
            } else {
                dstHeight = SizeManager.gardenItemSize;
                dstWidth = dstHeight * rawWidth / rawHeight;
                minimize = (float)dstHeight / (float)rawHeight;
            }
        }else{
            if (rawHeight < rawWidth) {
                dstWidth = (int) (rawWidth * minimize);
                dstHeight = dstWidth * rawHeight / rawWidth;
            } else {
                dstHeight = (int) (rawHeight * minimize);
                dstWidth = dstHeight * rawWidth / rawHeight;
            }
        }
        Bitmap mainBitmap = Bitmap.createScaledBitmap(raw,dstWidth,dstHeight,false);
        bitmaps.put(bitmapKey,mainBitmap);
    }

}
