package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;

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
    }
}
