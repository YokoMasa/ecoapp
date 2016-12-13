package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;

/**
 * Created by masato on 2016/12/12.
 */

public class RoundButton extends GameObject {

    private Bitmap normal;
    private Bitmap pressed;
    private Bitmap disabled;
    private Bitmap bitmap;
    private int returnCode;
    private int middleX,middleY;
    private float radius;
    private boolean buttonPressed;
    private boolean buttonDisabled;
    private GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(buttonDisabled){
            return;
        }
        double distance = Math.sqrt((middleX - x)*(middleX - x) + (middleY - y)*(middleY - y));
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(distance < radius){
                    buttonPressed = true;
                    bitmap = pressed;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(buttonPressed){
                    if(distance < radius){
                        bitmap = pressed;
                    }else{
                        bitmap = normal;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(buttonPressed){
                    if(distance < radius){
                        if(listener != null){
                            listener.gameCallBack(returnCode);
                        }
                    }
                }
                bitmap = normal;
                buttonPressed = false;
                break;
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap,getX(),getY(),null);
    }

    @Override
    public void tick() {
        middleX = (int) (getX() + normal.getWidth()/2);
        middleY = (int) (getY() + normal.getHeight()/2);
    }

    public void setButtonDisabled(){
        buttonDisabled = true;
        bitmap = disabled;
    }

    public void setButtonEnabled(){
        buttonDisabled = false;
        bitmap = normal;
    }

    public RoundButton(int x, int y, Bitmap normal, Bitmap pressed, Bitmap disabled, int returnCode,GameObjectHandler handler) {
        if(handler != null){
            handler.addGameObject(this);
        }
        setX(x);
        setY(y);
        this.returnCode = returnCode;
        this.normal = normal;
        this.pressed = pressed;
        this.disabled = disabled;
        this.radius = normal.getWidth()/2;
        bitmap = normal;
    }
}
