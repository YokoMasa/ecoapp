package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.listMaxContentHeight;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.tapDistance;

/**
 * Created by masato on 2016/12/08.
 */

public class GameMenuContentHandler extends ContentHandler implements GameCallback,GameMenuContent.GameMenuContentCallback{

    public static final int FADE = 190;
    private final int SCENE_MAIN = 0;
    private final int SCENE_SHOWING_DIALOG = 1;
    private int scene;
    private int leavesDelta;
    private int firstTouchY;
    private int previousY;
    private int contentY;
    private int ticks;
    private int totalContentsHeight;
    private int totalContentsYMin;
    private boolean scrollable;
    private boolean scrolling;
    private GameObjectHandler handler;

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);
        handler.render(canvas,scene);
    }

    @Override
    protected void renderContents(Canvas canvas) {
        int renderY = contentY;
        for (int i = 0; i < contents.size(); i++) {
            GameMenuContent content = (GameMenuContent) contents.get(i);
            content.setY(renderY);
            content.render(canvas);
            renderY += content.getHeight() + listContentGap;
        }
    }

    @Override
    public void tick() {
        int contentHeight = 0;
        for(int i = 0;i<contents.size();i++){
            GameMenuContent content = (GameMenuContent) contents.get(i);
            contentHeight += content.getHeight();
            contentHeight += listContentGap;
        }
        contentHeight -= listContentGap;
        this.totalContentsHeight = contentHeight;
        if(listMaxContentHeight < totalContentsHeight){
            totalContentsYMin = listMaxContentHeight - totalContentsHeight;
            scrollable = true;
        }else{
            scrollable = false;
            contentY = 0;
        }
        handler.tick(scene);
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        x -= SizeManager.listMenuPaddingX;
        y -= SizeManager.listMenuPaddingY;
        if(scene == SCENE_MAIN) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    firstTouchY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!scrolling) {
                        int distanceY = firstTouchY - y;
                        if (distanceY < 0) {
                            distanceY *= -1;
                        }
                        if (tapDistance < distanceY) {
                            scrolling = true;
                            previousY = y;
                        }
                        ticks++;
                    } else {
                        int dY = previousY - y;
                        scroll(dY);
                        previousY = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (ticks < 10 && !scrolling) {
                        clicked(x, y, action);
                    }
                    scrolling = false;
                    previousY = 0;
                    firstTouchY = 0;
                    ticks = 0;
                    break;
            }
        }
        x += SizeManager.listMenuPaddingX;
        y += SizeManager.listMenuPaddingY;
        handler.handleEvent(x,y,action,scene);
    }

    private void scroll(int dY){
        int check = 0;
        if(scrollable){
            if(dY < 0){
                //down
                if(0 <= contentY){
                    contentY = 0;
                }else {
                    check = contentY;
                    check -= dY;
                    if(check <= contentY) {
                        contentY = 0;
                    }else{
                        contentY -= dY;
                    }
                }
            }else{
                //up
                if(contentY <= totalContentsYMin){
                    contentY = totalContentsYMin;
                }else {
                    check = contentY;
                    check -= dY;
                    if(check <= totalContentsYMin) {
                        contentY = totalContentsYMin;
                    }else{
                        contentY -= dY;
                    }
                }
            }
        }
    }

    @Override
    public void shrinkContents() {
        for(int i = 0;i<contents.size();i++){
            GameMenuContent content = (GameMenuContent) contents.get(i);
            content.shrink();
        }
    }

    private void clicked(int x, int y, int action){
        for(int i = 0;i<contents.size();i++){
            contents.get(i).handleEvent(x,y,action);
        }
    }

    @Override
    public void buttonTouched(GameMenuContent content) {
        scene = SCENE_SHOWING_DIALOG;
        leavesDelta = Integer.parseInt(content.getHowMuch());
    }

    public GameMenuContentHandler(){
        scene = SCENE_MAIN;
        handler = new GameObjectHandler();
        GameDialog dialog = new GameDialog(handler,Texts.getText("achievement_dialog_text"));
        dialog.setGameCallback(this);
        dialog.setHandleEventScenes(new int[]{SCENE_SHOWING_DIALOG});
        dialog.setRenderScenes(new int[]{SCENE_SHOWING_DIALOG});
        addContent(new GameMenuContent(Texts.getText("achievement1_title"),Texts.getText("achievement1_content"),"5",this));
        addContent(new GameMenuContent(Texts.getText("achievement4_title"),Texts.getText("achievement4_content"),"10",this));
        addContent(new GameMenuContent(Texts.getText("achievement5_title"),Texts.getText("achievement5_content"),"20",this));
        addContent(new GameMenuContent(Texts.getText("achievement6_title"),Texts.getText("achievement6_content"),"20",this));
        addContent(new GameMenuContent(Texts.getText("achievement2_title"),Texts.getText("achievement2_content"),"50",this));
        addContent(new GameMenuContent(Texts.getText("achievement3_title"),Texts.getText("achievement3_content"),"50",this));
}

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case GameDialog.YES:
                scene = SCENE_MAIN;
                if(listener != null) {
                    listener.gameCallBack(FADE);
                }
                LeafIndicator.leaves += leavesDelta;
                leavesDelta = 0;
                break;
            case GameDialog.NO:
                scene = SCENE_MAIN;
                leavesDelta = 0;
                break;
        }
    }
}
