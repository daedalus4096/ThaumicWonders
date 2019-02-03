package com.verdantartifice.thaumicwonders;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

import org.apache.logging.log4j.Logger;

import com.verdantartifice.thaumicwonders.common.config.ConfigRecipes;
import com.verdantartifice.thaumicwonders.proxy.IProxyTW;

@Mod(modid = ThaumicWonders.MODID, name = ThaumicWonders.NAME, version = ThaumicWonders.VERSION, dependencies = ThaumicWonders.DEPENDENCIES)
public class ThaumicWonders
{
    public static final String MODID = "thaumicwonders";
    public static final String NAME = "Thaumic Wonders";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required-after:thaumcraft";

    public static Logger LOGGER;
    
    @SidedProxy(clientSide="com.verdantartifice.thaumicwonders.proxy.ClientProxy", serverSide="com.verdantartifice.thaumicwonders.proxy.ServerProxy")
    public static IProxyTW proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ConfigRecipes.initializeRecipes();
        ResearchCategories.registerCategory("THAUMIC_WONDERS", "FIRSTSTEPS", new AspectList(), new ResourceLocation("thaumcraft","textures/items/thaumonomicon.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicwonders", "research/misc" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicwonders", "research/alchemy" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicwonders", "research/infusion" ));
    }
}
