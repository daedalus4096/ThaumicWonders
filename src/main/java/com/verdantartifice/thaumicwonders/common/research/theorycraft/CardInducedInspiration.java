package com.verdantartifice.thaumicwonders.common.research.theorycraft;

import com.verdantartifice.thaumicwonders.common.tiles.devices.TileInspirationEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class CardInducedInspiration extends TheorycraftCard {
    private String category = null;
    private int amount;
    
    @Override
    public NBTTagCompound serialize() {
        NBTTagCompound nbt = super.serialize();
        nbt.setString("cat", this.category);
        nbt.setInteger("amt", this.amount);
        return nbt;
    }
    
    @Override
    public void deserialize(NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.category = nbt.getString("cat");
        this.amount = nbt.getInteger("amt");
    }
    
    @Override
    public String getResearchCategory() {
        return this.category;
    }
    
    @Override
    public boolean isAidOnly() {
        return true;
    }
    
    @Override
    public boolean initialize(EntityPlayer player, ResearchTableData data) {
        // Do an initial check for a fueled engine and abort if not found
        if (this.findFueledEngine(data.table.getWorld(), data.table.getPos()) == null) {
            return false;
        }
        
        if (data.categoryTotals.size() < 1) {
            return false;
        }
        int highestValue = 0;
        String highestKey = "";
        for (String category : data.categoryTotals.keySet()) {
            int value = data.getTotal(category);
            if (value > highestValue) {
                highestValue = value;
                highestKey = category;
            }
        }
        this.category = highestKey;
        this.amount = (5 + (highestValue / 3));
        return true;
    }

    @Override
    public boolean activate(EntityPlayer player, ResearchTableData data) {
        // Re-check to make sure the engine is still there between initialize and now
        TileInspirationEngine engineTile = this.findFueledEngine(data.table.getWorld(), data.table.getPos());
        if (engineTile != null && engineTile.deductCost()) {
            data.addTotal(this.category, this.amount);
            data.bonusDraws += 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getInspirationCost() {
        return -1;
    }

    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.induced_inspiration.name", new Object[0]).getUnformattedText();
    }

    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.induced_inspiration.text", new Object[] { Integer.valueOf(this.amount), TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.").append(this.category).toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
    }
    
    private TileInspirationEngine findFueledEngine(World world, BlockPos tablePos) {
        BlockPos.PooledMutableBlockPos pmbp = BlockPos.PooledMutableBlockPos.retain();
        for (int i = -4; i <= 4; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -4; k <= 4; k++) {
                    pmbp.setPos(tablePos.getX() + i, tablePos.getY() + j, tablePos.getZ() + k);
                    TileEntity tile = world.getTileEntity(pmbp);
                    if (tile != null && tile instanceof TileInspirationEngine) {
                        TileInspirationEngine engineTile = (TileInspirationEngine)tile;
                        if (engineTile.isFueled()) {
                            pmbp.release();
                            return engineTile;
                        }
                    }
                }
            }
        }
        pmbp.release();
        return null;
    }
}
