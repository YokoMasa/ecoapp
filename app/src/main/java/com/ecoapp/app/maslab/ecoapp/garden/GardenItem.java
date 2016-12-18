package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masato on 2016/12/10.
 */

public abstract class GardenItem extends GameObject implements  Comparable{

    protected String id;
    protected int cWidth,cHeight;
    protected int gapX,gapY;
    protected int bitmapWidth,bitmapHeight;
    protected float bitmapX,bitmapY;
    protected Bitmap mainBitmap;
    protected Bitmap icon;

    private boolean touched;
    private boolean editing;

    public String getId() {
        return id;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int getBitmapHeight() { return bitmapHeight; }

    public float getBitmapY() {
        return bitmapY;
    }

    public float getBitmapX() {

        return bitmapX;
    }

    public void setEditing(boolean editing){
        this.editing = editing;
    }

    @Override
    public void render(Canvas canvas) {
        convertIntoBitmapXY();
        canvas.drawBitmap(mainBitmap,bitmapX,bitmapY,null);
        if(editing){
            canvas.drawPath(Paints.getEditMark(this),Paints.button_B_pressed);
        }
        //canvas.drawRect(cx,cy,cx + cWidth,cy + cHeight, Paints.AMCUnPurchasable);
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(bitmapX < x && x < bitmapX + bitmapWidth){
                    if(bitmapY < y && y < bitmapY + bitmapHeight){
                        touched = true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(touched){
                    setCx(x);
                    setCy(y);
                }
                break;
            case MotionEvent.ACTION_UP:
                touched = false;
                break;
        }
    }

    public void convertIntoBitmapXY(){
        bitmapX = cx - gapX;
        bitmapY = cy - gapY;
    }

    public void setBitmapDimen(){
        bitmapWidth = mainBitmap.getWidth();
        bitmapHeight = mainBitmap.getHeight();
    }

    public int getcHeight() {
        return cHeight;
    }

    public int getcWidth() {

        return cWidth;
    }

    public GardenItem(){
        setHandleEventScenes(new int[]{GameObject.DONT_HANDLE});
    }

    public void setActiveHandleEvent(int[] activeScene){
        setHandleEventScenes(activeScene);
    }

    @Override
    public int compareTo(Object o) {
        GardenItem i = (GardenItem) o;
        float result = getCy() - i.getCy();
        if(result < 0){
            return -1;
        }else if(result == 0){
            return 0;
        }else {
            return 1;
        }
    }
}
