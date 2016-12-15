package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Canvas;

import com.ecoapp.app.maslab.ecoapp.BitmapButton;
import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import static  com.ecoapp.app.maslab.ecoapp.SizeManager.*;

import java.util.List;

/**
 * Created by masyu on 2016/12/15.
 */

public class EditMenu extends GameObject implements GameCallback{


    public static final int EDIT_MENU_FADED = 629;

    private final int CROSS_BUTTON_PRESSED = 235;
    private final int STATE_APPEARING = 0;
    private final int STATE_FADING = 1;
    private final int STATE_SHOWING = 2;
    private final int STATE_HIDING = 3;
    private int state;
    private int menuY;
    private BitmapButton crossButton;
    private List<GardenItem> items;
    private GameObjectHandler motherHandler;
    private GameObjectHandler handler;

    @Override
    public void handleEvent(int x, int y, int action) {
        handler.handleEvent(x,y,action,0);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.translate(0,menuY);
        canvas.drawRect(0,editMenuY,width,height, Paints.achievementMenuBack);
        handler.render(canvas,0);
        canvas.restore();
    }

    @Override
    public void tick() {
        switch(state){
            case STATE_APPEARING:
                menuY -= editMenuVel;
                if(menuY < 0){
                    menuY = 0;
                    state = STATE_SHOWING;
                }
                break;
            case STATE_FADING:
                menuY += editMenuVel;
                if(editMenuHeight < menuY){
                    menuY = editMenuHeight;
                    state = STATE_HIDING;
                    if(listener != null){
                        listener.gameCallBack(EDIT_MENU_FADED);
                    }
                }
                break;
        }
    }

    public void show(){
        state = STATE_APPEARING;
    }

    public void fade(){
        state = STATE_FADING;
    }

    public EditMenu(GameObjectHandler motherHandler,List<GardenItem> items,GameCallback listener) {
        setGameCallback(listener);
        motherHandler.addGameObject(this);
        this.handler = new GameObjectHandler();
        setRenderScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        setTickScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_KEEP_EDIT_MENU});
        this.motherHandler = motherHandler;
        this.items = items;
        setContent();
        state = STATE_HIDING;
        menuY = SizeManager.editMenuHeight;
    }

    private void setContent(){
        crossButton = new BitmapButton(handler,width-editMenuContentGap,editMenuY, Bitmaps.editMenuCrossButton,Bitmaps.editMenuCrossButton,this,CROSS_BUTTON_PRESSED);

    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case CROSS_BUTTON_PRESSED:
                fade();
                break;
        }
    }
}
