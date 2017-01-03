package com.ecoapp.app.maslab.ecoapp.settings;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.BitmapButton;
import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameDialog;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.MainActivity;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;
import com.ecoapp.app.maslab.ecoapp.Texts;

/**
 * Created by masyu on 2016/12/20.
 */

public class SettingMenu extends GameObject implements GameCallback, RadioButtonList.RadioButtonListListener{

    public static final int MENU_FADED = 27643;
    public static final int SHOW_PAST_MENU = 11503;
    public static final int JAPANESE = 551823;
    public static final int ENGLISH = 18902;

    private static final int APPEARING = 0;
    private static final int SHOWING = 1;
    private static final int FADING = 2;
    private static final int HIDING = 3;

    private static final int SCENE_MAIN = 5;
    private static final int SCENE_SHOWING_DIALOG = 4;

    private final int CROSS_PRESSED = 9999;
    private final int PAST_PRESSED = 3275;
    private final int PURCHASE_PRESSED = 5653;

    private int state;
    private int scene;
    private int menuY;
    private int totalContentHeight;
    private int maxScroll;
    private int touchingTime;
    private int firstTouchX,firstTouchY;
    private int previousX,previousY;
    private int contentY;
    private boolean scrollable;
    private boolean scrolling;
    private BitmapButton crossButton;
    private GameObjectHandler handler;
    private GameObjectHandler dialogHandler;
    private RadioButtonList sound;
    private RadioButtonList lang;
    private Context context;

    @Override
    public void handleEvent(int x, int y, int action) {
        crossButton.handleEvent(x,y,action);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                scrolling = false;
                firstTouchX = x;
                firstTouchY = y;
                previousX = x;
                previousY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                touchingTime++;
                int distance = (int) Math.sqrt((firstTouchX - x) * (firstTouchX - x) + (firstTouchY - y) * (firstTouchY - y));
                if (distance < SizeManager.tapDistance) {
                    if(touchingTime < 10){
                        scrolling = false;
                    }else{
                        scrolling = true;
                    }
                }else{
                    scrolling = true;
                }
                if(scrolling) {
                    if(scrollable) {
                        int deltaY = previousY - y;
                        if ((contentY - deltaY) < -maxScroll) {
                            contentY = -maxScroll;
                        } else if (0 < (contentY - deltaY)) {
                            contentY = 0;
                        } else {
                            contentY -= deltaY;
                        }
                        previousY = y;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!scrolling){
                    int modY = y - contentY;
                    handler.handleEvent(x,modY,action,scene);
                }
                touchingTime = 0;
                break;
        }
        dialogHandler.handleEvent(x,y,action,scene);
    }

    @Override
    public void render(Canvas canvas) {
        int code1 = canvas.save();
        canvas.translate(0,menuY);
        canvas.drawRect(0,0,SizeManager.width,SizeManager.height, Paints.achievementMenuBack);

        crossButton.render(canvas);

        int code2 = canvas.saveLayer(SizeManager.listMenuPaddingX,SizeManager.listMenuPaddingY,SizeManager.listMenuPaddingX + SizeManager.menuContentWidth,SizeManager.height - SizeManager.listMenuPaddingY,null,Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(0,contentY);
        handler.render(canvas,scene);
        canvas.restoreToCount(code2);
        dialogHandler.render(canvas,scene);
        canvas.restoreToCount(code1);
    }

    @Override
    public void tick() {
        crossButton.tick();
        handler.tick(scene);
        dialogHandler.tick(scene);
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
        int Y = SizeManager.listMenuPaddingY;
        int total = 0;
        for(int i = 0;i<handler.getCount();i++){
            GameObject object = handler.getGameObject(i);
            total += object.getHeight() + SizeManager.listContentGap;
            object.setY(Y);
            Y += object.getHeight() + SizeManager.listContentGap;
        }
        totalContentHeight = total - SizeManager.listContentGap;
        if((SizeManager.height - SizeManager.listMenuPaddingY*2) < totalContentHeight){
            scrollable = true;
            maxScroll = totalContentHeight - SizeManager.height + SizeManager.listMenuPaddingY*2;
        }else{
            scrollable = false;
            contentY = 0;
        }

    }

    public void show(){
        state = APPEARING;
    }

    public void hide(){
        state = FADING;
    }

    public SettingMenu(GameObjectHandler motherHandler,Context context) {
        this.context = context;
        motherHandler.addGameObject(this);
        setRenderScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        setTickScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_SETTING_MENU});
        this.handler = new GameObjectHandler();
        this.dialogHandler = new GameObjectHandler();
        crossButton = new BitmapButton(null, SizeManager.crossButtonX,SizeManager.crossButtonY, Bitmaps.crossButton,Bitmaps.crossButton,this,CROSS_PRESSED);
        state = HIDING;
        scene = SCENE_MAIN;
        menuY = SizeManager.height;
        contentY = 0;
        setContent();
    }

    private void setContent(){
        SettingMenuContent content = new SettingMenuContent(handler, Texts.getText("past_gardens"));
        content.setX(SizeManager.listMenuPaddingX);
        content.useAsAButton(this,PAST_PRESSED);
        content.setHandleEventScenes(new int[]{SCENE_MAIN});
        setSoundContent();
        setLangContent();
        /*
        SettingMenuContent purchase = new SettingMenuContent(handler,Texts.getText("purchase"));
        purchase.setX(SizeManager.listMenuPaddingX);
        purchase.useAsAButton(this,PURCHASE_PRESSED);
        purchase.setHandleEventScenes(new int[]{SCENE_MAIN});
        */
        selectButtons();

        GameDialog dialog = new GameDialog(dialogHandler,Texts.getText("purchase_dialog"));
        dialog.setGameCallback(this);
        dialog.setRenderScenes(new int[]{SCENE_SHOWING_DIALOG});
        dialog.setTickScenes(new int[]{SCENE_SHOWING_DIALOG});
        dialog.setHandleEventScenes(new int[]{SCENE_SHOWING_DIALOG});
    }

    private void setSoundContent(){
        SettingMenuContent test = new SettingMenuContent(handler,"BGM");
        test.setX(SizeManager.listMenuPaddingX);
        sound = new RadioButtonList(null,2);
        sound.setRadioButtonListListener(this);
        test.addContentText("ON",SizeManager.menuContentWidth/3,SizeManager.menuContentHeight/5 + SizeManager.menuContentTextSize);
        test.addContentText("OFF",SizeManager.menuContentWidth/3,SizeManager.menuContentHeight/5 + SizeManager.menuContentTextSize
                                                                   + SizeManager.radioButtonSize + SizeManager.radioButtonGap);
        test.addGameObject(sound,SizeManager.menuContentWidth * 5/6,SizeManager.menuContentHeight/5);
        test.setExtendable(true);
        test.setHandleEventScenes(new int[]{SCENE_MAIN});
    }

    private void setLangContent(){
        SettingMenuContent language = new SettingMenuContent(handler,"言語（Language）");
        language.setX(SizeManager.listMenuPaddingX);
        lang = new RadioButtonList(null,2);
        lang.setRadioButtonListListener(this);
        language.addContentText("日本語",SizeManager.menuContentWidth/3,SizeManager.menuContentHeight/5 + SizeManager.menuContentTextSize);
        language.addContentText("ENGLISH",SizeManager.menuContentWidth/3,SizeManager.menuContentHeight/5 + SizeManager.menuContentTextSize
                + SizeManager.radioButtonSize + SizeManager.radioButtonGap);
        language.addGameObject(lang,SizeManager.menuContentWidth * 5/6,SizeManager.menuContentHeight/5);
        language.setExtendable(true);
        language.setHandleEventScenes(new int[]{SCENE_MAIN});
    }

    private void selectButtons(){
        if(SettingPrefUtil.isSoundOn(context)){
            sound.selectButton(0);
        }else{
            sound.selectButton(1);
        }

        switch(SettingPrefUtil.getLang(context)){
            case Texts.ENGLISH:
                lang.selectButton(1);
                break;
            case Texts.JAPANESE:
                lang.selectButton(0);
                break;
        }
    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case CROSS_PRESSED:
                hide();
                break;
            case PAST_PRESSED:
                if(listener!= null){
                    listener.gameCallBack(SHOW_PAST_MENU);
                }
                break;
            case PURCHASE_PRESSED:
                scene = SCENE_SHOWING_DIALOG;
                break;
            case GameDialog.YES:
                scene = SCENE_MAIN;
                break;
            case GameDialog.NO:
                scene = SCENE_MAIN;
                break;
        }
    }

    @Override
    public void radioButtonChosen(RadioButtonList radioButtonList, int code) {
        if(radioButtonList == lang){
            //Log.i("info","chosen");
            switch(code){
                case 0:
                    if(Texts.loadedLang != Texts.JAPANESE) {
                        listener.gameCallBack(JAPANESE);
                        SettingPrefUtil.setLang(context,Texts.JAPANESE);
                    }
                    break;
                case 1:
                    if(Texts.loadedLang != Texts.ENGLISH){
                        listener.gameCallBack(ENGLISH);
                        SettingPrefUtil.setLang(context,Texts.ENGLISH);
                    }
                    break;
            }
        }else if(radioButtonList == sound){
            switch(code){
                case 0:
                    SettingPrefUtil.setSoundOnOff(context,true);
                    MainActivity.startBGM();
                    break;
                case 1:
                    SettingPrefUtil.setSoundOnOff(context,false);
                    MainActivity.stopBGM();
                    break;
            }

        }
    }
}
