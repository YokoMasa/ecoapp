package com.ecoapp.app.maslab.ecoapp.garden.theme4;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/23.
 */

public class Item2 extends GardenItem {


    public static int howMuch = 150;

    @Override
    public void tick() {

    }

    public Item2() {
        id = "2";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon2");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/4;
        cHeight = cWidth/2;
        gapX = bitmapWidth/2 - cWidth * 4/3;
        gapY = bitmapHeight - cHeight;
    }
}
