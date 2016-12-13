package com.ecoapp.app.maslab.ecoapp;

/**
 * Created by masato on 2016/12/07.
 */

public interface GameCallback {

    public static final int ADD_BUTTON_PRESSED = 0;
    public static final int DECOR_BUTTON_PRESSED = 2;
    public static final int MENU_FADED = 1;


    public void gameCallBack(int code);
}
