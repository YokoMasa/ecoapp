package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/13.
 */

public class Item9 extends GardenItem{

    public static int howMuch = 400;

    @Override
    public void tick() {

    }

    public Item9() {
        id = "09";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon09");
        setBitmapDimen();
        cWidth = bitmapWidth * 8/10;
        cHeight = bitmapHeight * 9/10;
        gapX = bitmapWidth * 1/10;
        gapY = bitmapHeight * 1/20;
    }
}
