package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.common.tiles.devices.TileVoidBeacon;

public class InitVoidBeacon {
    public static void init() {
        TileVoidBeacon.initRegistry();
        registerEntries();
    }
    
    private static void registerEntries() {
        TileVoidBeacon.registerOreDict("stone");
        TileVoidBeacon.registerOreDict("cobblestone");
    }
}
