package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by masato on 2016/12/07.
 */

public class GameButton extends GameObject {

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    private GameCallback listener;
    private boolean buttonPressed;
    private int code;
    private Paint unpressed;
    private Paint pressed;
    private Paint paint;
    private int width,height;
    private float textX,textY;
    private String text;

    public void setGameCallback(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(getX() < x && x < getX() + width){
                    if(getY() < y && y < getY() + height){
                        buttonPressed = true;
                        paint = pressed;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(buttonPressed) {
                    if (getX() < x && x < getX() + width) {
                        if (getY() < y && y < getY() + height) {
                            paint = pressed;
                        }
                        paint = unpressed;
                    }
                    paint = unpressed;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(buttonPressed) {
                    if (getX() < x && x < getX() + width) {
                        if (getY() < y && y < getY() + height) {
                            if(listener != null){
                                listener.gameCallBack(code);
                            }
                        }
                    }
                }
                paint = unpressed;
                buttonPressed = false;
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX() + width,getY() + height,paint);
        if(text != null){
            canvas.drawText(text,textX,textY,Paints.button_text);
        }
    }

    @Override
    public void tick() {

    }

    public void setText(String text){
        this.text = text;
    }

    public void setColor(int color){
        switch (color){
            default:
            case RED:
                unpressed = Paints.button_R;
                pressed = Paints.button_R_pressed;
                break;
            case GREEN:
                unpressed = Paints.button_G;
                pressed = Paints.button_G_pressed;
                break;
            case BLUE:
                unpressed = Paints.button_B;
                pressed = Paints.button_B_pressed;
                break;
        }
    }

    public GameButton(int x,int y,int width,int height,int code,GameObjectHandler handler){
        setX(x);setY(y);
        if(handler != null) {
            handler.addGameObject(this);
        }
        this.width = width;
        this.height = height;
        this.code = code;
        textX = getX() + width/2;
        textY = getY() + SizeManager.textSize/2 + height/2;
        setColor(RED);
        paint = unpressed;
    }

}
