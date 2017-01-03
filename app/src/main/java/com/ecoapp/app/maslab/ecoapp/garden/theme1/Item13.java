package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item13 extends GardenItem {

    public static int howMuch = 200;

    @Override
    public void tick() {

    }

    public Item13() {
        id = "13";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon13");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/3;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }
}
