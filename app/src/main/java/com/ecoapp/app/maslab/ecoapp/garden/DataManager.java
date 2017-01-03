package com.ecoapp.app.maslab.ecoapp.garden;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by masato on 2016/12/11.
 */

public class DataManager {

    public static final String THEME1_FQCN = "com.ecoapp.app.maslab.ecoapp.garden.theme1";
    public static final String THEME2_FQCN = "com.ecoapp.app.maslab.ecoapp.garden.theme2";
    public static final String THEME3_FQCN = "com.ecoapp.app.maslab.ecoapp.garden.theme3";
    public static final String THEME4_FQCN = "com.ecoapp.app.maslab.ecoapp.garden.theme4";
    public static final String THEME5_FQCN = "com.ecoapp.app.maslab.ecoapp.garden.theme5";
    public static final int END_APP = 999999999;

    public static String BR = System.getProperty("line.separator");

    private static GameCallback gameEnder;

    public static void setGameCallBack(GameCallback gameCallBack){
        gameEnder = gameCallBack;
    }

    private static void showErrorToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static String getyyyyMM(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(date);
    }

    public static File getDataDir(Context context){
        if(context.getExternalFilesDir(null) == null){
            Toast.makeText(context,"Couldn't access to files. Ending app.",Toast.LENGTH_SHORT).show();
            if(gameEnder != null){
                gameEnder.gameCallBack(END_APP);
            }
            return null;
        }
        File root = new File(context.getExternalFilesDir(null),"Data");
        if(!root.exists()){
            if(!root.mkdir()){
                Toast.makeText(context,"failed to create dataDir.",Toast.LENGTH_SHORT).show();
            }else{
                Log.i("info","data directory created");
            }
        }
        if(root.exists() && root.isDirectory()){
            return root;
        }else{
            return null;
        }
    }

    public static File getYearDir(Context context,String yyyy){
        File dataDir = getDataDir(context);
        if(dataDir == null){
            return null;
        }
        File yearDir = new File(dataDir,yyyy);
        if(!yearDir.exists()){
            if(!yearDir.mkdir()){
                Toast.makeText(context,"failed to create yearDir.",Toast.LENGTH_SHORT).show();
                if(gameEnder != null){
                    gameEnder.gameCallBack(END_APP);
                }
            }else{
                Log.i("info","year directory created");
            }
        }
        if(yearDir.exists() && yearDir.isDirectory()){
            return yearDir;
        }else{
            return null;
        }
    }

    public static File getMonthFile(Context context, String yyyyMM){
        String yyyy = yyyyMM.substring(0,4);
        String MM = yyyyMM.substring(4,6);
        File yearDir = getYearDir(context, yyyy);
        if(yearDir == null){
            return null;
        }

        File monthFile = new File(yearDir,MM);
        if(!monthFile.exists()){
            try {
                monthFile.createNewFile();
                Log.i("info","month file created");
            }catch(IOException ioe){
                Toast.makeText(context,"Failed to get save file.",Toast.LENGTH_SHORT).show();
                if(gameEnder != null){
                    gameEnder.gameCallBack(END_APP);
                }
                ioe.printStackTrace();
            }
        }
        if(monthFile.exists() && monthFile.isFile()){
            return monthFile;
        }else{
            return null;
        }
    }

    public static int getTheme(File data){
        if(data == null){
            return -1;
        }
        FileReader fr = null;
        char[] themeChar = new char[1];
        int result = 0;
        try{
            fr = new FileReader(data);
            result = fr.read(themeChar);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if(fr != null){
                try{
                    fr.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        if(result == -1){
            return -1;
        }
        Log.i("info","getTheme: " + Character.toString(themeChar[0]));
        return Integer.parseInt(Character.toString(themeChar[0]));
    }

    public static void saveTheme(File data,int theme){
        if(data == null) {
            return;
        }
        FileWriter fw = null;
        try{
            fw = new FileWriter(data);
            fw.write(Integer.toString(theme));
            fw.flush();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if(fw != null){
                try{
                    fw.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void saveGarden(List<GardenItem> gardenData,File savePath,int theme){
        FileWriter fw = null;
        try{
            fw = new FileWriter(savePath);
            fw.write(Integer.toString(theme));

            for(int i = 0;i<gardenData.size();i++){
                String id = gardenData.get(i).getId();
                int cxI = (int) gardenData.get(i).getCx();
                int cyI = (int) gardenData.get(i).getCy() + SizeManager.AdHeight/2;
                String cx = Integer.toString(cxI);
                String cy = Integer.toString(cyI);
                String saveLine = BR + id + "," + cx + "," + cy;
                fw.write(saveLine);
            }

            fw.flush();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if(fw != null){
                try{
                    fw.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<GardenItem> loadGardenData(File savePath){
        ArrayList<GardenItem> gardenData = new ArrayList<GardenItem>();
        int theme = getTheme(savePath);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(savePath));
            reader.readLine();
            String dataLine = reader.readLine();
            while(dataLine != null){
                String[] data = dataLine.split(",");
                String id = data[0];
                String cx = data[1];
                String cy = data[2];
                GardenItem item = getGardenItemInstance(theme,id);
                if(item != null){
                    item.setCx(Integer.parseInt(cx));
                    item.setCy(Integer.parseInt(cy) - SizeManager.AdHeight/2);
                    gardenData.add(item);
                }
                dataLine = reader.readLine();
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return gardenData;
    }

    public static GardenItem getGardenItemInstance(int theme,String id){
        if(id.startsWith("0")){
            id = id.substring(1);
        }
        String itemFQCN = null;
        switch(theme){
            case 1:
                itemFQCN = THEME1_FQCN + "." + "Item" + id;
                break;
            case 2:
                itemFQCN = THEME2_FQCN + "." + "Item" + id;
                break;
            case 3:
                itemFQCN = THEME3_FQCN + "." + "Item" + id;
                break;
            case 4:
                itemFQCN = THEME4_FQCN + "." + "Item" + id;
                break;
            case 5:
                itemFQCN = THEME5_FQCN + "." + "Item" + id;
                break;
        }
        Class<?> itemClazz = null;
        try{
            itemClazz = Class.forName(itemFQCN);
            if(itemClazz != null){
                return (GardenItem) itemClazz.newInstance();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static int getGardenItemCost(int theme,String id){
        if(id.startsWith("0")){
            id = id.substring(1);
        }
        String itemFQCN = null;
        switch(theme){
            case 1:
                itemFQCN = THEME1_FQCN + "." + "Item" + id;
                break;
            case 2:
                itemFQCN = THEME2_FQCN + "." + "Item" + id;
                break;
            case 3:
                itemFQCN = THEME3_FQCN + "." + "Item" + id;
                break;
            case 4:
                itemFQCN = THEME4_FQCN + "." + "Item" + id;
                break;
            case 5:
                itemFQCN = THEME5_FQCN + "." + "Item" + id;
                break;
        }
        Class<?> itemClazz = null;
        try{
            itemClazz = Class.forName(itemFQCN);
            if(itemClazz != null){
                Field howMuch = itemClazz.getField("howMuch");
                return howMuch.getInt(null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }


}
