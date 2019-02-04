package com.verdantartifice.thaumicwonders.common.init;

import java.util.HashSet;
import java.util.Set;

import com.verdantartifice.thaumicwonders.common.blocks.devices.BlockEverburningUrn;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileEverburningUrn;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class InitBlocks {
    public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<ItemBlock>();
    
    public static void initBlocks(IForgeRegistry<Block> forgeRegistry) {
        registerBlock(forgeRegistry, new BlockEverburningUrn());
    }
    
    private static void registerBlock(IForgeRegistry<Block> forgeRegistry, Block block) {
        forgeRegistry.register(block);
        InitBlocks.ITEM_BLOCKS.add(new ItemBlock(block));
    }
    
    public static void initItemBlocks(IForgeRegistry<Item> forgeRegistry) {
        for (ItemBlock itemBlock : InitBlocks.ITEM_BLOCKS) {
            Block block = itemBlock.getBlock();
            itemBlock.setRegistryName(block.getRegistryName());
            forgeRegistry.register(itemBlock);
        }
    }
    
    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileEverburningUrn.class, new ResourceLocation("thaumicwonders", "TileEverburningUrn"));
    }
}
