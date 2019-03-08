package com.verdantartifice.thaumicwonders.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTimewinder extends GuiScreen {    
    public GuiTimewinder() {
        super();
    }
    
    @Override
    public void initGui() {
        ThaumicWonders.LOGGER.info("Opening Timewinder GUI!");
        if (this.mc == null) {
            this.mc = Minecraft.getMinecraft();
        }
        this.buttonList.clear();
        // FIXME fix x and y positions to center GUI
        this.buttonList.add(new GuiSelectorButton(0, 120, 88, 16, 16, I18n.format("thaumicwonders.gui.timewinder.0")));
        this.buttonList.add(new GuiSelectorButton(1, 143, 97, 16, 16, I18n.format("thaumicwonders.gui.timewinder.1")));
        this.buttonList.add(new GuiSelectorButton(2, 152, 120, 16, 16, I18n.format("thaumicwonders.gui.timewinder.2")));
        this.buttonList.add(new GuiSelectorButton(3, 143, 143, 16, 16, I18n.format("thaumicwonders.gui.timewinder.3")));
        this.buttonList.add(new GuiSelectorButton(4, 120, 152, 16, 16, I18n.format("thaumicwonders.gui.timewinder.4")));
        this.buttonList.add(new GuiSelectorButton(8, 120, 120, 16, 16, I18n.format("thaumicwonders.gui.timewinder.8")));    // out of order for z-ordering
        this.buttonList.add(new GuiSelectorButton(5, 97, 143, 16, 16, I18n.format("thaumicwonders.gui.timewinder.5")));
        this.buttonList.add(new GuiSelectorButton(6, 88, 120, 16, 16, I18n.format("thaumicwonders.gui.timewinder.6")));
        this.buttonList.add(new GuiSelectorButton(7, 97, 97, 16, 16, I18n.format("thaumicwonders.gui.timewinder.7")));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        ThaumicWonders.LOGGER.info("Clicked button {}!", button.id);
    }
    
    private class GuiSelectorButton extends GuiButton {
        private final ResourceLocation TEXTURE = new ResourceLocation(ThaumicWonders.MODID, "textures/gui/gui_timewinder.png");
        
        public GuiSelectorButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
            super(buttonId, x, y, widthIn, heightIn, buttonText);
        }
        
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                this.hovered = ((mouseX >= this.x) && (mouseY >= this.y) && (mouseX < this.x + this.width) && (mouseY < this.y + this.height));
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                mc.renderEngine.bindTexture(this.TEXTURE);
                GL11.glPushMatrix();
                if (this.hovered) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                } else {
                    GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
                }
                this.drawTexturedModalRect(this.x, this.y, this.x, this.y, 16, 16);
                GL11.glPopMatrix();
                if (this.hovered) {
                    this.drawString(mc.fontRenderer, this.displayString, this.x + 19, this.y + 4, 16777215);
                }
                this.mouseDragged(mc, mouseX, mouseY);
            }
        }
    }
}
