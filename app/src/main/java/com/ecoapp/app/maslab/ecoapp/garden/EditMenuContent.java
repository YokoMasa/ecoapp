package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masato on 2016/12/16.
 */

public class EditMenuContent extends GameObject {

    private EditMenuContentListener editMenuContentListener;
    private GardenItem item;
    private Bitmap icon;
    private int iconPaddingX;
    private int iconPaddingY;

    public void setEditMenuContentListener(EditMenuContentListener editMenuContentListener){
        this.editMenuContentListener = editMenuContentListener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(getX() < x && x < getX() + SizeManager.editMenuContentSize){
            if(getY() < y && y < getY() + SizeManager.editMenuContentSize){
                if(editMenuContentListener != null){
                    editMenuContentListener.contentPressed(item);
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX()+ SizeManager.editMenuContentSize,getY()+SizeManager.editMenuContentSize, Paints.menuContentBack);
        canvas.drawBitmap(icon,getX()+iconPaddingX,getY()+iconPaddingY,null);
    }

    @Override
    public void tick() {

    }

    public EditMenuContent(int x,int y,GardenItem item) {
        setX(x);setY(y);
        String key = "icon" + item.getId();
        this.icon = GardenBitmaps.getBitmap(key);
        this.item = item;
        iconPaddingX = SizeManager.editMenuContentSize/2 - icon.getWidth()/2;
        iconPaddingY = SizeManager.editMenuContentSize/2 - icon.getHeight()/2;
    }

    public static interface EditMenuContentListener{
        public void contentPressed(GardenItem item);
    }
}
