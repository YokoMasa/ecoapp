package com.ecoapp.app.maslab.ecoapp.garden.theme3;

import com.ecoapp.app.maslab.ecoapp.garden.GardenBitmaps;
import com.ecoapp.app.maslab.ecoapp.garden.GardenItem;

/**
 * Created by masato on 2016/12/22.
 */

public class Item4 extends GardenItem {

    public static int howMuch = 60;

    @Override
    public void tick() {

    }

    public Item4() {
        id = "04";
        mainBitmap = GardenBitmaps.getBitmap(id);
        icon = GardenBitmaps.getBitmap("icon04");
        setBitmapDimen();
        cWidth = bitmapWidth * 4/10;
        cHeight = cWidth/4;
        gapX = bitmapWidth/3;
        gapY = bitmapHeight - cHeight;
    }
}
