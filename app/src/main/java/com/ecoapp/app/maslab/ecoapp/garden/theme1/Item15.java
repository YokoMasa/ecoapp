package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item15 extends GardenItem {

    public static int howMuch = 50;

    @Override
    public void tick() {

    }

    public Item15() {
        id = "15";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon15");
        setBitmapDimen();
        cWidth = bitmapWidth * 9/10;
        cHeight = cWidth/5;
        gapX = 0;
        gapY = bitmapHeight - cHeight;
    }
}
