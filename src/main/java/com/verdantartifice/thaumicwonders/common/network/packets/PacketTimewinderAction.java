package com.verdantartifice.thaumicwonders.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.aura.AuraHelper;

public class PacketTimewinderAction implements IMessage {
    private int targetPhase;
    
    public PacketTimewinderAction() {
        this.targetPhase = -1;
    }
    
    public PacketTimewinderAction(int targetPhase) {
        this.targetPhase = targetPhase;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.targetPhase = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.targetPhase);
    }
    
    public static class Handler implements IMessageHandler<PacketTimewinderAction, IMessage> {
        @Override
        public IMessage onMessage(PacketTimewinderAction message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }
        
        private void handle(PacketTimewinderAction message, MessageContext ctx) {
            EntityPlayerMP entityPlayer = ctx.getServerHandler().player;
            World world = entityPlayer.getEntityWorld();
            long currentTime = world.getWorldTime();
            long dayStart = currentTime - (currentTime % 24000L);

            if (message.targetPhase >= 0 && message.targetPhase <= 7) {
                // Advance to rise of specified moon phase
                int currentMoonPhase = (int)(currentTime / 24000L % 8L + 8L) % 8;   // from WorldProvider.getMoonPhase
                int daysToAdvance;
                if (message.targetPhase != currentMoonPhase) {
                    daysToAdvance = (message.targetPhase - currentMoonPhase + 8) % 8;
                } else if ((currentTime % 24000L) <= 13500L) {
                    daysToAdvance = 0;
                } else {
                    daysToAdvance = 8;
                }
                long targetTime = dayStart + (daysToAdvance * 24000L) + 13500L;
                world.setWorldTime(targetTime);
                AuraHelper.polluteAura(world, entityPlayer.getPosition(), 1.0F, true);
            } else if (message.targetPhase == 8) {
                // Advance to next sunrise
                long targetTime = dayStart + 24000L;
                world.setWorldTime(targetTime);
                AuraHelper.polluteAura(world, entityPlayer.getPosition(), 1.0F, true);
            }
        }
    }
}
