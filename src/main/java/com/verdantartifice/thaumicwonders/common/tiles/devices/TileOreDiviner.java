package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.awt.Color;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.common.misc.OreHelper;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;

public class TileOreDiviner extends TileTW implements ITickable {
    public static final int SCAN_RANGE = 20;
    
    protected BlockPos target = null;
    protected int counter = -1;
    
    public void setTarget(@Nullable BlockPos newTarget) {
        this.target = newTarget;
        this.counter = 0;
    }

    @Override
    public void update() {
        if (this.world.isRemote && this.target != null && this.counter % 44 == 0) {
            Color color = this.getOreColor(this.target);
            float r = color.getRed() / 255.0F;
            float g = color.getGreen() / 255.0F;
            float b = color.getBlue() / 255.0F;
            float colorAvg = (r + g + b) / 3.0F;
            
            FXGeneric particle = new FXGeneric(this.world, target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            particle.setMaxAge(44);
            particle.setRBGColorF(r, g, b);
            particle.setAlphaF(new float[] { 0.0F, 1.0F, 0.8F, 0.0F });
            particle.setParticles(240, 15, 1);
            particle.setGridSize(16);
            particle.setLoop(true);
            particle.setScale(new float[] { 9.0F });
            particle.setLayer(colorAvg < 0.25F ? 3 : 2);
            particle.setRotationSpeed(0.0F);
            ParticleEngine.addEffect(this.world, particle);
        }
        this.counter++;
    }
    
    protected Color getOreColor(BlockPos targetPos) {
        ItemStack stack = ItemStack.EMPTY;
        IBlockState state = world.getBlockState(targetPos);
        if (state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.BEDROCK) {
            Item item = Item.getItemFromBlock(state.getBlock());
            stack = new ItemStack(item, 1, item.getHasSubtypes() ? state.getBlock().getMetaFromState(state) : 0);
        }
        if (stack.isEmpty() || stack.getItem() == null) {
            return new Color(0xC0C0C0);
        } else {
            for (String name : OreHelper.getOreNames(stack)) {
                if (name != null) {
                    name = name.toUpperCase();
                    if (name.contains("IRON")) {
                        return new Color(0xD8AF93);
                    } else if (name.contains("COAL")) {
                        return new Color(0x101010);
                    } else if (name.contains("REDSTONE")) {
                        return new Color(0xFF0000);
                    } else if (name.contains("GOLD")) {
                        return new Color(0xFCEE4B);
                    } else if (name.contains("LAPIS")) {
                        return new Color(0x1445BC);
                    } else if (name.contains("DIAMOND")) {
                        return new Color(0x5DECF5);
                    } else if (name.contains("EMERALD")) {
                        return new Color(0x17DD62);
                    } else if (name.contains("QUARTZ")) {
                        return new Color(0xE5DED5);
                    } else if (name.contains("SILVER")) {
                        return new Color(0xDAD9FD);
                    } else if (name.contains("LEAD")) {
                        return new Color(0x6B6B6B);
                    } else if (name.contains("TIN")) {
                        return new Color(0xEFEFFB);
                    } else if (name.contains("COPPER")) {
                        return new Color(0xFD9C55);
                    } else if (name.contains("AMBER")) {
                        return new Color(0xFDB325);
                    } else if (name.contains("CINNABAR")) {
                        return new Color(0x9B0508);
                    }
                }
            }
            return new Color(0xC0C0C0);
        }
    }
}
