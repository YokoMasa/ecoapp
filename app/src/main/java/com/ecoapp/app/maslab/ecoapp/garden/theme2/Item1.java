package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/14.
 */

public class Item1 extends GardenItem {

    public static int howMuch = 100;

    @Override
    public void tick() {

    }

    public Item1() {
        id = "1";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon1");
        setBitmapDimen();
        cWidth = bitmapWidth * 2/3;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }
}
