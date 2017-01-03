package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item11 extends GardenItem {

    public static int howMuch = 600;

    @Override
    public void tick() {

    }

    public Item11() {
        id = "11";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon11");
        setBitmapDimen();
        cWidth = bitmapWidth;
        cHeight = cWidth/2;
        gapX = 0;
        gapY = bitmapHeight - cHeight ;
    }
}
