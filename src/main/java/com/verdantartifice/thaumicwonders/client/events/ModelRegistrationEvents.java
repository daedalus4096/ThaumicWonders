package com.verdantartifice.thaumicwonders.client.events;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.init.InitBlocks;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ThaumicWonders.MODID)
public class ModelRegistrationEvents {
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event) {
        registerItemBlockModels();
    }

    private static void registerItemBlockModels() {
        for (ItemBlock itemBlock : InitBlocks.ITEM_BLOCKS) {
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
        }
    }
}
