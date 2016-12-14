package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;

/**
 * Created by masato on 2016/12/07.
 */

public abstract class GameObject {

    public static final int DONT_HANDLE = 325849;
    protected float x,y;
    protected float cx,cy;
    protected int[] renderScenes;
    protected int[] tickScenes;
    protected  int[] handleEventScenes;

    public  void setRenderScenes(int[] renderScenes){
        this.renderScenes = renderScenes;
    }

    public  void setTickScenes(int[] tickScenes){
        this.tickScenes = tickScenes;
    }

    public  void setHandleEventScenes(int[] handleEventScenes){
        this.handleEventScenes = handleEventScenes;
    }

    public abstract void render(Canvas canvas);

    public abstract void tick();

    public abstract void handleEvent(int x,int y,int action);

    public boolean isRenderActive(int scene){
        if(renderScenes == null){
            return true;
        }
        for(int i = 0;i<renderScenes.length;i++){
            if(renderScenes[i] == scene){
                return true;
            }
        }
        return false;
    }

    public boolean isTickActive(int scene){
        if(tickScenes == null){
            return true;
        }
        for(int i = 0;i<tickScenes.length;i++){
            if(tickScenes[i] == scene){
                return true;
            }
        }
        return false;
    }

    public boolean isHandleEventActive(int scene){
        if(handleEventScenes == null){
            return true;
        }
        for(int i = 0;i<handleEventScenes.length;i++){
            if(handleEventScenes[i] == scene){
                return true;
            }
        }
        return false;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getCy() {

        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getCx() {

        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public GameObject(){

    }
}
