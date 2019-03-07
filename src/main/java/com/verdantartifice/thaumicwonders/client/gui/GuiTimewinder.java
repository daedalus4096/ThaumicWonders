package com.verdantartifice.thaumicwonders.client.gui;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTimewinder extends GuiScreen {    
    public GuiTimewinder() {
        super();
    }
    
    @Override
    public void initGui() {
        ThaumicWonders.LOGGER.info("Opening Timewinder GUI!");
        super.initGui();
    }
}
