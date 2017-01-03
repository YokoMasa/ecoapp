package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item6 extends GardenItem {

    public static int howMuch = 100;

    @Override
    public void tick() {

    }

    public Item6() {
        id = "06";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon06");
        setBitmapDimen();
        cWidth = bitmapWidth * 5/10;
        cHeight = cWidth/4;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }

}
