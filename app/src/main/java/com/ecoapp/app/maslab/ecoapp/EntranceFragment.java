package com.ecoapp.app.maslab.ecoapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by masato on 2016/12/06.
 */

public class EntranceFragment extends Fragment {

    private FragmentHandler handler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof FragmentHandler){
            this.handler = (FragmentHandler) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentHandler){
            this.handler = (FragmentHandler) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entrance_page,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        scaleSize();
        preparePaints();
        Bitmaps.loadBitmaps(getResources());
        Texts.loadTexts(getActivity(),Texts.JAPANESE);
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.callBack(FragmentHandler.EXIT_ENTRANCE);
            }
        },1000);
    }

    private void scaleSize(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        SizeManager.scaleSize(point);
    }

    private void preparePaints(){
        Paints.setPaints();
    }
}
