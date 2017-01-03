package com.ecoapp.app.maslab.ecoapp.garden.theme1;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/24.
 */

public class Item10 extends GardenItem {

    public static int howMuch = 150;

    @Override
    public void tick() {

    }

    public Item10() {
        id = "10";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon10");
        setBitmapDimen();
        cWidth = bitmapWidth * 2/5;
        cHeight = cWidth/3;
        gapX = bitmapWidth/2 - cWidth/2;
        gapY = bitmapHeight - cHeight;
    }

}
