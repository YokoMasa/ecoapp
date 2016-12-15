package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;

/**
 * Created by masato on 2016/12/09.
 */

public class LeafIndicator extends GameObject {

    public static int leaves;
    public static int leavesShowing;
    private float textX,textY;

    @Override
    public void handleEvent(int x, int y, int action) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(Bitmaps.leaf,getX(),getY(),null);
        canvas.drawText(Integer.toString(leavesShowing),textX,textY,Paints.text_black);
    }

    @Override
    public void tick() {
        if(leavesShowing < leaves){
            leavesShowing ++;
        }else if(leaves < leavesShowing){
            leavesShowing --;
        }
    }

    public LeafIndicator(GameObjectHandler handler){
        setTickScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_DECOR_MENU});
        setX(SizeManager.leafIndicatorX);
        setY(SizeManager.leafIndicatorY);
        textX = getX() + Bitmaps.leaf.getWidth();
        textY = getY() + Bitmaps.leaf.getHeight()/2 + SizeManager.textSize/2;
        handler.addGameObject(this);
        leaves = 100;
        leavesShowing = leaves;
    }
}
