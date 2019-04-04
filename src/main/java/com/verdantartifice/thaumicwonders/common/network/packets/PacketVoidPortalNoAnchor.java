package com.verdantartifice.thaumicwonders.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketVoidPortalNoAnchor implements IMessage {
    public PacketVoidPortalNoAnchor() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketVoidPortalNoAnchor, IMessage> {
        @Override
        public IMessage onMessage(PacketVoidPortalNoAnchor message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        @SideOnly(Side.CLIENT)
        private void handle(PacketVoidPortalNoAnchor message, MessageContext ctx) {
            EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
            entityPlayer.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + I18n.format("event.void_portal.no_anchor")), true);
        }
    }
}
