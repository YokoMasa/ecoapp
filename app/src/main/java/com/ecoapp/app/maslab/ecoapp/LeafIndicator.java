package com.ecoapp.app.maslab.ecoapp;

import android.content.Context;
import android.graphics.Canvas;

import com.ecoapp.app.maslab.ecoapp.settings.SettingPrefUtil;

/**
 * Created by masato on 2016/12/09.
 */

public class LeafIndicator extends GameObject {

    public static int leaves;
    public static int leavesShowing;
    private int saveTick;
    private float textX,textY;
    private Context context;

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
        if(saveTick != 0){
            saveTick--;
        }
        if(leavesShowing < leaves){
            leavesShowing ++;
            if(saveTick == 0){
                SettingPrefUtil.saveLeaves(context,leaves);
                saveTick = 200;
            }
        }else if(leaves < leavesShowing){
            leavesShowing --;
            if(saveTick == 0){
                SettingPrefUtil.saveLeaves(context,leaves);
                saveTick = 200;
            }
        }
    }

    public LeafIndicator(GameObjectHandler handler,Context context){
        this.context = context;
        setTickScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_DECOR_MENU});
        //setRenderScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_MENU,MainGameView.SCENE_ON_DECOR_MENU,MainGameView.SCENE_ON_KEEP_EDIT_MENU,
                //MainGameView.SCENE_ON_SETTING_MENU,MainGameView.SCENE_ON_EDIT_GARDEN,MainGameView.SCENE_ON_KEEP_EDIT});
        setX(SizeManager.leafIndicatorX);
        setY(SizeManager.leafIndicatorY);
        textX = getX() + Bitmaps.leaf.getWidth();
        textY = getY() + Bitmaps.leaf.getHeight()/2 + SizeManager.textSize/2;
        handler.addGameObject(this);
        leaves = SettingPrefUtil.getLeaves(context);
        leavesShowing = leaves;
    }
}
