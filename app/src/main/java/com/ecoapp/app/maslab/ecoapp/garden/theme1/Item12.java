package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item12 extends GardenItem {

    public static int howMuch = 200;

    @Override
    public void tick() {

    }

    public Item12() {
        id = "12";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon12");
        setBitmapDimen();
        cWidth = bitmapWidth * 2/5;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }

}
