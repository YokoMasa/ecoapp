package com.ecoapp.app.maslab.ecoapp.garden;

import android.graphics.Canvas;

import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameDialog;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import com.ecoapp.app.maslab.ecoapp.Texts;
import com.ecoapp.app.maslab.ecoapp.garden.ThemeMenuContent;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/14.
 */

public class ThemeMenu extends GameObject implements
        ThemeMenuContent.ThemeMenuContentListener,GameCallback {

    public static final int SCENE_MAIN = 0;
    public static final int SCENE_SHOWING_DIALOG = 1;

    private ThemeMenuListener listener;
    private GameObjectHandler motherHandler;
    private GameObjectHandler handler;
    private String text;
    private int contentY;
    private int textX,textY;
    private int scene;
    private int chosenTheme;

    public void setThemeMenuListener(ThemeMenuListener listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        handler.handleEvent(x,y,action,scene);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(0,0, SizeManager.width,SizeManager.height, Paints.achievementMenuBack);
        canvas.drawText(text,textX,textY,Paints.menuContentTitle);
        handler.render(canvas,scene);
    }

    @Override
    public void tick() {
        handler.tick(scene);
    }

    public ThemeMenu(GameObjectHandler motherHandler) {
        this.motherHandler = motherHandler;
        text = Texts.getText("theme_menu_title");
        int textWidth = (int) Paints.menuContentTitle.measureText(text);
        textX = width/2 - textWidth/2;
        textY = themeMenuY ;
        contentY = 0;
        motherHandler.addGameObject(this);
        setRenderScenes(new int[]{MainGameView.SCENE_ON_CHOOSE_THEME});
        setTickScenes(new int[]{MainGameView.SCENE_ON_CHOOSE_THEME});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_CHOOSE_THEME});
        handler = new GameObjectHandler();
        setContent();
        GameDialog dialog = new GameDialog(handler,Texts.getText("theme_dialog_text"));
        dialog.setRenderScenes(new int[]{SCENE_SHOWING_DIALOG});
        dialog.setHandleEventScenes(new int[]{SCENE_SHOWING_DIALOG});
        dialog.setGameCallback(this);
        scene = SCENE_MAIN;
    }

    private void setContent(){
        handler.addGameObject(new ThemeMenuContent(Bitmaps.theme1,Garden.THEME_1,this));
        handler.addGameObject(new ThemeMenuContent(Bitmaps.theme2,Garden.THEME_2,this));
        handler.addGameObject(new ThemeMenuContent(Bitmaps.theme3,Garden.THEME_3,this));
        sortContents();
    }

    private void sortContents(){
        int cx = SizeManager.listMenuPaddingX;
        int cy = SizeManager.listMenuPaddingY;
        for(int i = 0;i<handler.getCount();i++){
            int x = cx + (i%3) * (themeMenuContentSize + themeMenuContentGap);
            int y = cy + (i/3) * (themeMenuContentSize + themeMenuContentGap);
            GameObject object = handler.getGameObject(i);
            object.setX(x);
            object.setY(y);
        }
    }

    @Override
    public void contentTouched(int theme) {
        chosenTheme = theme;
        scene = SCENE_SHOWING_DIALOG;
    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case GameDialog.YES:
                if(listener != null) {
                    listener.themeChosen(chosenTheme);
                }
                motherHandler.removeGameObject(this);
                break;
            case GameDialog.NO:
                chosenTheme = -1;
                scene = SCENE_MAIN;
                break;
        }
    }

    public static interface ThemeMenuListener{
        public void themeChosen(int theme);
    }
}
