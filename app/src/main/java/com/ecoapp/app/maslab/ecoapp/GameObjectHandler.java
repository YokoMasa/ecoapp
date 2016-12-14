package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by masato on 2016/12/07.
 */

public class GameObjectHandler{

    private List<GameObject> gameObjects = new ArrayList<GameObject>();

    public void render(Canvas canvas,int scene){
        for(int i = 0;i<gameObjects.size();i++){
            if(gameObjects.get(i).isRenderActive(scene)) {
                gameObjects.get(i).render(canvas);
            }
        }
    }

    public void tick(int scene){
        for(int i = 0;i<gameObjects.size();i++){
            if(gameObjects.get(i).isTickActive(scene)) {
                gameObjects.get(i).tick();
            }
        }
    }

    public void handleEvent(int x,int y,int action,int scene){
        for(int i = 0;i<gameObjects.size();i++){
            if(gameObjects.get(i).isHandleEventActive(scene)) {
                gameObjects.get(i).handleEvent(x, y, action);
            }
        }
    }

    public void sort(){
        Collections.sort(gameObjects);
    }

    public void addGameObject(GameObject object){
        gameObjects.add(object);
    }

    public void removeGameObject(GameObject object){
        gameObjects.remove(object);
    }

    public void removeAll(){
        gameObjects.clear();
    }
}
