package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/14.
 */

public class Item5 extends GardenItem {

    public static int howMuch = 70;

    @Override
    public void tick() {

    }

    public Item5() {
        id = "5";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon5");
        setBitmapDimen();
        cWidth = bitmapWidth * 7/10;
        cHeight = cWidth/3;
        gapX = bitmapWidth/5;
        gapY = bitmapHeight - cHeight;
    }
}
