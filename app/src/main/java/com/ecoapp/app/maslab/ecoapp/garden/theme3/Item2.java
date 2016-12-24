package com.ecoapp.app.maslab.ecoapp.garden.theme3;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/22.
 */

public class Item2 extends GardenItem {

    public static int howMuch = 60;

    @Override
    public void tick() {

    }

    public Item2() {
        id = "02";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon02");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/4;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }
}
