package com.ecoapp.app.maslab.ecoapp.garden.theme4;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/23.
 */

public class Item3 extends GardenItem {

    public static int howMuch = 30;

    @Override
    public void tick() {

    }

    public Item3() {
        id = "3";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon3");
        setBitmapDimen();
        cWidth = bitmapWidth;
        cHeight = bitmapHeight;
        gapX = 0;
        gapY = 0;
    }
}
