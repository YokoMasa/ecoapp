package com.ecoapp.app.maslab.ecoapp;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import static com.ecoapp.app.maslab.ecoapp.SizeManager.*;

/**
 * Created by masato on 2016/12/07.
 */

public abstract class ContentHandler extends GameObject{

    protected List<GameObject> contents = new ArrayList<GameObject>();
    protected GameCallback listener;

    public void setGameCallBack(GameCallback listener){
        this.listener = listener;
    }

    public void addContent(GameObject content){
        contents.add(content);
    }

    public void removeContent(GameObject content){
        contents.remove(content);
    }

    public abstract void shrinkContents();

    @Override
    public void render(Canvas canvas) {
        canvas.saveLayer(listMenuPaddingX,listMenuPaddingY,width - listMenuPaddingX,
                height - listMenuPaddingY,null,Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(listMenuPaddingX,listMenuPaddingY);
        renderContents(canvas);
        canvas.restore();
    }

    protected abstract void renderContents(Canvas canvas);

}
