package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/14.
 */

public class Item2 extends GardenItem {

    public static int howMuch = 100;

    @Override
    public void tick() {

    }

    public Item2() {
        id = "2";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon2");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/2;
        cHeight = cWidth/2;
        gapX = bitmapWidth/2 - cWidth;
        gapY = bitmapHeight - cHeight;
    }
}
