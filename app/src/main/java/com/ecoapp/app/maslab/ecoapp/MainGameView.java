package com.ecoapp.app.maslab.ecoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.garden.Garden;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

import java.io.File;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;
import static com.ecoapp.app.maslab.ecoapp.Paints.*;

/**
 * Created by masato on 2016/12/06.
 */

public class MainGameView extends View implements
        Runnable,GameCallback ,View.OnTouchListener,DecorMenu.DecorMenuListener{

    public static final int SCENE_ON_MAIN = 0;
    public static final int SCENE_ON_MENU = 1;
    public static final int SCENE_ON_DECOR_MENU = 2;
    public static final int SCENE_ON_EDIT_GARDEN = 3;
    private int scene;
    private int theme;
    private boolean running;
    private Garden garden;
    private GameObjectHandler handler;
    private ListMenu achievementMenu;
    private DecorMenu decorMenu;


    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch(theme){
            case 1:
                canvas.drawRect(0,0,width,height,Paints.theme1);
                break;
            default:
                canvas.drawRect(0,0,width,height,background_white);
                break;
        }
        handler.render(canvas,scene);
    }

    public MainGameView(Context context) {
        super(context);
        setOnTouchListener(this);
        handler = new GameObjectHandler();
        AddButton addButton = new AddButton(handler);
        addButton.setGameCallBack(this);
        DecorButton decorButton = new DecorButton(handler);
        decorButton.setGameCallBack(this);
        new LeafIndicator(handler);

        File saveFile = DataManager.getMonthFile(getContext(),DataManager.getyyyyMM());
        if(DataManager.getTheme(saveFile) == -1){
            theme = Garden.THEME_1;
            garden = new Garden(handler,getContext(),saveFile,Garden.THEME_1);
            garden.setGameCallBack(this);
        }else{
            theme = DataManager.getTheme(saveFile);
            garden = new Garden(handler,getContext(),saveFile);
            garden.setGameCallBack(this);
        }

        achievementMenu = new AchievementMenu(handler);
        achievementMenu.setContentHandler(new GameMenuContentHandler());
        achievementMenu.setGameCallback(this);
        decorMenu = new DecorMenu(handler,theme);
        decorMenu.setGameCallback(this);
        decorMenu.setDecorMenuListener(this);
        setRunning(true);
        scene = SCENE_ON_MAIN;
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

    private void render(){
        postInvalidate();
    }

    private void tick(){
        handler.tick(scene);
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
        switch(code){
            case GameCallback.ADD_BUTTON_PRESSED:
                scene = SCENE_ON_MENU;
                achievementMenu.show();
                break;
            case GameCallback.DECOR_BUTTON_PRESSED:
                scene = SCENE_ON_DECOR_MENU;
                decorMenu.show();
                break;
            case GameCallback.MENU_FADED:
                scene = SCENE_ON_MAIN;
                break;
            case Garden.EDIT_END:
                scene = SCENE_ON_DECOR_MENU;
                break;
        }

    }

    @Override
    public void editStart(String id) {
        scene = SCENE_ON_EDIT_GARDEN;
        GardenItem item = DataManager.getGardenItemInstance(theme,id);
        garden.addEdit(item);
    }
}