package com.ecoapp.app.maslab.ecoapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ecoapp.app.maslab.ecoapp.pastdata.PastFragment;

public class MainActivity extends AppCompatActivity implements FragmentHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mother,new EntranceFragment());
        ft.commit();
    }


    @Override
    public void callBack(int code) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch(code){
            case FragmentHandler.EXIT_ENTRANCE:
                //MainGameFragment mainGameFragment = new MainGameFragment();
                //ft.replace(R.id.mother,mainGameFragment);
                PastFragment pastFragment = new PastFragment();
                ft.replace(R.id.mother,pastFragment);
                break;
            case FragmentHandler.BACK_TO_MAIN:
                MainGameFragment mainGameFragment = new MainGameFragment();
                ft.replace(R.id.mother,mainGameFragment);
                break;
        }
        ft.commit();

    }
}
