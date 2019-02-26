package com.verdantartifice.thaumicwonders.client.config;

import org.lwjgl.input.Keyboard;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindings {
    public static KeyBinding carpetForwardKey;
    public static KeyBinding carpetBackwardKey;
    public static KeyBinding carpetLeftKey;
    public static KeyBinding carpetRightKey;
    
    private static final String KEY_CATEGORY = "key.categories." + ThaumicWonders.MODID;
    
    public static void init() {
        carpetForwardKey = new KeyBinding("key.carpet_forward", Keyboard.KEY_W, KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(carpetForwardKey);
        
        carpetBackwardKey = new KeyBinding("key.carpet_backward", Keyboard.KEY_S, KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(carpetBackwardKey);
        
        carpetLeftKey = new KeyBinding("key.carpet_left", Keyboard.KEY_A, KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(carpetLeftKey);
        
        carpetRightKey = new KeyBinding("key.carpet_right", Keyboard.KEY_D, KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(carpetRightKey);
    }
}
