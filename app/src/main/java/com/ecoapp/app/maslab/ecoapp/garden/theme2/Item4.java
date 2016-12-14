package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/14.
 */

public class Item4 extends GardenItem {

    public static int howMuch = 400;

    @Override
    public void tick() {

    }

    public Item4() {
        id = "4";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon4");
        setBitmapDimen();
        cWidth = bitmapWidth * 8/10;
        cHeight = bitmapHeight * 9/10;
        gapX = bitmapWidth * 1/10;
        gapY = bitmapHeight * 1/20;
    }
}
