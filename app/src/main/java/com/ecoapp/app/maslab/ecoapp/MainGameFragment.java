package com.ecoapp.app.maslab.ecoapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by masato on 2016/12/06.
 */

public class MainGameFragment extends Fragment implements GameCallback {

    public static final int SHOW_PAST_FRAGMENT = 62999;
    public static final int RESTART_GAME = 3378900;
    private FragmentHandler fragmentHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainGameView view = new MainGameView(getActivity());
        view.setFragmentCallBack(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof FragmentHandler){
            this.fragmentHandler = (FragmentHandler) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentHandler){
            this.fragmentHandler = (FragmentHandler) context;
        }
    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case 0:
                fragmentHandler.callBack(SHOW_PAST_FRAGMENT);
                break;
            case 1:
                fragmentHandler.callBack(RESTART_GAME);
                break;
        }

    }
}
