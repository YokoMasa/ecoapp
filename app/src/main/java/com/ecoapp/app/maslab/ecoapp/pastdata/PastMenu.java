package com.ecoapp.app.maslab.ecoapp.pastdata;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.BitmapButton;
import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import com.ecoapp.app.maslab.ecoapp.Texts;
import com.ecoapp.app.maslab.ecoapp.garden.DataManager;

import java.io.File;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.editMenuContentGap;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.width;

/**
 * Created by masato on 2016/12/18.
 */

public class PastMenu extends GameObject implements GameCallback{

    private final int TOUCH_TIME = 10;
    private final int YEAR = 0;
    private final int MONTH = 1;
    private final int BACK_PRESSED = 9999;
    private int state;
    private int totalContentHeight;
    private int touchingTime;
    private int firstTouchX,firstTouchY;
    private int previousTouchX,previousTouchY;
    private int scrollY;
    private boolean scrolling;
    private boolean scrollable;
    private String chosenYear;
    private Context context;
    private GameObjectHandler handler;
    private PastMenuListener pastMenuListener;
    private BitmapButton back;

    public void setPastMenuListener(PastMenuListener pastMenuListener){
        this.pastMenuListener = pastMenuListener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        back.handleEvent(x,y,action);
       switch(action){
           case MotionEvent.ACTION_DOWN:
               firstTouchX = x;
               firstTouchY = y;
               touchingTime= 0;
               break;
           case MotionEvent.ACTION_MOVE:
               if(!scrolling){
                   touchingTime++;
                   float distance = (float) Math.sqrt((firstTouchX - x) * (firstTouchX - x) + (firstTouchY - y) * (firstTouchY - y));
                   if(TOUCH_TIME < touchingTime || SizeManager.tapDistance < distance){
                       scrolling = true;
                   }
                   previousTouchX = x;
                   previousTouchY = y;
               }else{
                   if(scrollable) {
                       int deltaY = previousTouchY - y;
                       if((scrollY - deltaY) < SizeManager.listMaxContentHeight - totalContentHeight){
                           scrollY = SizeManager.listMaxContentHeight - totalContentHeight;
                       }else if(0 < (scrollY - deltaY)){
                           scrollY = 0;
                       }else{
                           scrollY -= deltaY;
                       }
                   }
                   previousTouchY = y;
               }
               break;
           case MotionEvent.ACTION_UP:
               if(!scrolling){
                   int modY = y - scrollY;
                   handler.handleEvent(x,modY,action,0);
                   //Log.i("info","touch" + x + "," + modY);
               }
               touchingTime = 0;
               scrolling = false;
               break;

       }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(0,0,SizeManager.width,SizeManager.height, Paints.achievementMenuBack);

        back.render(canvas);

        canvas.saveLayer(SizeManager.listMenuPaddingX,SizeManager.listMenuPaddingY,SizeManager.width - SizeManager.listMenuPaddingX,SizeManager.height-SizeManager.listMenuPaddingY,null,Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(0,scrollY);
        handler.render(canvas,0);
        canvas.restore();
    }

    @Override
    public void tick() {
        back.tick();
        handler.tick(0);
    }

    public PastMenu(GameObjectHandler motherHandler,Context context) {
        motherHandler.addGameObject(this);
        setHandleEventScenes(new int[]{PastView.SCENE_MENU});
        setRenderScenes(new int[]{PastView.SCENE_MENU});
        setTickScenes(new int[]{PastView.SCENE_MENU});
        this.context = context;
        handler = new GameObjectHandler();
        setYearContent();
    }

    private void setYearContent(){
        handler.removeAll();
        File data = DataManager.getDataDir(context);
        String[] years = data.list();
        int x = SizeManager.listMenuPaddingX;
        int y = SizeManager.listMenuPaddingY;
        int gap = SizeManager.menuContentHeight + SizeManager.listContentGap;
        for(int i = 0;i<years.length;i++){
            String text = years[i];
            handler.addGameObject(new PastMenuContent(x,y,text,Integer.parseInt(years[i]),this));
            y += gap;
        }
        int size = handler.getCount();
        totalContentHeight = size * SizeManager.menuContentHeight + (size - 1) * SizeManager.listContentGap;
        if(SizeManager.listMaxContentHeight < totalContentHeight){
            scrollable = true;
        }else{
            scrollable = false;
            scrollY = 0;
        }
        back = new BitmapButton(null,SizeManager.listMenuPaddingX/5,SizeManager.listMenuPaddingY/5, Bitmaps.backPressed,Bitmaps.back,this,BACK_PRESSED);
        state = YEAR;
    }

    private void setMonthContent(String year){
        handler.removeAll();
        File data = DataManager.getYearDir(context,year);
        String[] months = data.list();
        int x = SizeManager.listMenuPaddingX;
        int y = SizeManager.listMenuPaddingY;
        int gap = SizeManager.menuContentHeight + SizeManager.listContentGap;
        for(int i = 0;i<months.length;i++){
            String text = Texts.getText(months[i]);
            handler.addGameObject(new PastMenuContent(x,y,text,Integer.parseInt(months[i]),this));
            y += gap;
        }
        int size = handler.getCount();
        totalContentHeight = size * SizeManager.menuContentHeight + (size - 1) * SizeManager.listContentGap;
        if(SizeManager.listMaxContentHeight < totalContentHeight){
            scrollable = true;
        }else{
            scrollable = false;
            scrollY = 0;
        }
        back = new BitmapButton(null,SizeManager.listMenuPaddingX/5,SizeManager.listMenuPaddingY/5, Bitmaps.backPressed,Bitmaps.back,this,BACK_PRESSED);
        state = MONTH;
    }

    @Override
    public void gameCallBack(int code) {
        if(code == BACK_PRESSED){
            if(state == YEAR){
                listener.gameCallBack(PastView.GO_BACK_TO_MAIN);
            }else{
                chosenYear = null;
                setYearContent();
            }
        }else {
            switch (state) {
                case YEAR:
                    chosenYear = Integer.toString(code);
                    setMonthContent(Integer.toString(code));
                    break;
                case MONTH:
                    String monthString = null;
                    if(code < 10){
                        monthString = '0' + Integer.toString(code);
                    }else{
                        monthString = Integer.toString(code);
                    }

                    if (pastMenuListener != null) {
                        pastMenuListener.showGarden(chosenYear + monthString);
                    }
                    break;
            }
        }
    }

    public static interface PastMenuListener{
        public void showGarden(String yyyyMM);
    }
}
