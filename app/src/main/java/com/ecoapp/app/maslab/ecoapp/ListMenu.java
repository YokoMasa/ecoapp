package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.view.MotionEvent;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/07.
 */

public abstract class ListMenu extends GameObject implements GameCallback {

    public static final int SCENE_ON_MAIN = 0;
    public static final int SCENE_ON_SHOWING_DIALOG = 1;
    protected int state;
    private final int SHOWING = 0;
    private final int APPEARING = 1;
    private final int FADING = 2;
    private final int HIDING = 3;
    private float rootY;
    private int scene;
    private GameCallback listener;
    private ContentHandler contentHandler;
    protected GameObject handler;

    public void setGameCallback(GameCallback listener){
        this.listener = listener;
    }

    public void setContentHandler(ContentHandler contentHandler){
        this.contentHandler = contentHandler;
        contentHandler.setGameCallBack(this);
    }

    public void setGameObjectHandler(GameObject handler){
        this.handler = handler;
    }

    public int getState() {
        return state;
    }

    public void show(){
        state = APPEARING;
        scene = SCENE_ON_MAIN;
        rootY = height;
    }

    public void fade(){
        state = FADING;
        if (contentHandler != null) {
            contentHandler.shrinkContents();
        }
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(crossButtonX < x && x < crossButtonX + crossButtonSize){
            if(crossButtonY < y && y < crossButtonY + crossButtonSize){
                if(action == MotionEvent.ACTION_DOWN) {
                    fade();
                }
            }
        }
        if(state == SHOWING){
            if(contentHandler != null){
                contentHandler.handleEvent(x,y,action);
            }
            if(handler != null){
                handler.handleEvent(x,y,action);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        int code = canvas.save();
        canvas.translate(0,rootY);
        if(state != HIDING) {
            drawMenuBase(canvas);
        }
        if(contentHandler != null) {
            contentHandler.render(canvas);
        }
        if(handler != null){
            handler.render(canvas);
        }
        canvas.restoreToCount(code);
    }

    private void drawMenuBase(Canvas canvas){
        canvas.drawRect(0,0,SizeManager.width,SizeManager.height,Paints.achievementMenuBack);
        canvas.drawBitmap(Bitmaps.crossButton,crossButtonX,crossButtonY,null);;
    }

    private void drawMenuContent(Canvas canvas){
        if(contentHandler == null){
            return;
        }

    }

    @Override
    public void tick() {
        switch(state){
            case APPEARING:
                rootY -= fadeVel;
                if(rootY < 0){
                    rootY = 0;
                    state = SHOWING;
                }
                break;
            case SHOWING:
                if(contentHandler != null){
                    contentHandler.tick();
                }
                if(handler != null){
                    handler.tick();
                }

                break;
            case FADING:
                rootY += fadeVel;
                if(height < rootY){
                    state = HIDING;
                    if(listener != null){
                        listener.gameCallBack(GameCallback.MENU_FADED);
                    }
                }
                break;
            case HIDING:
                break;
        }

    }

    public ListMenu(GameObjectHandler handler){
        handler.addGameObject(this);
        state = HIDING;
    }

    @Override
    public void gameCallBack(int code) {

    }
}
