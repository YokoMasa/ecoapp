package com.ecoapp.app.maslab.ecoapp.settings;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masato on 2016/12/21.
 */

public class SettingMenuContent extends GameObject {

    private List<ContentText> contentTexts = new ArrayList<ContentText>();
    private GameObjectHandler handler;
    private boolean extendable;
    private boolean extending;
    private String title;
    private int titleWidth;
    private int titleX,titleY;
    private int contentTX,contentTY;
    private float px,py;
    private int height;
    private int code;

    public int getHeight() {
        return height;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }

    public void addContentText(String text,int x,int y){
        ContentText contentText = new ContentText(text,x,y);
        contentTexts.add(contentText);
    }

    public void addGameObject(GameObject gameObject,int x,int y){
        gameObject.setX(x);
        gameObject.setY(y);
        handler.addGameObject(gameObject);
    }

    public void useAsAButton(GameCallback listener,int code){
        setGameCallback(listener);
        this.code = code;
        extendable = false;
        extending = false;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(extendable){
            if(extending){
                if(checkIfInTitleArea(x,y,action)){
                    extending = false;
                    height = SizeManager.menuContentHeight;
                }else{
                    int modX = x - contentTX;
                    int modY = y - contentTY;
                    handler.handleEvent(modX,modY,action,0);
                }
            }else{
                if(checkIfInTitleArea(x,y,action)) {
                    extending = true;
                }
            }
        }else{
            if(checkIfInArea(x,y,action)){
                if(listener != null){
                    listener.gameCallBack(code);
                }
            }
        }
    }

    private boolean checkIfInArea(int x,int y,int action){
        if(getX() < x && x < getX() + SizeManager.menuContentWidth){
            if(getY() < y && y < getY() + height){
                if(action == MotionEvent.ACTION_UP) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfInTitleArea(int x,int y,int action){
        if(getX() < x && x < getX() + SizeManager.menuContentWidth){
            if(getY() < y && y < getY() + SizeManager.menuContentHeight){
                if(action == MotionEvent.ACTION_UP) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX()+ SizeManager.menuContentWidth,getY()+height, Paints.menuContentBack);
        canvas.drawText(title,titleX,titleY,Paints.menuContentTitle);
        if(extending){
            canvas.save();
            canvas.translate(contentTX,contentTY);
            handler.render(canvas,0);
            for(int i = 0;i<contentTexts.size();i++){
                ContentText contentText = contentTexts.get(i);
                canvas.drawText(contentText.text,contentText.x,contentText.y,Paints.menuContentContent);
            }
            canvas.restore();
        }
    }

    @Override
    public void tick() {
        titleX = (int) getX() + SizeManager.menuContentWidth/2 - titleWidth/2;
        titleY = (int) getY() + SizeManager.menuContentHeight/2 + SizeManager.menuTitleSize/2;
        if(extending){
            int deepest = 0;
            for(int i = 0;i<handler.getCount();i++){
                GameObject object = handler.getGameObject(i);
                int depth = (int) object.getY() + contentTY+ object.getHeight() - (int) getY();
                if(deepest < depth){
                    deepest = depth;
                }
            }
            for(int i = 0;i<contentTexts.size();i++){
                ContentText contentText = contentTexts.get(i);
                int depth = contentText.y + contentTY - (int) getY();
                if(deepest < depth){
                    deepest = depth;
                }
            }
            height = deepest + SizeManager.listContentGap;
            handler.tick(0);
        }
        contentTY = (int) getY() + SizeManager.menuContentHeight;
    }

    public SettingMenuContent(GameObjectHandler motherHandler,String title) {
        motherHandler.addGameObject(this);
        this.title = title;
        this.height = SizeManager.menuContentHeight;
        titleWidth = (int) Paints.menuContentTitle.measureText(title);
        contentTX = SizeManager.listMenuPaddingX;
        handler = new GameObjectHandler();
        extending = false;
        extendable = false;
    }

    private class ContentText{
        String text;
        int x,y;

        public ContentText(String text,int x,int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }
}
