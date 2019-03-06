package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

public class InitResearch {
    public static void initResearch() {
        ResearchCategories.registerCategory("THAUMIC_WONDERS", "FIRSTSTEPS", new AspectList(), new ResourceLocation(ThaumicWonders.MODID,"textures/gui/wonder_chest.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/misc" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/alchemy" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/infusion" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/eldritch" ));
    }
}
