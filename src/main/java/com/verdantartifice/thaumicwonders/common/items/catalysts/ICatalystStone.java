package com.verdantartifice.thaumicwonders.common.items.catalysts;

import net.minecraft.item.ItemStack;

public interface ICatalystStone {
    /**
     * Gets the refining result for the given input stack
     * @param input Refining input
     * @return Refining output
     */
    public ItemStack getRefiningResult(ItemStack input);
    
    /**
     * Gets the flux chance denominator for this stone
     * @return X, where the chance of flux is 1-in-X
     */
    public int getFluxChance();
    
    /**
     * Gets the int-encoded color of sparkles to be emitted after this stone operates
     * @return Sparkle color, as an int
     */
    public int getSparkleColor();
}
