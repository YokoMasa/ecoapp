package com.ecoapp.app.maslab.ecoapp.garden.theme3;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/22.
 */

public class Item3 extends GardenItem {

    public static int howMuch = 70;

    @Override
    public void tick() {

    }

    public Item3() {
        id = "03";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon03");
        setBitmapDimen();
        cWidth = bitmapWidth * 7/10;
        cHeight = cWidth/3;
        gapX = bitmapWidth/5;
        gapY = bitmapHeight - cHeight;
    }
}
