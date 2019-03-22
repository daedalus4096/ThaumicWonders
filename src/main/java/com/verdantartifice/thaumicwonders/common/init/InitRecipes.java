package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.BlocksTW;
import com.verdantartifice.thaumicwonders.common.crafting.recipes.RecipeDisjunctionClothUse;
import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class InitRecipes {
    private static ResourceLocation defaultGroup = new ResourceLocation("");
    
    public static void initRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        initNormalRecipes(forgeRegistry);
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
    }
    
    private static void initNormalRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        forgeRegistry.register(new RecipeDisjunctionClothUse());
    }
    
    private static void initArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "dimensional_ripper"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_DIMENSIONAL_RIPPER",
                250,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.ENTROPY, 3),
                BlocksTW.DIMENSIONAL_RIPPER,
                new Object[] {
                        "BPB",
                        "VAV",
                        "VMV",
                        Character.valueOf('B'), "plateBrass",
                        Character.valueOf('P'), Ingredient.fromItem(ItemsTC.primordialPearl),
                        Character.valueOf('V'), "plateVoid",
                        Character.valueOf('A'), new ItemStack(ItemsTC.turretPlacer, 1, 2),
                        Character.valueOf('M'), new ItemStack(ItemsTC.mechanismComplex)
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "inspiration_engine"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_INSPIRATION_ENGINE",
                50,
                new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1),
                BlocksTW.INSPIRATION_ENGINE,
                new Object[] {
                        "BRB",
                        "VMV",
                        "SZS",
                        Character.valueOf('B'), "plateBrass",
                        Character.valueOf('R'), new ItemStack(ItemsTC.morphicResonator),
                        Character.valueOf('V'), new ItemStack(ItemsTC.visResonator),
                        Character.valueOf('M'), new ItemStack(ItemsTC.mechanismSimple),
                        Character.valueOf('S'), new ItemStack(BlocksTC.stoneArcane),
                        Character.valueOf('Z'), new ItemStack(ItemsTC.brain)
                }
        ));
    }

    private static void initCrucibleRecipes() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_soul_sand"), new CrucibleRecipe(
                "TWOND_NETHER_HEDGE",
                new ItemStack(Blocks.SOUL_SAND),
                new ItemStack(Blocks.SAND),
                new AspectList().add(Aspect.SOUL, 3).add(Aspect.EARTH, 3).add(Aspect.TRAP, 1)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_ghast_tear"), new CrucibleRecipe(
                "TWOND_NETHER_HEDGE",
                new ItemStack(Items.GHAST_TEAR, 2),
                new ItemStack(Items.GHAST_TEAR),
                new AspectList().add(Aspect.SOUL, 10).add(Aspect.ALCHEMY, 10).add(Aspect.UNDEAD, 5)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_wither_skull"), new CrucibleRecipe(
                "TWOND_NETHER_HEDGE",
                new ItemStack(Items.SKULL, 1, 1),
                new ItemStack(Items.SKULL, 1, 0),
                new AspectList().add(Aspect.UNDEAD, 10).add(Aspect.DEATH, 10).add(Aspect.SOUL, 10).add(Aspect.DARKNESS, 5)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_end_stone"), new CrucibleRecipe(
                "TWOND_END_HEDGE",
                new ItemStack(Blocks.END_STONE),
                new ItemStack(Blocks.STONE),
                new AspectList().add(Aspect.EARTH, 5).add(Aspect.DARKNESS, 5)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_chorus_fruit"), new CrucibleRecipe(
                "TWOND_END_HEDGE",
                new ItemStack(Items.CHORUS_FRUIT),
                new ItemStack(Items.APPLE),
                new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.SENSES, 5).add(Aspect.PLANT, 5)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "hedge_ender_pearl"), new CrucibleRecipe(
                "TWOND_END_HEDGE",
                new ItemStack(Items.ENDER_PEARL, 2),
                new ItemStack(Items.ENDER_PEARL),
                new AspectList().add(Aspect.MOTION, 15).add(Aspect.ELDRITCH, 10)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "disjunction_cloth"), new CrucibleRecipe(
                "TWOND_DISJUNCTION_CLOTH",
                new ItemStack(ItemsTW.DISJUNCTION_CLOTH),
                new ItemStack(ItemsTC.fabric),
                new AspectList().add(Aspect.MAGIC, 40).add(Aspect.VOID, 40).add(Aspect.AURA, 20)
        ));
    }
    
    private static void initInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "everburning_urn"), new InfusionRecipe(
                "TWOND_EVERBURNING_URN",
                new ItemStack(BlocksTW.EVERBURNING_URN),
                4,
                new AspectList().add(Aspect.FIRE, 40).add(Aspect.EARTH, 20).add(Aspect.ENERGY, 10).add(Aspect.CRAFT, 10),
                new ItemStack(BlocksTC.everfullUrn),
                new Object[] {
                        new ItemStack(Items.NETHERBRICK),
                        new ItemStack(Items.NETHERBRICK),
                        new ItemStack(Items.NETHERBRICK),
                        new ItemStack(Items.LAVA_BUCKET),
                        ThaumcraftApiHelper.makeCrystal(Aspect.FIRE),
                        new ItemStack(Blocks.OBSIDIAN)
                }
        ));
        
        ItemStack destroyer = new ItemStack(ItemsTW.PRIMAL_DESTROYER);
        EnumInfusionEnchantment.addInfusionEnchantment(destroyer, EnumInfusionEnchantment.ESSENCE, 3);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_destroyer"), new InfusionRecipe(
                "TWOND_PRIMAL_DESTROYER",
                destroyer,
                8,
                new AspectList().add(Aspect.FIRE, 100).add(Aspect.ENTROPY, 50).add(Aspect.VOID, 50).add(Aspect.AVERSION, 100).add(Aspect.ELDRITCH, 75).add(Aspect.DARKNESS, 75).add(Aspect.DEATH, 100),
                Ingredient.fromItem(ItemsTC.voidSword),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(Items.NETHER_STAR),
                        "plateVoid",
                        "plateVoid",
                        ThaumcraftApiHelper.makeCrystal(Aspect.FIRE),
                        ThaumcraftApiHelper.makeCrystal(Aspect.FIRE)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "flying_carpet"), new InfusionRecipe(
                "TWOND_FLYING_CARPET",
                new ItemStack(ItemsTW.FLYING_CARPET),
                6,
                new AspectList().add(Aspect.FLIGHT, 150).add(Aspect.MOTION, 100).add(Aspect.AIR, 100).add(Aspect.MAGIC, 50).add(Aspect.ENERGY, 50),
                new ItemStack(Blocks.CARPET, 1, 32767),
                new Object[] {
                        new ItemStack(BlocksTC.levitator),
                        new ItemStack(Items.SADDLE),
                        new ItemStack(ItemsTC.visResonator),
                        ThaumcraftApiHelper.makeCrystal(Aspect.AIR),
                        ThaumcraftApiHelper.makeCrystal(Aspect.AIR)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "timewinder"), new InfusionRecipe(
                "TWOND_TIMEWINDER",
                new ItemStack(ItemsTW.TIMEWINDER),
                7,
                new AspectList().add(Aspect.ELDRITCH, 100).add(Aspect.DARKNESS, 100).add(Aspect.LIGHT, 100),
                new ItemStack(Items.CLOCK),
                new Object[] {
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.ENDER_PEARL),
                        new ItemStack(ItemsTC.quicksilver),
                        new ItemStack(ItemsTC.celestialNotes, 1, 5),
                        new ItemStack(ItemsTC.celestialNotes, 1, 6),
                        new ItemStack(ItemsTC.celestialNotes, 1, 7),
                        new ItemStack(ItemsTC.celestialNotes, 1, 8),
                        new ItemStack(ItemsTC.celestialNotes, 1, 9),
                        new ItemStack(ItemsTC.celestialNotes, 1, 10),
                        new ItemStack(ItemsTC.celestialNotes, 1, 11),
                        new ItemStack(ItemsTC.celestialNotes, 1, 12),
                        new ItemStack(ItemsTC.celestialNotes, 1, 0),
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "madness_engine"), new InfusionRecipe(
                "TWOND_MADNESS_ENGINE",
                new ItemStack(BlocksTW.MADNESS_ENGINE),
                6,
                new AspectList().add(Aspect.ELDRITCH, 150).add(Aspect.MIND, 100).add(Aspect.MECHANISM, 100).add(Aspect.AURA, 50),
                new ItemStack(BlocksTW.INSPIRATION_ENGINE),
                new Object[] {
                        "plateVoid",
                        "plateVoid",
                        "plateThaumium",
                        "plateThaumium",
                        new ItemStack(ItemsTC.mind, 1, 1),
                        new ItemStack(Items.ENDER_PEARL)
                }
        ));
    }
}
