package com.ecoapp.app.maslab.ecoapp.pastdata;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.garden.Garden;

import java.io.File;

/**
 * Created by masato on 2016/12/18.
 */

public class PastView extends View implements Runnable, View.OnTouchListener,GameCallback,PastMenu.PastMenuListener{

    public static final int SCENE_MENU = 0;
    public static final int SCENE_GARDEN = 6132;
    public static final int GO_BACK_TO_MAIN = 2;

    private GameCallback fragmentCallBack;
    private GameObjectHandler handler;
    private Garden garden;
    private boolean running;
    private int scene;

    public void setGameCallBack(GameCallback fragmentCallBack){
        this.fragmentCallBack = fragmentCallBack;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(SizeManager.width,SizeManager.height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0, SizeManager.width,SizeManager.height, Paints.background_white);
        handler.render(canvas,scene);
    }

    public PastView(Context context) {
        super(context);
        setOnTouchListener(this);
        handler = new GameObjectHandler();

        PastMenu menu = new PastMenu(handler,getContext());
        menu.setPastMenuListener(this);
        menu.setGameCallback(this);

        scene = SCENE_MENU;
        setRunning(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long lastTimeR = System.nanoTime();
        long now = 0;
        double amountOfTicks = 60.0;
        double targetFPS = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double nsR = 1000000000 / targetFPS;
        double delta = 0;
        double deltaR = 0;
        long timer = System.currentTimeMillis();
        int fps = 0;
        int ticks = 0;
        while(isRunning()){
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                ticks++;
                delta--;
            }

            now = System.nanoTime();
            deltaR += (now - lastTimeR) / nsR;
            lastTimeR = now;
            if(1 < deltaR){
                render();
                fps++;
                deltaR = 0;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //Log.i("info","FPS: " + fps + " ticks: " + ticks);
                fps = 0;
                ticks = 0;
            }
        }
    }

    private void tick(){
        handler.tick(scene);
    }

    private void render(){
        postInvalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        handler.handleEvent(x,y,action,scene);
        return true;
    }

    @Override
    public void gameCallBack(int code) {
        switch (code){
            case GO_BACK_TO_MAIN:
                setRunning(false);
                fragmentCallBack.gameCallBack(0);
                break;
            case Garden.GARDEN_EXHIBIT_END:
                scene = SCENE_MENU;
                handler.removeGameObject(garden);
                garden = null;
                break;
        }

    }

    @Override
    public void showGarden(String yyyyMM) {
        File saveFile = DataManager.getMonthFile(getContext(),yyyyMM);
        garden = new Garden(handler,getContext(),saveFile,true);
        garden.setGameCallBack(this);
        scene = SCENE_GARDEN;
    }
}
