package com.ecoapp.app.maslab.ecoapp.garden;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.ecoapp.app.maslab.ecoapp.Bitmaps;
import com.ecoapp.app.maslab.ecoapp.GameCallback;
import com.ecoapp.app.maslab.ecoapp.GameObject;
import com.ecoapp.app.maslab.ecoapp.GameObjectHandler;
import com.ecoapp.app.maslab.ecoapp.LeafIndicator;
import com.ecoapp.app.maslab.ecoapp.MainGameView;
import com.ecoapp.app.maslab.ecoapp.Paints;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.addButtonX;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.addButtonY;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.baseSize;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.decorButtonX;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.decorButtonY;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.middleX;
import static com.ecoapp.app.maslab.ecoapp.SizeManager.middleY;

/**
 * Created by masato on 2016/12/11.
 */

public class Garden extends GameObject implements GameCallback {

    public static final int THEME_1 = 1;
    public static final int THEME_2 = 2;
    public static final int THEME_VARIATION = 2;
    public static final int SCENE_MAIN = 90;
    public static final int SCENE_EDIT = 91;
    public static final int SCENE_KEEP_EDIT = 87;
    public static final int OK_PRESSED = 63;
    public static final int CANCEL_PRESSED = 37;
    public static final int EDIT_END = 126;
    public static final int KEEP_EDIT_START = 532;
    private final double A = (Math.sqrt(2)/2)/(Math.sqrt(41/8));
    private final float baseBottom = (float) (baseSize * Math.sqrt(2)/2) + middleY;
    private List<GardenItem> items;
    private int scene;
    private int theme;
    private Paint basePaint;
    private File savePath;
    private GardenItem editingItem;
    private RoundButton ok;
    private RoundButton cancel;
    private GameCallback listener;

    private float[] testClip;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    public List<GardenItem> getItems(){
        return items;
    }

    public void addGardenItem(GardenItem item){
        items.add(item);
        Collections.sort(items);
        DataManager.saveGarden(items,savePath,theme);
    }

    public void save(){
        Collections.sort(items);
        DataManager.saveGarden(items,savePath,theme);
    }

    public void addEdit(GardenItem item){
        editingItem = item;
        item.setCx(middleX);
        item.setCy(middleY);
        item.setActiveHandleEvent(new int[]{SCENE_EDIT});
        items.add(item);
        scene = SCENE_EDIT;
    }

    public void keepEdit(GardenItem item){

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void handleEvent(int x, int y, int action) {
        if(items == null){
            return;
        }
        for(int i = 0;i<items.size();i++){
            if(items.get(i).isHandleEventActive(scene)) {
                items.get(i).handleEvent(x,y,action);
            }
        }
        if(scene == SCENE_EDIT && scene == SCENE_KEEP_EDIT){
            ok.handleEvent(x,y,action);
            cancel.handleEvent(x,y,action);
        }
        if(checkIfPointInBase(x,y) == 1 && action == MotionEvent.ACTION_DOWN){
            listener.gameCallBack(KEEP_EDIT_START);
            scene = SCENE_KEEP_EDIT;
        }
    }

    @Override
    public void render(Canvas canvas) {
        drawBase(canvas);
        if(items == null){
            return;
        }
        for(int i = 0;i<items.size();i++){
            if(items.get(i).isRenderActive(scene)) {
                items.get(i).render(canvas);
            }
        }
        if(scene == SCENE_EDIT && scene == SCENE_KEEP_EDIT){
            ok.render(canvas);
            cancel.render(canvas);
            if(testClip != null) {
                //canvas.drawLine(editingItem.getCx(), testClip[1], editingItem.getCx(), testClip[0], Paints.AMCUnPurchasable);
            }
        }
    }

    private void drawBase(Canvas canvas){
        canvas.save();
        canvas.translate(middleX,middleY);
        canvas.rotate(45);
        canvas.skew(-0.5f , -0.5f);
        canvas.drawRect(0,0,baseSize,baseSize,basePaint);
        canvas.restore();
    }

    @Override
    public void tick() {
        if(items == null){
            return;
        }
        for(int i = 0;i<items.size();i++){
            if(items.get(i).isTickActive(scene)){
                items.get(i).tick();
            }
        }
        ok.tick();
        cancel.tick();
        if(scene == SCENE_EDIT){
            if(checkIfInBase(editingItem)){
                ok.setButtonEnabled();
            }else{
                ok.setButtonDisabled();
            }
            testClip = getClip(editingItem.getCx());
        }
    }

    private boolean checkIfInBase(GardenItem item){
        float left = item.getCx();
        float top = item.getCy();
        float right = left + item.getcWidth();
        float bottom = top + item.getcHeight();
        int result = 0;
        result += checkIfPointInBase(left,top);
        result += checkIfPointInBase(right,top);
        result += checkIfPointInBase(left,bottom);
        result += checkIfPointInBase(right,bottom);
        if(result == 4){
            return true;
        }else{
            return false;
        }
    }

    private int checkIfPointInBase(float x,float y){
        float[] clip = getClip(x);
        if(clip[0] < y && y < clip[1]){
            return 1;
        }else{
            return 0;
        }
    }

    private float[] getClip(float x){
        float clip;
        if(x < middleX){
            clip = (float) ((middleX - x) * A);
        }else{
            clip = (float) ((x - middleX) * A);
        }
        return new float[]{middleY + clip,baseBottom - clip};
    }

    public Garden(GameObjectHandler handler, Context context, File savePath){
        handler.addGameObject(this);
        this.savePath = savePath;
        this.theme = DataManager.getTheme(savePath);
        GardenBitmaps.loadBitmaps(context,theme);
        init();
    }

    public Garden(GameObjectHandler handler, Context context, File savePath,int theme){
        GardenBitmaps.loadBitmaps(context,theme);
        handler.addGameObject(this);
        this.savePath = savePath;
        this.theme = theme;
        DataManager.saveTheme(savePath,theme);
        DataManager.getTheme(savePath);
        init();
    }

    private void init(){
        setPaint();
        setHandleEventScenes(new int[]{MainGameView.SCENE_ON_MAIN,MainGameView.SCENE_ON_EDIT_GARDEN});
        items = DataManager.loadGardenData(savePath);
        DataManager.getTheme(savePath);
        scene = SCENE_MAIN;
        ok = new RoundButton(addButtonX,addButtonY, Bitmaps.okButton,Bitmaps.okButtonPressed,Bitmaps.okButtonDisabled,OK_PRESSED,null);
        ok.setGameCallBack(this);
        cancel = new RoundButton(decorButtonX,decorButtonY,Bitmaps.cancelButton,Bitmaps.cancelButtonPressed,null,CANCEL_PRESSED,null);
        cancel.setGameCallBack(this);
    };

    private void setPaint(){
        switch(theme){
            default:
            case THEME_1:
                basePaint = Paints.base_green;
                break;
            case THEME_2:
                basePaint = Paints.base_desert;
                break;
        }
    }

    @Override
    public void gameCallBack(int code) {
        switch(code){
            case OK_PRESSED:
                if(scene == SCENE_EDIT) {
                    editingItem.setActiveHandleEvent(new int[]{GameObject.DONT_HANDLE});
                    save();
                    int howMuch = DataManager.getGardenItemCost(theme, editingItem.getId());
                    LeafIndicator.leaves -= howMuch;
                    editingItem = null;
                    scene = SCENE_MAIN;
                    listener.gameCallBack(EDIT_END);
                }else{

                }
                break;
            case CANCEL_PRESSED:
                if(scene == SCENE_EDIT) {
                    items.remove(editingItem);
                    editingItem = null;
                    scene = SCENE_MAIN;
                    listener.gameCallBack(EDIT_END);
                }else{

                }
                break;
        }
    }
}
