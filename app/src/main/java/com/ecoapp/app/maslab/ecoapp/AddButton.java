package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by masato on 2016/12/07.
 */

public class AddButton extends GameObject {

    private double middleX,middleY;
    private double radious;
    private boolean buttonPressed;
    protected Bitmap normal;
    protected Bitmap pressed;
    protected Bitmap bitmap;
    protected int callbackCode;
    private GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        double distance = Math.sqrt((middleX - x)*(middleX - x) + (middleY - y)*(middleY - y));
        //Log.i("info",Integer.toString(x));
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(distance < radious){
                    buttonPressed = true;
                    bitmap = pressed;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(buttonPressed){
                    if(distance < radious){
                        bitmap = pressed;
                    }else{
                        bitmap = normal;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(buttonPressed){
                    if(distance < radious){
                        if(listener != null) {
                            listener.gameCallBack(callbackCode);
                            Log.i("info","add pressed");
                        }
                    }
                }
                bitmap = normal;
                buttonPressed = false;

        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap,SizeManager.addButtonX,SizeManager.addButtonY,null);
    }

    @Override
    public void tick() {

    }

    public AddButton(GameObjectHandler handler){
        handler.addGameObject(this);
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_MAIN});
        setRenderScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_MENU,MainGameView.SCENE_ON_DECOR_MENU,MainGameView.SCENE_ON_KEEP_EDIT_MENU,MainGameView.SCENE_ON_SETTING_MENU});
        middleX = SizeManager.addButtonX + SizeManager.addButtonSize/2;
        middleY = SizeManager.addButtonY + SizeManager.addButtonSize/2;
        radious = SizeManager.addButtonSize / 2;
        bitmap = Bitmaps.addButton;
        normal = Bitmaps.addButton;
        pressed = Bitmaps.addButtonPressed;
        callbackCode = GameCallback.ADD_BUTTON_PRESSED;
    }

}
