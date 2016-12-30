package com.ecoapp.app.maslab.ecoapp;

import android.app.FragmentTransaction;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.ecoapp.app.maslab.ecoapp.pastdata.PastFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements FragmentHandler {

    private boolean initiated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-9123843554491088~2194031658");

        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E89CB614F304A53F1B274611DDCAD047")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(!initiated) {
                    enterEntrance(mAdView.getHeight());
                    initiated = true;
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(!initiated) {
                    enterEntrance(0);
                    initiated = true;
                }
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void enterEntrance(int adHeight){
        Bundle args = new Bundle();
        args.putInt("adHeight",adHeight);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        EntranceFragment entranceFragment = new EntranceFragment();
        entranceFragment.setArguments(args);
        ft.add(R.id.mother,entranceFragment);
        ft.commit();
    }

    @Override
    public void callBack(int code) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch(code){
            case FragmentHandler.EXIT_ENTRANCE:
            case MainGameFragment.RESTART_GAME:
                MainGameFragment mainGameFragment = new MainGameFragment();
                ft.replace(R.id.mother,mainGameFragment);
                break;
            case FragmentHandler.BACK_TO_MAIN:
                MainGameFragment mainGameFragment2 = new MainGameFragment();
                ft.replace(R.id.mother,mainGameFragment2);
                break;
            case MainGameFragment.SHOW_PAST_FRAGMENT:
                PastFragment pastFragment = new PastFragment();
                ft.replace(R.id.mother,pastFragment);
                break;
        }
        ft.commit();

    }
}
