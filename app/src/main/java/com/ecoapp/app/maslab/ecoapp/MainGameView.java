package com.ecoapp.app.maslab.ecoapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.garden.EditMenu;
import com.ecoapp.app.maslab.ecoapp.garden.Garden;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;
import com.ecoapp.app.maslab.ecoapp.garden.ThemeMenu;
import com.ecoapp.app.maslab.ecoapp.settings.SettingMenu;

import java.io.File;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;
import static com.ecoapp.app.maslab.ecoapp.Paints.*;

/**
 * Created by masato on 2016/12/06.
 */

public class MainGameView extends View implements
        Runnable,GameCallback ,View.OnTouchListener,DecorMenu.DecorMenuListener,ThemeMenu.ThemeMenuListener,EditMenu.EditMenuListener{

    public static final int SCENE_ON_MAIN = 0;
    public static final int SCENE_ON_MENU = 1;
    public static final int SCENE_ON_DECOR_MENU = 2;
    public static final int SCENE_ON_EDIT_GARDEN = 3;
    public static final int SCENE_ON_CHOOSE_THEME = 4;
    public static final int SCENE_ON_KEEP_EDIT_MENU = 5;
    public static final int SCENE_ON_KEEP_EDIT = 6;
    public static final int SCENE_ON_SETTING_MENU = 7;
    private final int SETTING_PRESSED = 298374;
    private int scene;
    private int theme;
    private boolean running;
    private GameCallback fragmentCallBack;
    private File saveFile;
    private Garden garden;
    private GameObjectHandler handler;
    private BitmapButton settingButton;
    private ListMenu achievementMenu;
    private DecorMenu decorMenu;
    private SettingMenu settingMenu;
    private EditMenu editMenu;

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    public void setFragmentCallBack(GameCallback fragmentCallBack){
        this.fragmentCallBack = fragmentCallBack;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch(theme){
            case 1:
                canvas.drawRect(0,0,width,height,Paints.theme1);
                break;
            default:
            case 2:
                canvas.drawRect(0,0,width,height,Paints.theme2);
                break;
            case 3:
                canvas.drawRect(0,0,width,height,Paints.theme3);
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

        saveFile = DataManager.getMonthFile(getContext(),DataManager.getyyyyMM());
        if(DataManager.getTheme(saveFile) == -1){
            chooseTheme();
        }else{
            theme = DataManager.getTheme(saveFile);
            garden = new Garden(handler,getContext(),saveFile,false);
            garden.setGameCallBack(this);
            scene = SCENE_ON_MAIN;
            setMenues();
            start();
        }
    }

    private void chooseTheme(){
        ThemeMenu themeMenu = new ThemeMenu(handler);
        themeMenu.setThemeMenuListener(this);
        scene = SCENE_ON_CHOOSE_THEME;
        start();
    }

    private void setMenues(){
        settingButton = new BitmapButton(handler,width - leafIndicatorX - crossButtonSize,leafIndicatorY,Bitmaps.settingPressed,Bitmaps.setting,this,SETTING_PRESSED);
        settingButton.setHandleEventScenes(new int[]{SCENE_ON_MAIN});
        achievementMenu = new AchievementMenu(handler);
        achievementMenu.setContentHandler(new GameMenuContentHandler());
        achievementMenu.setGameCallback(this);
        decorMenu = new DecorMenu(handler,theme);
        decorMenu.setGameCallback(this);
        decorMenu.setDecorMenuListener(this);
        settingMenu = new SettingMenu(handler);
        settingMenu.setGameCallback(this);
        editMenu = new EditMenu(handler,garden.getItems(),this);
        editMenu.setEditMenuListener(this);
    }

    private void start(){
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
            case Garden.KEEP_EDIT_START:
                scene = SCENE_ON_KEEP_EDIT_MENU;
                editMenu.show();
                break;
            case Garden.KEEP_EDIT_MENU:
                scene = SCENE_ON_KEEP_EDIT_MENU;
                break;
            case Garden.KEEP_EDIT_END:
                editMenu.fade();
                break;
            case EditMenu.EDIT_MENU_FADED:
                scene = SCENE_ON_MAIN;
                break;
            case SETTING_PRESSED:
                scene = SCENE_ON_SETTING_MENU;
                settingMenu.show();
                break;
            case SettingMenu.MENU_FADED:
                scene = SCENE_ON_MAIN;
                break;
            case SettingMenu.SHOW_PAST_MENU:
                if(fragmentCallBack != null){
                    setRunning(false);
                    fragmentCallBack.gameCallBack(0);
                }
                break;
            case SettingMenu.ENGLISH:
                Texts.loadTexts(getContext(),Texts.ENGLISH);
                restart();
                break;
            case SettingMenu.JAPANESE:
                Texts.loadTexts(getContext(),Texts.JAPANESE);
                restart();
                break;

        }

    }

    private void restart(){
        setRunning(false);
        fragmentCallBack.gameCallBack(1);
    }

    @Override
    public void editStart(String id) {
        scene = SCENE_ON_EDIT_GARDEN;
        GardenItem item = DataManager.getGardenItemInstance(theme,id);
        garden.addEdit(item);
    }

    @Override
    public void keepEditStart(GardenItem item) {
        scene = SCENE_ON_KEEP_EDIT;
        garden.keepEdit(item);
    }

    @Override
    public void themeChosen(int theme) {
        this.theme = theme;
        garden = new Garden(handler,getContext(),saveFile,theme);
        garden.setGameCallBack(this);
        scene = SCENE_ON_MAIN;
        setMenues();
    }
}
