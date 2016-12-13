package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/11.
 */

public class Item2 extends GardenItem {

    public static int howMuch = 70;

    @Override
    public void tick() {

    }

    public Item2() {
        id = "2";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon2");
        setBitmapDimen();
        cWidth = bitmapWidth * 5/10;
        cHeight = cWidth * 2/5;
        gapX = (bitmapWidth - cWidth)/2;
        gapY = bitmapHeight - cHeight;
    }

}
