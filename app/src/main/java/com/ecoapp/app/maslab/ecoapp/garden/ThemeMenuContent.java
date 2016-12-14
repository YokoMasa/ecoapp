package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import com.ecoapp.app.maslab.ecoapp.Texts;
import com.ecoapp.app.maslab.ecoapp.garden.ThemeMenu;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/14.
 */

public class ThemeMenuContent extends GameObject {

    private ThemeMenuContentListener listener;
    private Bitmap thumbNail;
    private String text;
    private int textWidth;
    private int theme;
    private int bitmapX,bitmapY;
    private int textX,textY;

    public void setThememenuContentListener(ThemeMenuContentListener listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(getX() < x && x < getX() + themeMenuContentSize){
            if(getY() < y && y < getY() + themeMenuContentSize){
                if(listener != null){
                    listener.contentTouched(theme);
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX()+ SizeManager.themeMenuContentSize,getY()+SizeManager.themeMenuContentSize, Paints.menuContentBack);
        canvas.drawBitmap(thumbNail,bitmapX,bitmapY,null);
        canvas.drawText(text,textX,textY,Paints.themeMenuContentText);
    }

    @Override
    public void tick() {
        bitmapX = (int) getX() + themeMenuContentPadding*5/2;
        textX = (int) (getX() + themeMenuContentSize/2 - textWidth/2);
    }

    public ThemeMenuContent(Bitmap thumbNail,int theme,int x,int y,ThemeMenuContentListener listener) {
        setHandleEventScenes(new int[]{ThemeMenu.SCENE_MAIN});
        this.listener = listener;
        this.theme = theme;
        this.thumbNail = thumbNail;
        this.text = Texts.getText("theme" + Integer.toString(theme));
        bitmapY = y + themeMenuContentPadding;
        textY = y + themeMenuContentTextY;
        textWidth = (int) Paints.themeMenuContentText.measureText(text);
        setX(x);
        setY(y);
    }

    public static interface ThemeMenuContentListener{
        public void contentTouched(int theme);
    }
}
