package com.ecoapp.app.maslab.ecoapp;

/**
 * Created by masato on 2016/12/08.
 */

public class AchievementMenu extends ListMenu {

    public AchievementMenu(GameObjectHandler handler) {
        super(handler);
        setRenderScenes(new int[]{MainGameView.SCENE_ON_MENU});
        setTickScenes( new int[]{MainGameView.SCENE_ON_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_MENU});
    }

    @Override
    public void gameCallBack(int code) {
        if(code == GameMenuContentHandler.FADE){
            fade();
        }
    }
}
