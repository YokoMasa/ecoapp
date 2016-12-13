package com.ecoapp.app.maslab.ecoapp;

/**
 * Created by masato on 2016/12/08.
 */

public class DecorMenu extends ListMenu implements GameCallback{

    private DecorMenuListener listener;

    public void setDecorMenuListener(DecorMenuListener listener){
        this.listener = listener;
    }

    public DecorMenu(GameObjectHandler handler,int theme) {
        super(handler);
        DecorMenuContentHandler decorMenuContentHandler = new DecorMenuContentHandler(theme);
        decorMenuContentHandler.setGameCallBack(this);
        setGameObjectHandler(decorMenuContentHandler);
        setRenderScenes(new int[]{MainGameView.SCENE_ON_DECOR_MENU});
        setTickScenes(new int[]{MainGameView.SCENE_ON_DECOR_MENU});
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_DECOR_MENU});
    }

    @Override
    public void gameCallBack(int code) {
        listener.editStart(Integer.toString(code));
    }

    public static interface DecorMenuListener{
        public void editStart(String id);
    }
}
