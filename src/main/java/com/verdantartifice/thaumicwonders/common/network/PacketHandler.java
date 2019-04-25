package com.verdantartifice.thaumicwonders.common.network;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketLocalizedMessage;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketPrimalDestroyerHungerFull;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketTileToClient;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketTileToServer;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketTimewinderAction;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketTimewinderUsed;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketVoidPortalNoAnchor;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketVoidPortalNoWorld;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ThaumicWonders.MODID);
    
    private static int packetId = 0;
    
    private static int nextId() {
        return packetId++;
    }
    
    public static void registerMessages() {
        INSTANCE.registerMessage(PacketTimewinderAction.Handler.class, PacketTimewinderAction.class, nextId(), Side.SERVER);
        INSTANCE.registerMessage(PacketTimewinderUsed.Handler.class, PacketTimewinderUsed.class, nextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketPrimalDestroyerHungerFull.Handler.class, PacketPrimalDestroyerHungerFull.class, nextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketVoidPortalNoAnchor.Handler.class, PacketVoidPortalNoAnchor.class, nextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketVoidPortalNoWorld.Handler.class, PacketVoidPortalNoWorld.class, nextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketTileToServer.Handler.class, PacketTileToServer.class, nextId(), Side.SERVER);
        INSTANCE.registerMessage(PacketTileToClient.Handler.class, PacketTileToClient.class, nextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketLocalizedMessage.Handler.class, PacketLocalizedMessage.class, nextId(), Side.CLIENT);
    }
}
