package com.verdantartifice.thaumicwonders.client.events;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.client.config.KeyBindings;
import com.verdantartifice.thaumicwonders.common.network.PacketHandler;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketControlFlyingCarpet;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ThaumicWonders.MODID)
public class InputEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PacketHandler.INSTANCE.sendToServer(new PacketControlFlyingCarpet(
            KeyBindings.carpetForwardKey.isKeyDown(),
            KeyBindings.carpetBackwardKey.isKeyDown(),
            KeyBindings.carpetLeftKey.isKeyDown(),
            KeyBindings.carpetRightKey.isKeyDown()
        ));
    }
}
