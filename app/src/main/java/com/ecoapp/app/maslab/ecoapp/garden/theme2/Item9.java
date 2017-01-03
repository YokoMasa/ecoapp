package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item9 extends GardenItem{

    public static int howMuch = 100;

    @Override
    public void tick() {

    }

    public Item9() {
        id = "09";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon09");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/7;
        cHeight = cWidth/2;
        gapX = bitmapWidth/2 - cWidth * 4/3;
        gapY = bitmapHeight - cHeight;
    }
}
