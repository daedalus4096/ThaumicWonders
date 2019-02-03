package com.verdantartifice.thaumicwonders.common.events;

import com.verdantartifice.thaumicwonders.common.init.InitBlocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistrationEvents {
    @SubscribeEvent
    public static void regsiterBlocks(RegistryEvent.Register<Block> event) {
        InitBlocks.initBlocks(event.getRegistry());
        InitBlocks.initTileEntities();
    }
}
