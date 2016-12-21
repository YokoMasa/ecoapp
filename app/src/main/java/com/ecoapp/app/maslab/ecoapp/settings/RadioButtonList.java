package com.ecoapp.app.maslab.ecoapp.settings;

import android.graphics.Canvas;
import android.graphics.Path;
import android.util.Log;

import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masato on 2016/12/21.
 */

public class RadioButtonList extends GameObject implements RadioButton.RadioButtonListener{

    private GameObjectHandler handler;
    private RadioButtonListListener radioButtonListListener;
    private int count;
    private float px,py;

    public void setRadioButtonListListener(RadioButtonListListener radioButtonListListener){
        this.radioButtonListListener = radioButtonListListener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        handler.handleEvent(x,y,action,0);
    }

    @Override
    public void render(Canvas canvas) {
        handler.render(canvas,0);
    }

    @Override
    public void tick() {
        handler.tick(0);
        if(getX() != px || getY() != py){
            int buttonX = (int) getX() + SizeManager.radioButtonSize/2;
            int buttonY = (int) getY() + SizeManager.radioButtonSize/2;
            for(int i = 0;i<count;i++){
                handler.getGameObject(i).setY(buttonY);
                handler.getGameObject(i).setX(buttonX);
                buttonY += SizeManager.radioButtonSize + SizeManager.radioButtonGap;
            }
        }
        px = getX();
        py = getY();
    }

    public RadioButtonList(GameObjectHandler motherHandler,int count) {
        if(motherHandler != null) {
            motherHandler.addGameObject(this);
        }
        this.handler = new GameObjectHandler();
        this.count = count;
        createButtonRow();
        gwidth = SizeManager.radioButtonSize;
        gheight = SizeManager.radioButtonSize * count + SizeManager.radioButtonGap * (count - 1);
    }

    private void createButtonRow(){
        handler.removeAll();
        int buttonX = (int) getX() + SizeManager.radioButtonSize/2;
        int buttonY = (int) getY() + SizeManager.radioButtonSize/2;
        for(int i = 0;i<count;i++){
            new RadioButton(handler,this,buttonX,buttonY,i);
            buttonY += SizeManager.radioButtonSize + SizeManager.radioButtonGap;
        }
    }

    @Override
    public void radioButtonChosen(int code) {
        radioButtonListListener.radioButtonChosen(this,code);
        for(int i = 0;i<handler.getCount();i++){
            if(i != code){
                RadioButton button = (RadioButton) handler.getGameObject(i);
                button.setPressed(false);
            }
        }
    }

    @Override
    public void radioButtonUnchosen(int code) {

    }

    public static interface RadioButtonListListener{
        public void radioButtonChosen(RadioButtonList radioButtonList,int code);
    }
}
