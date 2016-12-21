package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Point;

/**
 * Created by masato on 2016/12/06.
 */

public class SizeManager {

    public static int width,height;
    public static float textSize;
    public static int middleX,middleY;
    public static int baseSize;
    public static int addButtonSize;
    public static int addButtonX,addButtonY;
    public static int decorButtonSize;
    public static int decorButtonX,decorButtonY;
    public static int crossButtonSize;
    public static int crossButtonX,crossButtonY;
    public static float fadeVel;
    public static int menuContentWidth;
    public static int menuContentHeight;
    public static int menuTitleSize;
    public static int menuDialogWidth;
    public static int menuDialogHeight;
    public static int menuDialogX,menuDialogY;
    public static int listMenuPaddingX;
    public static int listMenuPaddingY;
    public static int listContentGap;
    public static int listMaxContentHeight;
    public static int tapDistance;
    public static int menuContentTextSize;
    public static int menuContentContentWidth;
    public static int leafSize;
    public static int leafIndicatorX,leafIndicatorY;
    public static int dialogBorder;
    public static float gridWidth;
    public static float gridHeight;
    public static int itemIconSize;
    public static int AMCSize;
    public static int AMCgap;
    public static int AMCBitmapSize;
    public static int AMCBitmapX;
    public static int AMCBitmapY;
    public static int AMCHowMuchTextSize;
    public static int AMCLeafSize;
    public static int AMCLeafY;
    public static int AMCTextY;
    public static int AMCTotalWidth;
    public static int AMCTotalHeight;
    public static int gardenItemSize;
    public static int themeMenuX,themeMenuY;
    public static int themeMenuHeight;
    public static int themeMenuContentSize;
    public static int themeMenuContentGap;
    public static int themeMenuContentTextSize;
    public static int themeMenuContentTextY;
    public static int themeMenuContentBitmapSize;
    public static int themeMenuContentPadding;
    public static int editMenuHeight;
    public static int editMenuY;
    public static int editMenuContentSize;
    public static int editMenuContentGap;
    public static int editMenuVel;
    public static int disposeButtonX;
    public static int editMarkSize;
    public static int radioButtonSize;
    public static int radioButtonPaintWidth;
    public static int radioButtonMiddleSize;
    public static int radioButtonGap;

    public static void scaleSize(Point appScreenSize){
        width = appScreenSize.x;
        height = appScreenSize.y;
        middleX = width / 2;
        middleY = height / 2;
        baseSize = width * 5/11;
        textSize = height / 23;
        addButtonSize = width / 6;
        addButtonX = width * 6/8;
        addButtonY = height - addButtonSize - (width - addButtonX - addButtonSize);
        decorButtonSize = width / 6;
        decorButtonX = width * 2/8 - decorButtonSize;
        decorButtonY = addButtonY;
        fadeVel = height / 10;
        crossButtonSize = width / 10;
        crossButtonX = width * 7/8;
        crossButtonY = width - crossButtonX - crossButtonSize;
        listMenuPaddingX = width / 13;
        listMenuPaddingY = listMenuPaddingX * 5/3;
        menuContentWidth = width - listMenuPaddingX*2;
        menuContentHeight = menuContentWidth * 1/4;
        listContentGap = menuContentHeight / 5;
        menuTitleSize = width / 17;
        menuContentTextSize = width / 20;
        listMaxContentHeight = height - listMenuPaddingY*2;
        tapDistance = height / 100;
        menuContentContentWidth = menuContentWidth - width / 6;
        leafSize = menuContentHeight / 2;
        leafIndicatorX = width / 15;
        leafIndicatorY = leafIndicatorX;
        menuDialogHeight = menuContentHeight * 3;
        menuDialogWidth = menuContentWidth * 4/5;
        menuDialogX = menuContentWidth/2 - menuDialogWidth/2;
        menuDialogY = (height - listMenuPaddingY*2)/2 - menuDialogHeight/2;
        dialogBorder = menuDialogHeight/30;
        float baseWidth = (float) Math.sqrt( 7/2 ) * baseSize;
        float baseHeight = (float) Math.sqrt(2)/2 * baseSize;
        gridWidth = baseWidth / 9;
        gridHeight = baseHeight / 9;
        itemIconSize = width/5;
        AMCSize = (width - listMenuPaddingX*2) * 5/16;
        AMCgap = (menuContentWidth - AMCSize*3) / 2;
        AMCBitmapSize = AMCSize * 3/5;
        AMCBitmapX = AMCSize * 1/5;
        AMCBitmapY = AMCSize * 1/10;
        AMCHowMuchTextSize = AMCSize * 1/5;
        AMCLeafSize = AMCHowMuchTextSize;
        AMCLeafY = AMCSize * 7/10;
        AMCTextY = AMCLeafY + AMCSize/10 + AMCHowMuchTextSize/2;
        AMCTotalWidth = width - listMenuPaddingX*2;
        AMCTotalHeight = height - listMenuPaddingY*2;
        gardenItemSize = height / 7;

        themeMenuContentSize = (width - listMenuPaddingX*2) * 5/18;
        themeMenuContentGap = themeMenuContentSize * 3/10;
        themeMenuHeight = themeMenuContentSize + themeMenuContentGap*3 + menuTitleSize;
        themeMenuX = 0;
        themeMenuY = listMenuPaddingY * 2/3;
        themeMenuContentTextSize = themeMenuContentSize/8;
        themeMenuContentTextY = themeMenuContentSize - themeMenuContentTextSize/2;
        themeMenuContentBitmapSize = themeMenuContentSize * 11/16;
        themeMenuContentPadding = themeMenuContentSize/16;

        editMenuContentSize = itemIconSize * 7/5;
        editMenuContentGap = editMenuContentSize/6;
        editMenuHeight = editMenuContentSize + editMenuContentGap*2;
        editMenuY = height - editMenuHeight;
        editMenuVel = editMenuHeight/10;
        disposeButtonX = width/2 - addButtonSize/2;

        editMarkSize = width/20;

        radioButtonSize = width / 12;
        radioButtonPaintWidth = radioButtonSize/8;
        radioButtonMiddleSize = radioButtonSize/5;
        radioButtonGap = radioButtonSize/2;


    }
}
