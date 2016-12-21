package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/07.
 */

public class GameMenuContent extends GameObject {

    private int width,height;
    private float titleX,titleY;
    private float contentTranslate;
    private float buttonX,buttonY;
    private float buttonWidth,buttonHeight;
    private float buttonTranslate;
    private float buttonTextX;
    private float buttonTextY;
    private float leafX,leafY;
    private Paint buttonPaint;
    private String title;
    private String howMuch;
    private StaticLayout content;
    private boolean extended;
    private GameMenuContentCallback listener;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHowMuch() {
        return howMuch;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(getX() < x && x < getX() + this.width){
            if(getY() < y && y < getY() + this.height){
                if(extended){
                    if(checkButton(x,y) && listener != null){
                        listener.buttonTouched(this);
                        return;
                    }
                    extended = false;
                    if(this.content != null){
                        this.height -= this.content.getHeight() + buttonHeight * 2;
                    }
                }else{
                    extended = true;
                    if(this.content != null){
                        this.height += this.content.getHeight() + buttonHeight * 2;
                    }
                }
            }
        }
    }

    private boolean checkButton(int x,int y){
        if(buttonX < x && x < buttonX + buttonWidth){
            if(buttonY < y && y < buttonY + buttonHeight){
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(getX(),getY(),getX() + this.width,getY() + this.height,Paints.menuContentBack);
        titleY = getY() + menuContentHeight/2 + textSize/2;
        //Log.i("info",Float.toString(textSize));
        canvas.drawText(title,titleX,titleY,Paints.menuContentTitle);
        if(extended) {
            int code = canvas.save();
            contentTranslate = titleY + menuContentHeight / 3;
            canvas.translate(titleX,contentTranslate);
            if(content != null) {
                content.draw(canvas);
            }
            buttonY = getY() + buttonTranslate;
            buttonTextY = buttonY + buttonHeight/2 + menuContentTextSize/2;
            leafY = buttonY + buttonHeight/6;
            canvas.restoreToCount(code);
            canvas.drawRect(buttonX,buttonY,buttonX + buttonWidth,buttonY + buttonHeight,Paints.achievementMenuButton);
            canvas.drawBitmap(Bitmaps.leaf,leafX,leafY,null);
            canvas.drawText(howMuch,buttonTextX,buttonTextY,Paints.menuContentContent);
        }



    }

    @Override
    public void tick() {

    }

    public void shrink(){
        if(extended) {
            extended = false;
            if (this.content != null) {
                this.height -= this.content.getHeight() + buttonHeight * 2;
            }
        }
    }

    public GameMenuContent(String title, String content,String howMuch,GameMenuContentCallback listener){
        this.listener = listener;
        this.width = menuContentWidth;
        this.height = menuContentHeight;
        titleX = getX() + this.width / 12;
        buttonX =  titleX;
        buttonHeight = menuContentHeight * 2/3;
        buttonWidth = menuContentWidth - titleX*2;
        leafX = this.width/2 - (Bitmaps.leaf.getWidth() + Paints.menuContentContent.measureText(howMuch))/2;
        buttonTextX = leafX + Bitmaps.leaf.getWidth();
        this.title = title;
        this.howMuch = howMuch;
        TextPaint paint = new TextPaint(Paints.menuContentContent);
        if(content != null) {
            this.content = new StaticLayout(content, paint, menuContentContentWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
            if(this.content != null){
                buttonTranslate = this.height * 3/2 + this.content.getHeight();
            }
        }
    }

    public static interface GameMenuContentCallback{
        public void buttonTouched(GameMenuContent content);
    }

}
