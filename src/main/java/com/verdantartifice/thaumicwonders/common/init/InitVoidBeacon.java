package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.common.tiles.devices.TileVoidBeacon;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class InitVoidBeacon {
    public static void init() {
        TileVoidBeacon.initRegistry();
        registerEntries();
    }
    
    private static void registerEntries() {
        TileVoidBeacon.registerOreDict("oreLapis");
        TileVoidBeacon.registerOreDict("oreDiamond");
        TileVoidBeacon.registerOreDict("oreRedstone");
        TileVoidBeacon.registerOreDict("oreEmerald");
        TileVoidBeacon.registerOreDict("oreQuartz");
        TileVoidBeacon.registerOreDict("oreIron");
        TileVoidBeacon.registerOreDict("oreGold");
        TileVoidBeacon.registerItemStack(new ItemStack(Blocks.COAL_ORE));
        TileVoidBeacon.registerOreDict("glowstone");
        
        TileVoidBeacon.registerOreDict("oreCopper");
        TileVoidBeacon.registerOreDict("oreTin");
        TileVoidBeacon.registerOreDict("oreSilver");
        TileVoidBeacon.registerOreDict("oreLead");
        
        TileVoidBeacon.registerOreDict("oreUranium");
        TileVoidBeacon.registerOreDict("oreRuby");
        TileVoidBeacon.registerOreDict("oreGreenSapphire");
        TileVoidBeacon.registerOreDict("oreSapphire");
        
        TileVoidBeacon.registerOreDict("stone");
        TileVoidBeacon.registerOreDict("stoneGranite");
        TileVoidBeacon.registerOreDict("stoneDiorite");
        TileVoidBeacon.registerOreDict("stoneAndesite");
        TileVoidBeacon.registerOreDict("cobblestone");
        TileVoidBeacon.registerOreDict("dirt");
        TileVoidBeacon.registerItemStack(new ItemStack(Blocks.DIRT, 1, 2));
        TileVoidBeacon.registerOreDict("sand");
        TileVoidBeacon.registerOreDict("grass");
    }
}
