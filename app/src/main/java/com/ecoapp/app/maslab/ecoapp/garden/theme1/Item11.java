package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/30.
 */

public class Item11 extends GardenItem {

    public static int howMuch = 200;

    @Override
    public void tick() {

    }

    public Item11() {
        id = "11";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon11");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/3;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }
}
