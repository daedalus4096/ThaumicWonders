package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.entities.EntityHexamitePrimed;
import com.verdantartifice.thaumicwonders.common.entities.EntityPrimalArrow;
import com.verdantartifice.thaumicwonders.common.entities.EntityVoidPortal;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

public class InitEntities {
    public static void initEntities(IForgeRegistry<EntityEntry> iForgeRegistry) {
        int id = 0;
        
        EntityEntry flyingCarpetEntry = EntityEntryBuilder.create()
                .entity(EntityFlyingCarpet.class)
                .id(new ResourceLocation(ThaumicWonders.MODID, "flying_carpet"), id++)
                .name("flying_carpet")
                .tracker(64, 1, true)
                .build();
        iForgeRegistry.register(flyingCarpetEntry);
        
        EntityEntry voidPortalEntry = EntityEntryBuilder.create()
                .entity(EntityVoidPortal.class)
                .id(new ResourceLocation(ThaumicWonders.MODID, "void_portal"), id++)
                .name("void_portal")
                .tracker(64, 20, false)
                .build();
        iForgeRegistry.register(voidPortalEntry);
        
        EntityEntry hexamitePrimedEntry = EntityEntryBuilder.create()
                .entity(EntityHexamitePrimed.class)
                .id(new ResourceLocation(ThaumicWonders.MODID, "hexamite_primed"), id++)
                .name("hexamite_primed")
                .tracker(64, 1, true)
                .build();
        iForgeRegistry.register(hexamitePrimedEntry);
        
        EntityEntry primalArrowEntry = EntityEntryBuilder.create()
                .entity(EntityPrimalArrow.class)
                .id(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow"), id++)
                .name("primal_arrow")
                .tracker(64, 1, true)
                .build();
        iForgeRegistry.register(primalArrowEntry);
    }
}
