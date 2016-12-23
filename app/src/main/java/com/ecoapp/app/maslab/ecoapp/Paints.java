package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

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
    public static Paint base_ocean;
    public static Paint base_beach;
    public static Paint AMCHowMuchText;
    public static Paint AMCUnPurchasable;
    public static Paint theme1;
    public static Paint theme2;
    public static Paint theme3;
    public static Paint theme4;
    public static Paint themeMenuContentText;
    public static Paint radioButtonOuter;

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
        base_ocean = new Paint();
        base_ocean.setARGB(255,180,230,235);
        base_beach = new Paint();
        LinearGradient beachG = new LinearGradient(SizeManager.baseSize,SizeManager.baseSize/2,0
        ,SizeManager.baseSize/2,Color.argb(255,200,240,250),Color.argb(255,255,201,107), Shader.TileMode.CLAMP);
        base_beach.setShader(beachG);

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
        theme2 = new Paint();
        LinearGradient theme2G = new LinearGradient(SizeManager.width/2,0,SizeManager.width/2,
                SizeManager.height,Color.argb(255,255,220,163),Color.WHITE, Shader.TileMode.CLAMP);
        theme2.setShader(theme2G);
        theme3 = new Paint();
        LinearGradient theme3G = new LinearGradient(SizeManager.width/2,0,SizeManager.width/2,
                SizeManager.height,Color.argb(255,170,220,255),Color.WHITE, Shader.TileMode.CLAMP);
        theme3.setShader(theme3G);

        themeMenuContentText = new Paint();
        themeMenuContentText.setTextSize(SizeManager.themeMenuContentTextSize);
        themeMenuContentText.setARGB(255,100,100,100);
        radioButtonOuter = new Paint();
        radioButtonOuter.setARGB(255,100,100,100);
        radioButtonOuter.setStyle(Paint.Style.STROKE);
        radioButtonOuter.setStrokeWidth(SizeManager.radioButtonPaintWidth);
    }

    public static Path getEditMark(GardenItem item){
        int x = (int) item.getBitmapX();
        int y = (int) item.getBitmapY();
        int width = item.getBitmapWidth();
        int height = item.getBitmapHeight();
        int size = SizeManager.editMarkSize;
        int sx = x + width/2;
        int sy = y - size;

        Path path = new Path();
        path.moveTo(sx,sy);
        path.lineTo(sx - size/2,y);
        path.lineTo(sx + size/2,y);
        path.lineTo(sx,sy);

        sy = y + height + size;
        path.moveTo(sx,sy);
        path.lineTo(sx - size/2,sy - size);
        path.lineTo(sx + size/2,sy - size);
        path.lineTo(sx,sy);

        sx = x - size;
        sy = y + height/2;
        path.moveTo(sx,sy);
        path.lineTo(sx + size,sy - size/2);
        path.lineTo(sx + size,sy + size/2);
        path.lineTo(sx,sy);

        sx = x + width + size;
        path.moveTo(sx,sy);
        path.lineTo(sx - size,sy - size/2);
        path.lineTo(sx - size,sy + size/2);
        path.lineTo(sx,sy);

        return path;
    }
}
