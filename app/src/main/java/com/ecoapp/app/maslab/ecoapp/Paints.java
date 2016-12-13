package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by masato on 2016/12/06.
 */

public class Paints {

    public static Paint background_white;
    public static Paint text_black;
    public static Paint button_text;
    public static Paint base_green;
    public static Paint button_R;
    public static Paint button_R_pressed;
    public static Paint button_G;
    public static Paint button_G_pressed;
    public static Paint button_B;
    public static Paint button_B_pressed;
    public static Paint achievementMenuBack;
    public static Paint menuContentBack;
    public static Paint menuContentTitle;
    public static Paint menuContentContent;
    public static Paint achievementMenuButton;
    public static Paint dialogBorder;
    public static Paint base_desert;
    public static Paint AMCHowMuchText;
    public static Paint AMCUnPurchasable;
    public static Paint theme1;

    public static void setPaints(){
        background_white = new Paint();
        background_white.setColor(Color.WHITE);
        text_black = new Paint();
        text_black.setARGB(255,100,100,100);
        text_black.setTextSize(SizeManager.textSize);
        button_text = new Paint();
        button_text.setARGB(255,100,100,100);
        button_text.setTextSize(SizeManager.textSize);
        button_text.setTextAlign(Paint.Align.CENTER);
        base_green = new Paint();
        base_green.setARGB(255,200,255,200);
        base_desert = new Paint();
        base_desert.setARGB(255,254,227,202);

        button_R = new Paint();
        button_R.setARGB(255,255,200,200);
        button_R_pressed = new Paint();
        button_R_pressed.setARGB(255,255,100,100);
        button_G = new Paint();
        button_G.setARGB(255,200,255,200);
        button_G_pressed = new Paint();
        button_G_pressed.setARGB(255,100,255,100);
        button_B = new Paint();
        button_B.setARGB(255,200,200,255);
        button_B_pressed = new Paint();
        button_B_pressed.setARGB(255,100,100,255);

        achievementMenuBack = new Paint();
        achievementMenuBack.setARGB(100,208,122,44);
        menuContentBack = new Paint();
        menuContentBack.setARGB(255,236,163,51);
        menuContentTitle = new Paint();
        menuContentTitle.setARGB(255,100,100,100);
        menuContentTitle.setTextSize(SizeManager.menuTitleSize);
        menuContentContent = new Paint();
        menuContentContent.setARGB(255,100,100,100);
        menuContentContent.setTextSize(SizeManager.menuContentTextSize);
        achievementMenuButton = new Paint();
        achievementMenuButton.setARGB(255,230,130,35);
        dialogBorder = new Paint();
        dialogBorder.setStyle(Paint.Style.STROKE);
        dialogBorder.setStrokeWidth(SizeManager.dialogBorder);
        dialogBorder.setARGB(255,100,100,100);
        AMCHowMuchText = new Paint();
        AMCHowMuchText.setARGB(255,100,100,100);
        AMCHowMuchText.setTextSize(SizeManager.AMCHowMuchTextSize);
        AMCUnPurchasable = new Paint();
        AMCUnPurchasable.setARGB(255,161,177,189);

        theme1 = new Paint();
        LinearGradient theme1G = new LinearGradient(SizeManager.width/2,0,SizeManager.width/2,
                SizeManager.height,Color.argb(255,200,240,255),Color.WHITE, Shader.TileMode.CLAMP);
        theme1.setShader(theme1G);
    }
}