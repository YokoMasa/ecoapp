package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item13 extends GardenItem {

    public static int howMuch = 100;

    @Override
    public void tick() {

    }

    public Item13() {
        id = "13";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon13");
        setBitmapDimen();
        cWidth = bitmapWidth/6;
        cHeight = cWidth/2;
        gapX = bitmapWidth - cWidth;
        gapY = bitmapHeight - cHeight ;
    }
}
