package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.BitmapButton;
import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import static  com.ecoapp.app.maslab.ecoapp.SizeManager.*;

import java.util.List;

/**
 * Created by masyu on 2016/12/15.
 */

public class EditMenu extends GameObject implements EditMenuContent.EditMenuContentListener{


    public static final int EDIT_MENU_FADED = 629;

    private final int STATE_APPEARING = 0;
    private final int STATE_FADING = 1;
    private final int STATE_SHOWING = 2;
    private final int STATE_HIDING = 3;
    private final int TOUCH_TIME = 10;
    private int touchDistance = width/50;
    private int state;
    private int menuY;
    private int totalWidth;
    private int scrollX;
    private int firstTouchX,firstTouchY;
    private int previousTouchX,previousTouchY;
    private int touchingTime;
    private int itemCount;
    private boolean scrollable;
    private boolean scrolling;
    private List<GardenItem> items;
    private EditMenuListener editMenuListener;
    private GameObjectHandler motherHandler;
    private GameObjectHandler handler;

    public void setEditMenuListener(EditMenuListener editMenuListener){
        this.editMenuListener = editMenuListener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(!(editMenuY < y && y < height)){
            scrolling = false;
            return ;
        }
        switch(action){
            case MotionEvent.ACTION_DOWN:
                    firstTouchX = x;
                    firstTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if(!scrolling) {
                    touchingTime++;
                    float distance = (float) Math.sqrt((firstTouchX - x) * (firstTouchX - x) + (firstTouchY - y) * (firstTouchY - y));
                    if (TOUCH_TIME < touchingTime || touchDistance < distance) {
                        scrolling = true;
                        previousTouchX = x;
                        previousTouchY = y;
                    }
                }else{
                    if(scrollable) {
                        //Log.i("info","iosdfa");
                        int deltaX = previousTouchX - x;
                        if (0 < (scrollX - deltaX)) {
                            scrollX = 0;
                        }else if((scrollX - deltaX) < -(totalWidth - width + editMenuContentGap*2)){
                            scrollX = -(totalWidth - width + editMenuContentGap*2);
                        }else{
                            scrollX -= deltaX;
                        }
                        previousTouchX = x;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                float distance = (float) Math.sqrt((firstTouchX - x) * (firstTouchX - x) + (firstTouchY - y) * (firstTouchY - y));
                if(!scrolling){
                    int modX = x - scrollX;
                    handler.handleEvent(modX,y,action,0);
                }
                scrolling = false;
                touchingTime = 0;
        }
    }

    @Override
    public void render(Canvas canvas) {
        int code = canvas.save();
        canvas.translate(0,menuY);
        canvas.drawRect(0,editMenuY,width,height, Paints.achievementMenuBack);
        canvas.translate(scrollX,0);
        handler.render(canvas,0);
        canvas.restoreToCount(code);
    }

    @Override
    public void tick() {
        if(itemCount != items.size()){
            setContent();
        }
        switch(state){
            case STATE_APPEARING:
                menuY -= editMenuVel;
                if(menuY < 0){
                    menuY = 0;
                    state = STATE_SHOWING;
                }
                break;
            case STATE_FADING:
                menuY += editMenuVel;
                if(editMenuHeight < menuY){
                    menuY = editMenuHeight;
                    state = STATE_HIDING;
                    if(listener != null){
                        listener.gameCallBack(EDIT_MENU_FADED);
                    }
                }
                break;
            case STATE_SHOWING:
                totalWidth = items.size() * editMenuContentSize + (items.size()-1) * editMenuContentGap;
                if(width < totalWidth){
                    scrollable = true;
                }else{
                    scrollable = false;
                    scrollX = 0;
                }
                break;
        }
    }

    public void show(){
        setContent();
        state = STATE_APPEARING;
    }

    public void fade(){
        state = STATE_FADING;
    }

    public EditMenu(GameObjectHandler motherHandler,List<GardenItem> items,GameCallback listener) {
        setGameCallback(listener);
        motherHandler.addGameObject(this);
        this.handler = new GameObjectHandler();
        setRenderScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        setTickScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        this.motherHandler = motherHandler;
        this.items = items;
        setContent();
        state = STATE_HIDING;
        menuY = SizeManager.editMenuHeight;
    }

    private void setContent(){
        handler.removeAll();
        int contentX = editMenuContentGap;
        int contentY = editMenuY + editMenuContentGap;
        int contentGap = editMenuContentSize + editMenuContentGap;

        for(int i = 0;i<items.size();i++){
            EditMenuContent content = new EditMenuContent(contentX,contentY,items.get(i));
            content.setEditMenuContentListener(this);
            handler.addGameObject(content);
            contentX += contentGap;
        }

        itemCount = items.size();
    }

    @Override
    public void contentPressed(GardenItem item) {
        if(editMenuListener != null){
            editMenuListener.keepEditStart(item);
        }
    }

    public static interface EditMenuListener{
        public void keepEditStart(GardenItem item);
    }
}
