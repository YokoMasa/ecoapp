package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/11.
 */

public class Item3 extends GardenItem {

    public static int howMuch = 30;

    @Override
    public void tick() {

    }

    public Item3() {
        id = "03";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon03");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/4;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }

}
