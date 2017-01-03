package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2017/01/02.
 */

public class Item14 extends GardenItem {

    public static int howMuch = 70;

    @Override
    public void tick() {

    }

    public Item14() {
        id = "14";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon14");
        setBitmapDimen();
        cWidth = bitmapWidth * 1/6;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }
}
