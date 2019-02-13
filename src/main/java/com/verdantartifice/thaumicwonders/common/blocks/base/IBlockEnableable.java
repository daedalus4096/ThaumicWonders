package com.verdantartifice.thaumicwonders.common.blocks.base;

import net.minecraft.block.properties.PropertyBool;

public interface IBlockEnableable {
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");

    default boolean getEnableableDefault() {
        return true;
    }
}
