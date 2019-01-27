package com.verdantartifice.thaumicwonders;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThaumicWonders.MODID, name = ThaumicWonders.NAME, version = ThaumicWonders.VERSION, dependencies = ThaumicWonders.DEPENDENCIES)
public class ThaumicWonders
{
    public static final String MODID = "thaumicwonders";
    public static final String NAME = "Thaumic Wonders";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required-after:thaumcraft";

    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
