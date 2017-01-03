package com.ecoapp.app.maslab.ecoapp;

import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.ecoapp.app.maslab.ecoapp.garden.DataManager;
import com.ecoapp.app.maslab.ecoapp.pastdata.PastFragment;
import com.ecoapp.app.maslab.ecoapp.settings.SettingPrefUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements FragmentHandler,GameCallback {

    private boolean initiated;
    private EntranceFragment entranceFragment;
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        entranceFragment = new EntranceFragment();
        ft.add(R.id.mother,entranceFragment);
        ft.commit();

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.ecoapp_bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.2f,0.2f);

        DataManager.setGameCallBack(this);

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                forceExitEntrance();
            }
        },10000);

        MobileAds.initialize(getApplicationContext(),"ca-app-pub-9123843554491088~2194031658");

        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E89CB614F304A53F1B274611DDCAD047")
                .addTestDevice("BA25B6278B347821C3FB26313E1A81A3")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(!initiated) {
                    entranceFragment.init(mAdView.getHeight());
                    initiated = true;
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(!initiated) {
                    entranceFragment.init(0);
                    initiated = true;
                    Toast.makeText(getApplicationContext(),"Couldn't load ads",Toast.LENGTH_SHORT).show();
                }
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void forceExitEntrance(){
        if(!initiated){
            entranceFragment.init(0);
            initiated = true;
            Toast.makeText(getApplicationContext(),"Couldn't load ads",Toast.LENGTH_SHORT).show();
        }
    }

    public static void startBGM(){
        mediaPlayer.start();
    }

    public static void stopBGM(){
        mediaPlayer.stop();
        try{
            mediaPlayer.prepare();
        }catch(IllegalArgumentException ex){
            ex.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingPrefUtil.isSoundOn(this)){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
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

    @Override
    public void gameCallBack(int code) {
        if(code == DataManager.END_APP){
            finish();
        }
    }
}
