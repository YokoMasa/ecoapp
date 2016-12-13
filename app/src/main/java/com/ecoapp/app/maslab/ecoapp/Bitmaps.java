package com.ecoapp.app.maslab.ecoapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/07.
 */

public class Bitmaps {

    public static Bitmap addButton;
    public static Bitmap addButtonPressed;
    public static Bitmap decorButton;
    public static Bitmap decorButtonPressed;
    public static Bitmap crossButton;
    public static Bitmap leaf;
    public static Bitmap AMCleaf;
    public static Bitmap okButton;
    public static Bitmap okButtonPressed;
    public static Bitmap okButtonDisabled;
    public static Bitmap cancelButton;
    public static Bitmap cancelButtonPressed;

    public static void loadBitmaps(Resources resources){
        Bitmap raw = BitmapFactory.decodeResource(resources,R.drawable.add_button);
        addButton = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.add_button_pressed);
        addButtonPressed = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.cross_button);
        crossButton = Bitmap.createScaledBitmap(raw,crossButtonSize,crossButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.decor_button);
        decorButton = Bitmap.createScaledBitmap(raw,decorButtonSize,decorButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.decor_button_pressed);
        decorButtonPressed = Bitmap.createScaledBitmap(raw,decorButtonSize,decorButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.leaf);
        leaf = Bitmap.createScaledBitmap(raw,SizeManager.leafSize,leafSize,false);
        AMCleaf = Bitmap.createScaledBitmap(raw,AMCLeafSize,AMCLeafSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.ok_button);
        okButton = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.ok_button_pressed);
        okButtonPressed = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.ok_button_disabled);
        okButtonDisabled = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.cancel_button);
        cancelButton = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
        raw = BitmapFactory.decodeResource(resources,R.drawable.cancel_button_pressed);
        cancelButtonPressed = Bitmap.createScaledBitmap(raw,addButtonSize,addButtonSize,false);
    }
}
