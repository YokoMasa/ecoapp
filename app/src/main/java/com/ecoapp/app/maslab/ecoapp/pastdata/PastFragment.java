package com.ecoapp.app.maslab.ecoapp.pastdata;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecoapp.app.maslab.ecoapp.FragmentHandler;
import com.ecoapp.app.maslab.ecoapp.GameCallback;

/**
 * Created by masato on 2016/12/18.
 */

public class PastFragment extends Fragment implements GameCallback{

    private FragmentHandler fragmentHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PastView view = new PastView(getActivity());
        view.setGameCallBack(this);
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
        fragmentHandler.callBack(FragmentHandler.BACK_TO_MAIN);
    }
}
