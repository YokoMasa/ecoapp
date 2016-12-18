package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by masyu on 2016/12/15.
 */

public class BitmapButton extends GameObject {

    private Bitmap bitmap;
    private Bitmap pressed;
    private Bitmap unPressed;
    private boolean buttonPressed;
    private int width,height;
    private int code;

    @Override
    public void handleEvent(int x, int y, int action) {
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(getX() < x && x < getX() + width){
                    if(getY() < y && y < getY() + height){
                        buttonPressed = true;
                        bitmap = pressed;
                        //Log.i("info","pressed");
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(buttonPressed){
                    if(getX() < x && x < getX() + width){
                        if(getY() < y && y < getY() + height){
                            bitmap = pressed;
                        }else{
                            bitmap = unPressed;
                        }
                    }else{
                        bitmap = unPressed;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(buttonPressed){
                    if(getX() < x && x < getX() + width){
                        if(getY() < y && y < getY() + height){
                            if(listener != null){
                                listener.gameCallBack(code);
                            }
                        }
                    }
                }
                buttonPressed = false;
                bitmap = unPressed;
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap,getX(),getY(),null);
    }

    @Override
    public void tick() {

    }

    public BitmapButton(GameObjectHandler handler,int x,int y,Bitmap pressed,Bitmap unPressed,GameCallback listener,int code) {
        if(handler != null) {
            handler.addGameObject(this);
        }
        this.code = code;
        this.pressed = pressed;
        this.unPressed = unPressed;
        this.width = unPressed.getWidth();
        this.height = unPressed.getHeight();
        setGameCallback(listener);
        setX(x);
        setY(y);
        bitmap = unPressed;
    }
}
