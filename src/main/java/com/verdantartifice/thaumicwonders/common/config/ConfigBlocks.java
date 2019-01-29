package com.verdantartifice.thaumicwonders.common.config;

import com.verdantartifice.thaumicwonders.common.blocks.BlocksTW;
import com.verdantartifice.thaumicwonders.common.blocks.devices.BlockLavaJug;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileLavaJug;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ConfigBlocks {
    public static void initBlocks(IForgeRegistry<Block> forgeRegistry) {
        BlocksTW.everburningUrn = registerBlock(new BlockLavaJug());
    }
    
    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileLavaJug.class, "thaumicwonders:TileLavaJug");
    }
    
    private static Block registerBlock(Block block) {
        return registerBlock(block, new ItemBlock(block));
    }
    
    private static Block registerBlock(Block block, ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        // TODO register model with proxy
        return block;
    }
}
