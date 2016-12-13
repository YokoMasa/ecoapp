package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/13.
 */

public class Item7 extends GardenItem {

    public static int howMuch = 50;

    @Override
    public void tick() {

    }

    public Item7() {
        id = "7";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon7");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/3;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }
}
