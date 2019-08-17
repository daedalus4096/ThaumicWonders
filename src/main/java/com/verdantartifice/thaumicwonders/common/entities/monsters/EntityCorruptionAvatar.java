package com.verdantartifice.thaumicwonders.common.entities.monsters;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.entities.projectile.EntityGolemOrb;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
import thaumcraft.common.lib.utils.EntityUtils;

public class EntityCorruptionAvatar extends EntityThaumcraftBoss implements IRangedAttackMob, IEldritchMob, ITaintedMob {
    public EntityCorruptionAvatar(World world) {
        super(world);
        this.setSize(0.75F, 2.25F);
        this.experienceValue = 50;
    }
    
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 40, 20.0F));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
        // FIXME Use real health
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
    }
    
    @Override
    public boolean isOnSameTeam(Entity el) {
        return (el instanceof IEldritchMob) || (el instanceof ITaintedMob);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        // FIXME Do real attack (flux fireball)
        if (this.canEntityBeSeen(target)) {
            swingArm(getActiveHand());
            getLookHelper().setLookPosition(target.posX, target.getEntityBoundingBox().minY + target.height / 2.0F, target.posZ, 30.0F, 30.0F);
            
            EntityGolemOrb blast = new EntityGolemOrb(this.world, this, target, true);
            blast.posX += blast.motionX / 2.0D;
            blast.posZ += blast.motionZ / 2.0D;
            blast.setPosition(blast.posX, blast.posY, blast.posZ);
            
            double d0 = target.posX - this.posX;
            double d1 = target.getEntityBoundingBox().minY + target.height / 2.0F - (this.posY + this.height / 2.0F);
            double d2 = target.posZ - this.posZ;
            
            blast.shoot(d0, d1 + 2.0D, d2, 0.66F, 3.0F);
            
            playSound(SoundsTC.egattack, 1.0F, 1.0F + this.rand.nextFloat() * 0.1F);
            this.world.spawnEntity(blast);
        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {}
    
    @Override
    protected void updateAITasks() {
        if (!this.world.isRemote) {
            // Generate flux, 1/sec
            if (this.ticksExisted % 5 == 0) {
                AuraHelper.polluteAura(this.world, this.getPosition().up(), 0.25F, true);
            }
            
            // Regenerate based on local flux
            if (this.ticksExisted % 40 == 0) {
                float flux = Math.min(100.0F, AuraHelper.getFlux(this.world, this.getPosition()));
                int amp = (int)(0.5F * MathHelper.sqrt(flux));
                this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, amp, false, false));
            }
            
            // Generate flux phage aura
            List<EntityPlayer> playersNearby = EntityUtils.getEntitiesInRange(this.world, this.getPosition(), this, EntityPlayer.class, 10.0D);
            for (EntityPlayer player : playersNearby) {
                player.addPotionEffect(new PotionEffect(PotionInfectiousVisExhaust.instance, 300, 0));
            }
            
            // TODO Spawn taint seeds
            // TODO Empower if near rift
            // TODO Teleport near attack target if too far away, then explode
        }
        super.updateAITasks();
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityCorruptionAvatar) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsTC.egidle;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsTC.egdeath;
    }
    
    @Override
    public int getTalkInterval() {
        return 500;
    }
}
