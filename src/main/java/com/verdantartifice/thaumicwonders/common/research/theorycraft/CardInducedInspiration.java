package com.verdantartifice.thaumicwonders.common.research.theorycraft;

import com.verdantartifice.thaumicwonders.common.tiles.devices.IResearchEngine;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileInspirationEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.research.theorycraft.ResearchTableData;

public class CardInducedInspiration extends AbstractResearchEngineCard {
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
    protected int getResearchAmount() {
        return this.amount;
    }
    
    @Override
    protected Class<? extends IResearchEngine> getEngineTileClass() {
        return TileInspirationEngine.class;
    }
    
    @Override
    public boolean initialize(EntityPlayer player, ResearchTableData data) {
        if (!super.initialize(player, data)) {
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
    public String getLocalizedName() {
        return new TextComponentTranslation("card.induced_inspiration.name", new Object[0]).getUnformattedText();
    }

    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.induced_inspiration.text", new Object[] { Integer.valueOf(this.amount), TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.").append(this.category).toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
    }
    
}
