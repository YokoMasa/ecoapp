package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;


/**
 * Created by masato on 2016/12/11.
 */

public class DecorMenuContentHandler extends GameObject implements DecorMenuContent.DecorMenuContentListener{

    public static final int SCENE_MAIN = 0;
    public static final int SCENE_SHOWING_DIALOG = 1;
    private int baseX;
    private int baseY;
    private int gap;
    private int scene;
    private int itemCount;
    private int theme;
    private int totalHeight;
    private int firstTouchX,firstTouchY;
    private int previousTouchY;
    private int touchingTime;
    private int rootY;
    private boolean scrolling;
    private boolean scrollable;
    private GameObjectHandler handler;
    private GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        switch(action){
            case MotionEvent.ACTION_DOWN:
                firstTouchX = x;
                firstTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                touchingTime++;
                int distance = (int) Math.sqrt((firstTouchX - x)*(firstTouchX - x) + (firstTouchY - y)*(firstTouchY - y));
                if(!scrolling) {
                    if (10 < touchingTime || SizeManager.tapDistance < distance) {
                        scrolling = true;
                        previousTouchY = y;
                    }
                }else{
                    if(scrollable) {
                        int deltaY = previousTouchY - y;
                        if ((rootY - deltaY) < (SizeManager.listMaxContentHeight - totalHeight)) {
                            rootY = SizeManager.listMaxContentHeight - totalHeight;
                        } else if (0 < (rootY - deltaY)) {
                            rootY = 0;
                        } else {
                            rootY -= deltaY;
                        }
                        previousTouchY = y;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!scrolling){
                    handler.handleEvent(x,y - rootY,action,scene);
                }
                touchingTime = 0;
                scrolling = false;
                previousTouchY = 0;
                break;
        }
        //handler.handleEvent(x,y,action,scene);
    }

    @Override
    public void render(Canvas canvas) {
        int code = canvas.save();
        canvas.saveLayer(baseX,baseY,baseX + SizeManager.AMCTotalWidth,baseY + SizeManager.AMCTotalHeight,null,Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(0,rootY);
        handler.render(canvas,scene);
        canvas.restoreToCount(code);
    }

    @Override
    public void tick() {
        handler.tick(scene);
        if(itemCount%3 == 0){
            totalHeight = (int) itemCount/3 * gap - SizeManager.AMCgap;
        }else{
            totalHeight = ((int) itemCount/3 + 1) * gap - SizeManager.AMCgap;
        }
        if(SizeManager.listMaxContentHeight < totalHeight){
            scrollable = true;
        }
    }

    public DecorMenuContentHandler(int theme) {
        this.theme = theme;
        baseX = SizeManager.listMenuPaddingX;
        baseY = SizeManager.listMenuPaddingY;
        handler = new GameObjectHandler();
        itemCount = GardenBitmaps.getItemCount();
        gap = SizeManager.AMCSize + SizeManager.AMCgap;
        rootY = 0;
        scene = SCENE_MAIN;
        for(int i = 0;i<itemCount;i++){
            String id = Integer.toString(i + 1);
            if(id.length() == 1){
                id = "0" + id;
            }
            //Log.i("info",id);
            int howMuch = DataManager.getGardenItemCost(theme,id);
            DecorMenuContent content = new DecorMenuContent(id,howMuch,this);
            //content.setX(baseX + (i%3)*gap);
            //content.setY(baseY + (int)i/3 * gap);
            handler.addGameObject(content);
        }
        //handler.sort();
        sort();
    }

    private void sort(){
        handler.sort();
        for(int i = 0;i<handler.getCount();i++){
            DecorMenuContent content = (DecorMenuContent) handler.getGameObject(i);
            content.setX(baseX + (i%3)*gap);
            content.setY(baseY + (int)i/3 * gap);
        }
    }

    @Override
    public void DecorMenuContentPressed(DecorMenuContent content) {
        listener.gameCallBack(Integer.parseInt(content.getId()));
    }
}
