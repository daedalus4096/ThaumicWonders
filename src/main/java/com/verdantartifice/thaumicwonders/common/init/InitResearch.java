package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.items.ItemsTW;
import com.verdantartifice.thaumicwonders.common.research.theorycraft.AidInspirationEngine;
import com.verdantartifice.thaumicwonders.common.research.theorycraft.AidMadnessEngine;
import com.verdantartifice.thaumicwonders.common.research.theorycraft.CardInducedInspiration;
import com.verdantartifice.thaumicwonders.common.research.theorycraft.CardInducedMadness;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.api.research.theorycraft.TheorycraftManager;

public class InitResearch {
    public static void initResearch() {
        initCategories();
        initResearchLocations();
        initTheorycraft();
        initScannables();
    }

    private static void initCategories() {
        ResearchCategories.registerCategory(
                "THAUMIC_WONDERS", 
                "FIRSTSTEPS", 
                new AspectList(), 
                new ResourceLocation(ThaumicWonders.MODID, "textures/gui/wonder_chest.png"), 
                new ResourceLocation(ThaumicWonders.MODID, "textures/gui/gui_research_back.jpg"), 
                new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png")
        );
    }

    private static void initResearchLocations() {
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/misc" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/alchemy" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/artifice" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/infusion" ));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWonders.MODID, "research/eldritch" ));
    }
    
    private static void initTheorycraft() {
        TheorycraftManager.registerAid(new AidInspirationEngine());
        TheorycraftManager.registerAid(new AidMadnessEngine());
        TheorycraftManager.registerCard(CardInducedInspiration.class);
        TheorycraftManager.registerCard(CardInducedMadness.class);
    }
    
    private static void initScannables() {
        ScanningManager.addScannableThing(new ScanItem("!TWOND_PRIMORDIAL_GRAIN", new ItemStack(ItemsTW.PRIMORDIAL_GRAIN)));
    }
}
