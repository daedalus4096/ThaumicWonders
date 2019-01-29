package com.verdantartifice.thaumicwonders;

import com.verdantartifice.thaumicwonders.common.config.ConfigBlocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Registrar {
    @SubscribeEvent
    public static void regsiterBlocks(RegistryEvent.Register<Block> event) {
        ConfigBlocks.initBlocks(event.getRegistry());
        ConfigBlocks.initTileEntities();
    }
}
