package com.ecoapp.app.maslab.ecoapp.pastdata;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masato on 2016/12/18.
 */

public class PastMenuContent extends GameObject {

    private int textX,textY;
    private int code;
    private String text;
    private GameCallback callback;

    @Override
    public void handleEvent(int x, int y, int action) {
        if(getX() < x && x < getX() + SizeManager.menuContentWidth){
            if(getY() < y && y < getY() + SizeManager.menuContentHeight){
                if(callback != null){
                    callback.gameCallBack(code);
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX() + SizeManager.menuContentWidth,getY() + SizeManager.menuContentHeight,Paints.menuContentBack);
        canvas.drawText(text,textX,textY,Paints.menuContentTitle);
    }

    @Override
    public void tick() {
        textY = (int)getY() + SizeManager.menuContentHeight/2 + SizeManager.menuTitleSize/2;
    }

    public PastMenuContent(int x,int y,String text, int code, GameCallback callback) {
        setX(x);setY(y);
        this.callback = callback;
        this.code = code;
        this.text = text;
        measure();
    }

    private void measure(){
        int textWidth = (int) Paints.menuContentTitle.measureText(text);
        textX = (int)getX() + SizeManager.menuContentWidth/2 - textWidth/2;
    }
}
