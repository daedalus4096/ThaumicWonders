package com.verdantartifice.thaumicwonders.common.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.BlocksTW;
import com.verdantartifice.thaumicwonders.common.crafting.recipes.RecipeDisjunctionClothUse;
import com.verdantartifice.thaumicwonders.common.crafting.recipes.RecipeFlyingCarpetDyes;
import com.verdantartifice.thaumicwonders.common.fluids.FluidQuicksilver;
import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.IngredientNBTTC;
import thaumcraft.api.crafting.Part;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

public class InitRecipes {
    private static ResourceLocation defaultGroup = new ResourceLocation("");
    
    public static void initRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        initNormalRecipes(forgeRegistry);
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
        initMultiblockRecipes();
        initSmelting();
    }
    
    private static void initMultiblockRecipes() {
        Part AS = new Part(BlocksTC.stoneArcane, new ItemStack(BlocksTW.PLACEHOLDER_ARCANE_STONE));
        Part OB = new Part(Blocks.OBSIDIAN, new ItemStack(BlocksTW.PLACEHOLDER_OBSIDIAN));
        Part IB = new Part(Blocks.IRON_BARS, "AIR");
        Part QS = new Part(BlocksTW.FLUID_QUICKSILVER, BlocksTW.CATALYZATION_CHAMBER, true);
        Part[][][] catalyzationChamberBlueprint = {
                {
                    { AS, OB, AS },
                    { OB, null, OB},
                    { AS, OB, AS }
                },
                {
                    { AS, OB, AS },
                    { OB, QS, OB },
                    { AS, IB, AS }
                },
                {
                    { AS, OB, AS },
                    { OB, OB, OB },
                    { AS, OB, AS }
                }
        };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("TWOND_CATALYZATION_CHAMBER@2", catalyzationChamberBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation(ThaumicWonders.MODID, "catalyzation_chamber"), new ThaumcraftApi.BluePrint(
                "TWOND_CATALYZATION_CHAMBER@2", 
                catalyzationChamberBlueprint, 
                new ItemStack[] {
                        new ItemStack(BlocksTC.stoneArcane, 12),
                        new ItemStack(Blocks.OBSIDIAN, 12),
                        new ItemStack(Blocks.IRON_BARS),
                        FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000))
                }
        ));
    }
    
    private static void initNormalRecipes(IForgeRegistry<IRecipe> forgeRegistry) {
        forgeRegistry.register(new RecipeDisjunctionClothUse());
        forgeRegistry.register(new RecipeFlyingCarpetDyes());
        
        ResourceLocation qsGroup = new ResourceLocation(ThaumicWonders.MODID, "quicksilver_bucket_group");
        shapelessOreDictRecipe("quicksilver_bucket", qsGroup, FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000)), new Object[] { 
                Items.BUCKET, new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver), 
                new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver), 
                new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver) 
        });
        shapelessOreDictRecipe("quicksilver_bucket_deconstruct", qsGroup, new ItemStack(ItemsTC.quicksilver, 8), 
                new Object[] { new IngredientNBTTC(FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000))) });
    }
    
    private static IRecipe shapelessOreDictRecipe(@Nonnull String name, @Nullable ResourceLocation group, @Nonnull ItemStack result, Object[] inputs) {
        IRecipe recipe = new ShapelessOreRecipe(group, result, inputs);
        recipe.setRegistryName(new ResourceLocation(ThaumicWonders.MODID, name));
        GameData.register_impl(recipe);
        return recipe;
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
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "portal_anchor"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_VOID_PORTAL@2",
                150,
                new AspectList().add(Aspect.AIR, 3).add(Aspect.ORDER, 3).add(Aspect.ENTROPY, 3),
                BlocksTW.PORTAL_ANCHOR,
                new Object[] {
                        "VPV",
                        "PRP",
                        "VPV",
                        Character.valueOf('V'), "plateVoid",
                        Character.valueOf('P'), new ItemStack(Items.ENDER_PEARL),
                        Character.valueOf('R'), new ItemStack(ItemsTC.morphicResonator)
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "hexamite"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_HEXAMITE",
                125,
                new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2),
                BlocksTW.HEXAMITE,
                new Object[] {
                        "AVA",
                        "VGV",
                        "AVA",
                        Character.valueOf('A'), new ItemStack(ItemsTC.alumentum),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.FLUX)),
                        Character.valueOf('G'), new ItemStack(Items.GUNPOWDER)
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "bone_bow"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_BONE_BOW",
                50,
                new AspectList().add(Aspect.AIR, 2).add(Aspect.ENTROPY, 2),
                ItemsTW.BONE_BOW,
                new Object[] {
                        " BS",
                        "BVS",
                        " BS",
                        Character.valueOf('B'), new ItemStack(Items.BONE),
                        Character.valueOf('S'), new ItemStack(Items.STRING),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_air"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 0),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.AIR))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_earth"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 1),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.EARTH))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_fire"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 2),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.FIRE))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_water"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 3),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.WATER))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_order"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 4),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.ORDER))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "primal_arrow_entropy"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_PRIMAL_ARROWS",
                10,
                new AspectList(),
                new ItemStack(ItemsTW.PRIMAL_ARROW, 9, 5),
                new Object[] {
                        "AAA",
                        "AVA",
                        "AAA",
                        Character.valueOf('A'), new ItemStack(Items.ARROW),
                        Character.valueOf('V'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY))
                }
        ));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "flux_distiller"), new ShapedArcaneRecipe(
                defaultGroup,
                "TWOND_FLUX_DISTILLER",
                750,
                new AspectList().add(Aspect.AIR, 8).add(Aspect.WATER, 8).add(Aspect.ORDER, 8),
                BlocksTW.FLUX_DISTILLER,
                new Object[] {
                        "VLV",
                        "MCM",
                        "VAV",
                        Character.valueOf('V'), "plateVoid",
                        Character.valueOf('M'), ItemsTC.mechanismComplex,
                        Character.valueOf('L'), BlocksTC.condenserlattice,
                        Character.valueOf('C'), BlocksTC.condenser,
                        Character.valueOf('A'), BlocksTC.metalAlchemicalAdvanced
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
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "alchemist_stone"), new CrucibleRecipe(
                "TWOND_CATALYZATION_CHAMBER",
                new ItemStack(ItemsTW.ALCHEMIST_STONE),
                new ItemStack(Items.DIAMOND),
                new AspectList().add(Aspect.METAL, 50).add(Aspect.ORDER, 50).add(Aspect.ALCHEMY, 10)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumicWonders.MODID, "transmuter_stone"), new CrucibleRecipe(
                "TWOND_TRANSMUTER_STONE",
                new ItemStack(ItemsTW.TRANSMUTER_STONE),
                new ItemStack(ItemsTW.ALCHEMIST_STONE),
                new AspectList().add(Aspect.EXCHANGE, 50).add(Aspect.ALCHEMY, 10)
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
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "portal_generator"), new InfusionRecipe(
                "TWOND_VOID_PORTAL",
                new ItemStack(BlocksTW.PORTAL_GENERATOR),
                8,
                new AspectList().add(Aspect.ELDRITCH, 150).add(Aspect.MOTION, 150).add(Aspect.EXCHANGE, 100),
                new ItemStack(BlocksTW.PORTAL_ANCHOR),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(BlocksTC.mirror),
                        new ItemStack(Items.ENDER_PEARL)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "flux_capacitor"), new InfusionRecipe(
                "TWOND_FLUX_CAPACITOR",
                new ItemStack(BlocksTW.FLUX_CAPACITOR),
                6,
                new AspectList().add(Aspect.FLUX, 50).add(Aspect.AURA, 50).add(Aspect.VOID, 50),
                new ItemStack(BlocksTC.visBattery),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(BlocksTC.crystalTaint),
                        new ItemStack(ItemsTC.visResonator),
                        new ItemStack(BlocksTC.condenserlattice)
                }
        ));
        
        List<Object> ingredients = new ArrayList<Object>();
        ingredients.add(Ingredient.fromItem(ItemsTC.primordialPearl));
        ingredients.add(new ItemStack(ItemsTC.clusters, 1, 0));
        ingredients.add(new ItemStack(ItemsTC.clusters, 1, 1));
        if (OreDictionary.doesOreNameExist("oreCopper") && !OreDictionary.getOres("oreCopper", false).isEmpty()) {
            ingredients.add(new ItemStack(ItemsTC.clusters, 1, 2));
        }
        if (OreDictionary.doesOreNameExist("oreTin") && !OreDictionary.getOres("oreTin", false).isEmpty()) {
            ingredients.add(new ItemStack(ItemsTC.clusters, 1, 3));
        }
        if (OreDictionary.doesOreNameExist("oreSilver") && !OreDictionary.getOres("oreSilver", false).isEmpty()) {
            ingredients.add(new ItemStack(ItemsTC.clusters, 1, 4));
        }
        if (OreDictionary.doesOreNameExist("oreLead") && !OreDictionary.getOres("oreLead", false).isEmpty()) {
            ingredients.add(new ItemStack(ItemsTC.clusters, 1, 5));
        }
        ingredients.add(new ItemStack(ItemsTC.clusters, 1, 6));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "alienist_stone"), new InfusionRecipe(
                "TWOND_ALIENIST_STONE",
                new ItemStack(ItemsTW.ALIENIST_STONE),
                7,
                new AspectList().add(Aspect.METAL, 100).add(Aspect.FLUX, 100).add(Aspect.ALCHEMY, 25),
                new ItemStack(ItemsTW.ALCHEMIST_STONE),
                ingredients.toArray()
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "meteorb"), new InfusionRecipe(
                "TWOND_METEORB",
                new ItemStack(BlocksTW.METEORB),
                6,
                new AspectList().add(Aspect.AIR, 100).add(Aspect.WATER, 100).add(Aspect.ENERGY, 100).add(Aspect.ELDRITCH, 50),
                new ItemStack(Items.ENDER_PEARL),
                new Object[] {
                        new ItemStack(BlocksTC.stoneArcane),
                        new ItemStack(BlocksTC.slabArcaneStone),
                        new ItemStack(BlocksTC.tube),
                        new ItemStack(BlocksTC.stoneArcane),
                        new ItemStack(BlocksTC.slabArcaneStone),
                        new ItemStack(BlocksTC.tube),
                        new ItemStack(BlocksTC.stoneArcane),
                        new ItemStack(BlocksTC.slabArcaneStone),
                        new ItemStack(BlocksTC.tube),
                        new ItemStack(BlocksTC.stoneArcane),
                        new ItemStack(BlocksTC.slabArcaneStone),
                        new ItemStack(Blocks.STONE_BUTTON)
                }
        ));
        
        List<Object> divinerIngredients = new ArrayList<Object>();
        divinerIngredients.add("oreIron");
        divinerIngredients.add("oreGold");
        if (OreDictionary.doesOreNameExist("oreCopper") && !OreDictionary.getOres("oreCopper", false).isEmpty()) {
            ingredients.add("oreCopper");
        }
        if (OreDictionary.doesOreNameExist("oreTin") && !OreDictionary.getOres("oreTin", false).isEmpty()) {
            ingredients.add("oreTin");
        }
        if (OreDictionary.doesOreNameExist("oreSilver") && !OreDictionary.getOres("oreSilver", false).isEmpty()) {
            ingredients.add("oreSilver");
        }
        if (OreDictionary.doesOreNameExist("oreLead") && !OreDictionary.getOres("oreLead", false).isEmpty()) {
            ingredients.add("oreLead");
        }
        divinerIngredients.add("oreCoal");
        divinerIngredients.add("oreRedstone");
        divinerIngredients.add("oreLapis");
        divinerIngredients.add("oreDiamond");
        divinerIngredients.add("oreEmerald");
        divinerIngredients.add("oreCinnabar");
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "ore_diviner"), new InfusionRecipe(
                "TWOND_ORE_DIVINER",
                new ItemStack(BlocksTW.ORE_DIVINER),
                6,
                new AspectList().add(Aspect.SENSES, 100).add(Aspect.EARTH, 50).add(Aspect.METAL, 50).add(Aspect.MAGIC, 50),
                new ItemStack(Blocks.QUARTZ_BLOCK),
                divinerIngredients.toArray()
        ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "void_fortress_helm"), new InfusionRecipe(
                "TWOND_VOID_FORTRESS_ARMOR",
                new ItemStack(ItemsTW.VOID_FORTRESS_HELM),
                8,
                new AspectList().add(Aspect.PROTECT, 45).add(Aspect.METAL, 45).add(Aspect.ELDRITCH, 50).add(Aspect.ENERGY, 25).add(Aspect.VOID, 25).add(Aspect.MAGIC, 25).add(Aspect.SENSES, 25).add(Aspect.UNDEAD, 40).add(Aspect.LIFE, 40),
                new ItemStack(ItemsTC.voidHelm),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        "plateVoid",
                        new ItemStack(ItemsTC.goggles, 1, 32767),
                        new ItemStack(Items.GHAST_TEAR),
                        new ItemStack(ItemsTC.salisMundus),
                        "leather"
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "void_fortress_chest"), new InfusionRecipe(
                "TWOND_VOID_FORTRESS_ARMOR",
                new ItemStack(ItemsTW.VOID_FORTRESS_CHEST),
                8,
                new AspectList().add(Aspect.PROTECT, 55).add(Aspect.METAL, 55).add(Aspect.ELDRITCH, 50).add(Aspect.ENERGY, 25).add(Aspect.VOID, 35).add(Aspect.MAGIC, 25),
                new ItemStack(ItemsTC.voidChest),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        "plateVoid",
                        "plateVoid",
                        "plateVoid",
                        new ItemStack(ItemsTC.salisMundus),
                        "leather"
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "void_fortress_legs"), new InfusionRecipe(
                "TWOND_VOID_FORTRESS_ARMOR",
                new ItemStack(ItemsTW.VOID_FORTRESS_LEGS),
                8,
                new AspectList().add(Aspect.PROTECT, 50).add(Aspect.METAL, 50).add(Aspect.ELDRITCH, 50).add(Aspect.ENERGY, 25).add(Aspect.VOID, 30).add(Aspect.MAGIC, 25),
                new ItemStack(ItemsTC.voidLegs),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        "plateVoid",
                        "plateVoid",
                        new ItemStack(ItemsTC.salisMundus),
                        "leather"
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "meaty_orb"), new InfusionRecipe(
                "TWOND_MEATY_ORB",
                new ItemStack(BlocksTW.MEATY_ORB),
                8,
                new AspectList().add(Aspect.WATER, 250).add(Aspect.LIFE, 250).add(Aspect.ELDRITCH, 250),
                new ItemStack(BlocksTW.METEORB),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(Items.BEEF),
                        new ItemStack(Items.PORKCHOP),
                        new ItemStack(Items.CHICKEN),
                        new ItemStack(Items.MUTTON),
                        new ItemStack(Items.RABBIT)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "structure_diviner"), new InfusionRecipe(
                "TWOND_STRUCTURE_DIVINER",
                new ItemStack(ItemsTW.STRUCTURE_DIVINER),
                6,
                new AspectList().add(Aspect.SENSES, 100).add(Aspect.MECHANISM, 50).add(Aspect.MAGIC, 50).add(Aspect.EARTH, 50),
                new ItemStack(Items.COMPASS),
                new Object[] {
                        new ItemStack(Items.ENDER_EYE),
                        new ItemStack(Items.EMERALD),
                        new ItemStack(Items.ENDER_EYE),
                        new ItemStack(Blocks.NETHER_BRICK),
                        new ItemStack(Items.ENDER_EYE),
                        new ItemStack(Items.PRISMARINE_CRYSTALS)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "night_vision_goggles"), new InfusionRecipe(
                "TWOND_NV_GOGGLES",
                new ItemStack(ItemsTW.NIGHT_VISION_GOGGLES),
                4,
                new AspectList().add(Aspect.SENSES, 50).add(Aspect.LIGHT, 50).add(Aspect.MAGIC, 25).add(Aspect.ENERGY, 25),
                new ItemStack(ItemsTC.goggles),
                new Object[] {
                        new ItemStack(Items.GOLDEN_CARROT),
                        "nitor",
                        ThaumcraftApiHelper.makeCrystal(Aspect.SENSES),
                        ThaumcraftApiHelper.makeCrystal(Aspect.SENSES)
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "void_beacon"), new InfusionRecipe(
                "TWOND_VOID_BEACON",
                new ItemStack(BlocksTW.VOID_BEACON),
                10,
                new AspectList().add(Aspect.ELDRITCH, 250).add(Aspect.VOID, 250).add(Aspect.MAGIC, 250).add(Aspect.FLUX, 200).add(Aspect.AIR, 100).add(Aspect.EARTH, 100).add(Aspect.FIRE, 100).add(Aspect.WATER, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
                new ItemStack(Blocks.BEACON),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid",
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        "plateVoid"
                }
        ));
        
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumicWonders.MODID, "cleansing_charm"), new InfusionRecipe(
                "TWOND_CLEANSING_CHARM",
                new ItemStack(ItemsTW.CLEANSING_CHARM),
                8,
                new AspectList().add(Aspect.MIND, 225).add(Aspect.ORDER, 225).add(Aspect.ELDRITCH, 150).add(Aspect.LIFE, 150),
                new ItemStack(Items.ENDER_PEARL),
                new Object[] {
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(Items.GOLD_INGOT),
                        new ItemStack(Items.GOLD_INGOT),
                        new ItemStack(Items.GOLD_INGOT)
                }
        ));
    }
    
    private static void initSmelting() {
        GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 0), new ItemStack(Items.IRON_INGOT, 3, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 1), new ItemStack(Items.GOLD_INGOT, 3, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 6), new ItemStack(ItemsTC.quicksilver, 3, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 7), new ItemStack(Items.QUARTZ, 3, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 8), new ItemStack(ItemsTC.ingots, 2, 1), 1.0F);
        
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 0), new ItemStack(Items.IRON_NUGGET, 1, 0));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 1), new ItemStack(Items.GOLD_NUGGET, 1, 0));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 6), new ItemStack(ItemsTC.nuggets, 1, 5));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 7), new ItemStack(ItemsTC.nuggets, 1, 9));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 8), new ItemStack(ItemsTC.nuggets, 1, 7));
        
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 32767), new ItemStack(ItemsTC.nuggets, 1, 10), 0.025F);
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 32767), ThaumcraftApiHelper.makeCrystal(Aspect.FLUX), 0.1F);

        if (OreDictionary.doesOreNameExist("ingotCopper") && !OreDictionary.getOres("ingotCopper", false).isEmpty()) {
            ItemStack stack = OreDictionary.getOres("ingotCopper", false).get(0);
            GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 2), new ItemStack(stack.getItem(), 3, stack.getItemDamage()), 1.0F);
            ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 2), new ItemStack(ItemsTC.nuggets, 1, 1));
        }
        if (OreDictionary.doesOreNameExist("ingotTin") && !OreDictionary.getOres("ingotTin", false).isEmpty()) {
            ItemStack stack = OreDictionary.getOres("ingotTin", false).get(0);
            GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 3), new ItemStack(stack.getItem(), 3, stack.getItemDamage()), 1.0F);
            ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 3), new ItemStack(ItemsTC.nuggets, 1, 2));
        }
        if (OreDictionary.doesOreNameExist("ingotSilver") && !OreDictionary.getOres("ingotSilver", false).isEmpty()) {
            ItemStack stack = OreDictionary.getOres("ingotSilver", false).get(0);
            GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 4), new ItemStack(stack.getItem(), 3, stack.getItemDamage()), 1.0F);
            ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 4), new ItemStack(ItemsTC.nuggets, 1, 3));
        }
        if (OreDictionary.doesOreNameExist("ingotLead") && !OreDictionary.getOres("ingotLead", false).isEmpty()) {
            ItemStack stack = OreDictionary.getOres("ingotLead", false).get(0);
            GameRegistry.addSmelting(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 5), new ItemStack(stack.getItem(), 3, stack.getItemDamage()), 1.0F);
            ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTW.ELDRITCH_CLUSTER, 1, 5), new ItemStack(ItemsTC.nuggets, 1, 4));
        }
    }
}
