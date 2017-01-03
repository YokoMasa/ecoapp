package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item8 extends GardenItem {
    public static int howMuch = 70;

    @Override
    public void tick() {

    }

    public Item8() {
        id = "08";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon08");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/6;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }
}
