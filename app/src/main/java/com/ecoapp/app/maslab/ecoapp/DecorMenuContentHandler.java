package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.util.Size;

import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;


/**
 * Created by masato on 2016/12/11.
 */

public class DecorMenuContentHandler extends GameObject implements DecorMenuContent.DecorMenuContentListener{

    public static final int SCENE_MAIN = 0;
    public static final int SCENE_SHOWING_DIALOG = 1;
    private int baseX;
    private int baseY;
    private int gap;
    private int scene;
    private int itemCount;
    private int theme;
    private boolean scrollable;
    private GameObjectHandler handler;
    private GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        handler.handleEvent(x,y,action,scene);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.saveLayer(baseX,baseY,baseX + SizeManager.AMCTotalWidth,baseY + SizeManager.AMCTotalWidth,null,Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        handler.render(canvas,scene);
        canvas.restore();
    }

    @Override
    public void tick() {
        handler.tick(scene);
        int totalHeight = (int) itemCount/3 * gap - SizeManager.AMCgap;
        if(SizeManager.listMaxContentHeight < totalHeight){
            scrollable = true;
        }
    }

    public DecorMenuContentHandler(int theme) {
        this.theme = theme;
        baseX = SizeManager.listMenuPaddingX;
        baseY = SizeManager.listMenuPaddingY;
        handler = new GameObjectHandler();
        itemCount = GardenBitmaps.getItemCount();
        gap = SizeManager.AMCSize + SizeManager.AMCgap;
        scene = SCENE_MAIN;
        for(int i = 0;i<itemCount;i++){
            String id = Integer.toString(i + 1);
            int howMuch = DataManager.getGardenItemCost(theme,id);
            DecorMenuContent content = new DecorMenuContent(id,howMuch,this);
            content.setX(baseX + (i%3)*gap);
            content.setY(baseY + (int)i/3 * gap);
            handler.addGameObject(content);
        }
    }

    @Override
    public void DecorMenuContentPressed(DecorMenuContent content) {
        listener.gameCallBack(Integer.parseInt(content.getId()));
    }
}
