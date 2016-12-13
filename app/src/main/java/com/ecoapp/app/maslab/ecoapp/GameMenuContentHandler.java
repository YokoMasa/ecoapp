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

public class GameMenuContentHandler extends ContentHandler implements GameMenuContent.GameMenuContentCallback{

    public static final int FADE = 190;
    private final int SCENE_MAIN = 0;
    private final int SCENE_SHOWING_DIALOG = 1;
    private int scene;
    private int leavesDelta;
    private int firstTouchX;
    private int firstTouchY;
    private int previousY;
    private int contentY;
    private int ticks;
    private int totalContentsHeight;
    private int totalContentsYMin;
    private int button1X;
    private int button2X;
    private int buttonY;
    private int buttonWidth,buttonHeight;
    private int dialogTextX,dialogTextY;
    private int dialogButton1TextX,dialogButton2TextX;
    private int dialogButtonTextY;
    private Paint button1;
    private Paint button2;
    private boolean dialogButtonPressed;
    private boolean scrollable;
    private boolean scrolling;

    @Override
    protected void renderContents(Canvas canvas) {
        int renderY = contentY;
        for (int i = 0; i < contents.size(); i++) {
            GameMenuContent content = (GameMenuContent) contents.get(i);
            content.setY(renderY);
            content.render(canvas);
            renderY += content.getHeight() + listContentGap;
        }
        if(scene == SCENE_SHOWING_DIALOG){
            canvas.drawRect(menuDialogX,menuDialogY,menuDialogX + menuDialogWidth,menuDialogY + menuDialogHeight,Paints.achievementMenuButton);
            canvas.drawText(Texts.getText("achievement_dialog_text"),dialogTextX,dialogTextY,Paints.menuContentContent);
            canvas.drawRect(button1X,buttonY,button1X+buttonWidth,buttonY+buttonHeight,button1);
            canvas.drawRect(button2X,buttonY,button2X+buttonWidth,buttonY+buttonHeight,button2);
            canvas.drawText(Texts.getText("achievement_dialog_no"),dialogButton1TextX,dialogButtonTextY,Paints.menuContentContent);
            canvas.drawText(Texts.getText("achievement_dialog_yes"),dialogButton2TextX,dialogButtonTextY,Paints.menuContentContent);
            canvas.drawRect(menuDialogX,menuDialogY,menuDialogX + menuDialogWidth,menuDialogY + menuDialogHeight,Paints.dialogBorder);
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
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        x -= SizeManager.listMenuPaddingX;
        y -= SizeManager.listMenuPaddingY;
        if(scene == SCENE_MAIN) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    firstTouchX = x;
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
                    firstTouchX = 0;
                    firstTouchY = 0;
                    ticks = 0;
                    break;
            }
        }else{
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    if(buttonY < y && y < buttonY + buttonHeight){
                        if(button1X < x && x < button1X + buttonWidth){
                            dialogButtonPressed = true;
                            button1 = Paints.button_R_pressed;
                        }else if(button2X < x && x < button2X + buttonWidth){
                            dialogButtonPressed = true;
                            button2 = Paints.button_G_pressed;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(dialogButtonPressed){
                        if(buttonY < y && y < buttonY + buttonHeight){
                            if(button1X < x && x < button1X + buttonWidth){
                                button1 = Paints.button_R_pressed;
                            }else if(button2X < x && x < button2X + buttonWidth){
                                button2 = Paints.button_G_pressed;
                            }else {
                                button1 = Paints.button_R;
                                button2 = Paints.button_G;
                            }
                        }else {
                            button1 = Paints.button_R;
                            button2 = Paints.button_G;
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if(dialogButtonPressed){
                        if(buttonY < y && y < buttonY + buttonHeight){
                            if(button1X < x && x < button1X + buttonWidth){
                                dialogNo();
                            }else if(button2X < x && x < button2X + buttonWidth){
                                dialogYes();
                            }
                        }
                    }
                    button1 = Paints.button_R;
                    button2 = Paints.button_G;
                    dialogButtonPressed = false;
                    break;
            }
        }
    }

    private void dialogYes(){
        scene = SCENE_MAIN;
        if(listener != null) {
            listener.gameCallBack(FADE);
        }
        LeafIndicator.leaves += leavesDelta;
        leavesDelta = 0;
    }

    private void dialogNo(){
        scene = SCENE_MAIN;
        leavesDelta = 0;
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
        button1 = Paints.button_R;
        button2 = Paints.button_G;
        buttonWidth = menuDialogWidth/2;
        buttonHeight = buttonWidth * 2/5;
        button1X = menuDialogX;
        button2X = menuDialogX + menuDialogWidth/2;
        buttonY = menuDialogY + (menuDialogHeight - buttonHeight);
        int dialogTextWidth = (int) Paints.menuContentContent.measureText(Texts.getText("achievement_dialog_text"));
        dialogTextX = menuDialogX + menuDialogWidth/2 - dialogTextWidth/2;
        dialogTextY = menuDialogY + menuDialogHeight * 2/5;
        int dialogButtonText1Width = (int) Paints.menuContentContent.measureText(Texts.getText("achievement_dialog_no"));
        dialogButton1TextX = button1X + buttonWidth/2 - dialogButtonText1Width/2;
        int dialogButtonText2Width = (int) Paints.menuContentContent.measureText(Texts.getText("achievement_dialog_yes"));
        dialogButton2TextX = button2X + buttonWidth/2 - dialogButtonText2Width/2;
        dialogButtonTextY = buttonY + buttonHeight/2 + menuContentTextSize/2 - dialogBorder;
        addContent(new GameMenuContent(Texts.getText("achievement1_title"),Texts.getText("achievement1_content"),"20",this));
        addContent(new GameMenuContent(Texts.getText("achievement4_title"),Texts.getText("achievement4_content"),"50",this));
        addContent(new GameMenuContent(Texts.getText("achievement5_title"),Texts.getText("achievement5_content"),"50",this));
        addContent(new GameMenuContent(Texts.getText("achievement2_title"),Texts.getText("achievement2_content"),"100",this));
        addContent(new GameMenuContent(Texts.getText("achievement3_title"),Texts.getText("achievement3_content"),"100",this));
}
}
