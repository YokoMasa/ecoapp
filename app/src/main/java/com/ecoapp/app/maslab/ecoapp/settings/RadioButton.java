package com.ecoapp.app.maslab.ecoapp.settings;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.Paints;
import com.ecoapp.app.maslab.ecoapp.SizeManager;

/**
 * Created by masato on 2016/12/21.
 */

public class RadioButton extends GameObject {

    private boolean pressed;
    private RadioButtonListener listener;
    private int code;

    public void setRadioButtonListener(RadioButtonListener listener){
        this.listener = listener;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        float distance = (float)Math.sqrt((getX() - x)*(getX() - x) + (getY() - y)*(getY() - y));
        if(distance < SizeManager.radioButtonSize/2){
            if(action == MotionEvent.ACTION_UP) {
                touched();
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawCircle(getX(),getY(),SizeManager.radioButtonSize/2,Paints.radioButtonOuter);
        if(pressed){
            canvas.drawCircle(getX(),getY(),SizeManager.radioButtonMiddleSize,Paints.text_black);
        }
    }

    @Override
    public void tick() {

    }

    private void touched(){
        if(pressed){
            /*
            if(listener != null){
                listener.radioButtonUnchosen(code);
            }
            pressed = false;
            */
        }else{
            if(listener != null){
                listener.radioButtonChosen(code);
            }
            pressed = true;
        }
    }

    public RadioButton(GameObjectHandler handler,RadioButtonListener listener,int x,int y,int code) {
        handler.addGameObject(this);
        setX(x);
        setY(y);
        setRadioButtonListener(listener);
        this.code = code;
    }


    public static interface RadioButtonListener{
        public void radioButtonChosen(int code);

        public void radioButtonUnchosen(int code);
    }
}
