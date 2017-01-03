package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item10 extends GardenItem {

    public static int howMuch = 30;

    @Override
    public void tick() {

    }

    public Item10() {
        id = "10";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon10");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/4;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }
}
