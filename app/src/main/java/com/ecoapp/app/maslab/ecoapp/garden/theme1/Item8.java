package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/13.
 */

public class Item8 extends GardenItem {

    public static int howMuch = 50;

    @Override
    public void tick() {

    }

    public Item8() {
        id = "8";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon8");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/3;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }
}
