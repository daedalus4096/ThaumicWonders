package com.verdantartifice.thaumicwonders.common.network.packets;

import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketControlFlyingCarpet implements IMessage {
    private boolean forwardDown;
    private boolean backwardDown;
    private boolean leftDown;
    private boolean rightDown;
    
    public PacketControlFlyingCarpet() {}
    
    public PacketControlFlyingCarpet(boolean forwardDown, boolean backwardDown, boolean leftDown, boolean rightDown) {
        this.forwardDown = forwardDown;
        this.backwardDown = backwardDown;
        this.leftDown = leftDown;
        this.rightDown = rightDown;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.forwardDown = buf.readBoolean();
        this.backwardDown = buf.readBoolean();
        this.leftDown = buf.readBoolean();
        this.rightDown = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.forwardDown);
        buf.writeBoolean(this.backwardDown);
        buf.writeBoolean(this.leftDown);
        buf.writeBoolean(this.rightDown);
    }
    
    public static class Handler implements IMessageHandler<PacketControlFlyingCarpet, IMessage> {
        @Override
        public IMessage onMessage(PacketControlFlyingCarpet message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }
        
        private void handle(PacketControlFlyingCarpet packet, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            Entity ridingEntity = player.getRidingEntity();
            if (ridingEntity != null && ridingEntity instanceof EntityFlyingCarpet) {
                ((EntityFlyingCarpet)ridingEntity).updateInputs(packet.forwardDown, packet.backwardDown, packet.leftDown, packet.rightDown);
            }
        }
    }
}
