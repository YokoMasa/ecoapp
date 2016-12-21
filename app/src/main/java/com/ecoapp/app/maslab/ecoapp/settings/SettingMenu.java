package com.ecoapp.app.maslab.ecoapp.settings;

import android.graphics.Canvas;

import com.ecoapp.app.maslab.ecoapp.BitmapButton;
import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masyu on 2016/12/20.
 */

public class SettingMenu extends GameObject implements GameCallback{

    public static final int MENU_FADED = 27643;

    private static final int APPEARING = 0;
    private static final int SHOWING = 1;
    private static final int FADING = 2;
    private static final int HIDING = 3;

    private final int CROSS_PRESSED = 9999;

    private int state;
    private int menuY;
    private BitmapButton crossButton;
    private GameObjectHandler handler;

    @Override
    public void handleEvent(int x, int y, int action) {
        crossButton.handleEvent(x,y,action);
        handler.handleEvent(x,y,action,0);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.save();
        canvas.translate(0,menuY);
        canvas.drawRect(0,0,SizeManager.width,SizeManager.height, Paints.achievementMenuBack);

        crossButton.render(canvas);
        handler.render(canvas,0);

        canvas.restore();
    }

    @Override
    public void tick() {
        crossButton.tick();
        handler.tick(0);
        if(state == APPEARING){
            menuY -= SizeManager.fadeVel;
            if(menuY < 0){
                menuY = 0;
                state = SHOWING;
            }
        }else if(state == FADING){
            menuY += SizeManager.fadeVel;
            if(SizeManager.height < menuY){
                menuY = SizeManager.height;
                state = HIDING;
                if(listener != null){
                    listener.gameCallBack(MENU_FADED);
                }
            }
        }
    }

    public void show(){
        state = APPEARING;
    }

    public void hide(){
        state = FADING;
    }

    public SettingMenu(GameObjectHandler motherHandler) {
        motherHandler.addGameObject(this);
        setRenderScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        setTickScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        this.handler = new GameObjectHandler();
        crossButton = new BitmapButton(null, SizeManager.crossButtonX,SizeManager.crossButtonY, Bitmaps.crossButton,Bitmaps.crossButton,this,CROSS_PRESSED);
        state = HIDING;
        menuY = SizeManager.height;
    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case CROSS_PRESSED:
                hide();
                break;
        }
    }
}
