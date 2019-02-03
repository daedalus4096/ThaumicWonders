package com.verdantartifice.thaumicwonders.common.init;

import java.util.HashSet;
import java.util.Set;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.devices.BlockEverburningUrn;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileEverburningUrn;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class InitBlocks {
    private static final Set<Block> BLOCKS = new HashSet<Block>();
    
    public static void initBlocks(IForgeRegistry<Block> forgeRegistry) {
        registerBlock(forgeRegistry, new BlockEverburningUrn());
    }
    
    private static void registerBlock(IForgeRegistry<Block> forgeRegistry, Block block) {
        forgeRegistry.register(block);
        InitBlocks.BLOCKS.add(block);
    }
    
    public static void initItemBlocks(IForgeRegistry<Item> forgeRegistry) {
        for (Block block : InitBlocks.BLOCKS) {
            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(block.getRegistryName());
            forgeRegistry.register(itemBlock);
            ThaumicWonders.proxy.registerModel(itemBlock);
        }
    }
    
    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileEverburningUrn.class, new ResourceLocation("thaumicwonders", "TileEverburningUrn"));
    }
}
