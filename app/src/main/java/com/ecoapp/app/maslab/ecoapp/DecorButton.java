package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by masato on 2016/12/07.
 */

public class DecorButton extends GameObject {

    private double middleX,middleY;
    private double radious;
    private boolean buttonPressed;
    protected Bitmap bitmap;
    private GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        double distance = Math.sqrt((middleX - x)*(middleX - x) + (middleY - y)*(middleY - y));
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(distance < radious){
                    buttonPressed = true;
                    bitmap = Bitmaps.decorButtonPressed;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(buttonPressed){
                    if(distance < radious){
                        bitmap = Bitmaps.decorButtonPressed;
                    }else{
                        bitmap = Bitmaps.decorButton;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(buttonPressed){
                    if(distance < radious){
                        if(listener != null) {
                            listener.gameCallBack(GameCallback.DECOR_BUTTON_PRESSED);
                        }
                    }
                }
                bitmap = Bitmaps.decorButton;
                buttonPressed = false;

        }
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap,SizeManager.decorButtonX,SizeManager.decorButtonY,null);
    }

    @Override
    public void tick() {

    }

    public DecorButton(GameObjectHandler handler){
        handler.addGameObject(this);
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_MAIN});
        setRenderScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_MENU,MainGameView.SCENE_ON_DECOR_MENU,MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        middleX = SizeManager.decorButtonX + SizeManager.decorButtonSize/2;
        middleY = SizeManager.decorButtonY + SizeManager.decorButtonSize/2;
        radious = SizeManager.decorButtonSize/2;
        bitmap = Bitmaps.decorButton;
    }
}
