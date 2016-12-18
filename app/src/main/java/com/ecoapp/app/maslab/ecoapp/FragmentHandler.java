package com.ecoapp.app.maslab.ecoapp;

/**
 * Created by masato on 2016/12/06.
 */

public interface FragmentHandler {

    public static final int EXIT_ENTRANCE = 0;
    public static final int BACK_TO_MAIN = 1;

    public void callBack(int code);
}
