package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.client.gui.GuiTimewinder;
import com.verdantartifice.thaumicwonders.common.misc.GuiIds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ProxyGUI {
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
        case GuiIds.TIMEWINDER:
            return new GuiTimewinder();
        default:
            return null;
        }
    }

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
        default:
            return null;
        }
    }
}
