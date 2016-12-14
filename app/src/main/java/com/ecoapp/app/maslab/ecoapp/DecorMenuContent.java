package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/11.
 */

public class DecorMenuContent extends GameObject{

    private Bitmap icon;
    private String id;
    private int size;
    private int howMuch;
    private int bitmapX,bitmapY;
    private float AMCLeafX;
    private float textX;
    private boolean purchasable;
    private DecorMenuContentListener listener;

    public String getId() {
        return id;
    }

    public int getHowMuch() {
        return howMuch;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(!purchasable){
            return;
        }
        if(getX() < x && x < getX() + size){
            if(getY() < y && y < getY() + size){
                listener.DecorMenuContentPressed(this);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        if(purchasable) {
            canvas.drawRect(getX(), getY(), getX() + SizeManager.AMCSize, getY() + SizeManager.AMCSize, Paints.menuContentBack);
        }else{
            canvas.drawRect(getX(), getY(), getX() + SizeManager.AMCSize, getY() + SizeManager.AMCSize, Paints.AMCUnPurchasable);
        }
        float iconX = getX() + bitmapX;
        float iconY = getY() + bitmapY;
        canvas.drawBitmap(icon,iconX,iconY,null);
        canvas.drawBitmap(Bitmaps.AMCleaf,AMCLeafX,getY() + AMCLeafY,null);
        canvas.drawText(Integer.toString(howMuch),textX,getY() + AMCTextY,Paints.AMCHowMuchText);
    }

    @Override
    public void tick() {
        int textWidth = (int) Paints.AMCHowMuchText.measureText(Integer.toString(howMuch));
        AMCLeafX = getX() + size/2 - (textWidth + AMCLeafSize)/2;
        textX = AMCLeafX + AMCLeafSize;
        if(LeafIndicator.leaves < this.howMuch){
            purchasable = false;
        }else{
            purchasable = true;
        }
    }

    public DecorMenuContent(String id,int howMuch,DecorMenuContentListener listener) {
        this.id = id;
        this.howMuch = howMuch;
        this.listener = listener;
        this.size = AMCSize;
        icon = GardenBitmaps.getBitmap("icon" + id);
        setHandleEventScenes(new int[]{DecorMenuContentHandler.SCENE_MAIN});
        bitmapX = size/2 - icon.getWidth()/2;
        bitmapY = size*7/10 - icon.getHeight();
    }

    @Override
    public int compareTo(Object o) {
        DecorMenuContent content = (DecorMenuContent) o;
        int diff = getHowMuch() - content.getHowMuch();
        if(diff<0){
            return -1;
        }else if(0<diff){
            return 1;
        }else {
            return 0;
        }
    }

    public static interface DecorMenuContentListener{
        public void DecorMenuContentPressed(DecorMenuContent content);
    }
}
