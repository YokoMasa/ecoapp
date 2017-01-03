package com.ecoapp.app.maslab.ecoapp.garden.theme2;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item7 extends GardenItem {

    public static int howMuch = 60;

    @Override
    public void tick() {

    }

    public Item7() {
        id = "07";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon07");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/8;
        cHeight = cWidth/2;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }

}
