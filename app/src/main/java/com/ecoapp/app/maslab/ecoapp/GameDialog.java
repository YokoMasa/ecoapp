package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by masato on 2016/12/14.
 */

public class GameDialog extends GameObject implements GameCallback {

    public static final int YES = 0;
    public static final int NO = 1;
    private StaticLayout staticLayout;
    private GameObjectHandler handler;
    private GameCallback listener;
    private String text;
    private int textX,textY;
    private int dialogWidth,dialogHeight;
    private int dialogX,dialogY;
    private int dialogGap;
    private int dialogButtonHeight;
    private int dialogButtonY;
    private int frameX,frameY;
    private int frameWidth,frameHeight;

    public void setGameCallback(GameCallback listener){
        this.listener = listener;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        handler.handleEvent(x,y,action,0);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(dialogX,dialogY,dialogX+dialogWidth,dialogY+dialogHeight,Paints.menuContentBack);
        canvas.save();
        canvas.translate(textX,textY);
        staticLayout.draw(canvas);
        canvas.restore();
        handler.render(canvas,0);
        canvas.drawRect(frameX,frameY,frameX+frameWidth,frameY+frameHeight,Paints.dialogBorder);
    }

    @Override
    public void tick() {
        handler.tick(0);
    }



    public GameDialog(GameObjectHandler motherHandler,String text) {
        motherHandler.addGameObject(this);
        this.text = text;
        handler = new GameObjectHandler();
        dialogWidth = SizeManager.width * 2/3;
        dialogGap = dialogWidth * 1/10;
        Paint paint = new Paint();
        paint.setARGB(255,100,100,100);
        paint.setTextSize(dialogWidth/12);
        TextPaint textPaint = new TextPaint(paint);
        dialogButtonHeight = dialogWidth * 3/15;
        staticLayout = new StaticLayout(text,textPaint,dialogWidth-dialogGap*2, Layout.Alignment.ALIGN_CENTER,1.0f,0,false);
        dialogHeight = dialogGap*2 + staticLayout.getHeight() + dialogButtonHeight;
        dialogX = SizeManager.width/2 - dialogWidth/2;
        dialogY = SizeManager.height/2 - dialogHeight/2;
        dialogButtonY = dialogY + dialogGap*2 + staticLayout.getHeight();
        textX = dialogX + dialogGap;
        textY = dialogY + dialogGap;
        frameX = dialogX-SizeManager.dialogBorder/2;
        frameY = dialogY-SizeManager.dialogBorder/2;
        frameWidth = dialogWidth + SizeManager.dialogBorder;
        frameHeight = dialogHeight + SizeManager.dialogBorder;

        GameButton no = new GameButton(dialogX,dialogButtonY,dialogWidth/2,dialogButtonHeight,NO,GameButton.RED,handler);
        no.setText(Texts.getText("achievement_dialog_no"));
        no.setGameCallback(this);
        GameButton yes = new GameButton(dialogX+dialogWidth/2,dialogButtonY,dialogWidth/2,dialogButtonHeight,YES,GameButton.GREEN,handler);
        yes.setText(Texts.getText("achievement_dialog_yes"));
        yes.setGameCallback(this);
    }

    @Override
    public void gameCallBack(int code) {
        listener.gameCallBack(code);
    }
}
