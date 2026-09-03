package com.provismet.tooltipscroll.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.tooltipscroll.ScrollTracker;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    // Reset the tracker whenever a GUI window closes.
	@Inject (method = "openScreen", at = @At("HEAD"))
	public void resetTrackerOnScreenClose(Screen screen, CallbackInfo info) {
		if (Minecraft.getInstance().gui != null && screen == null) {
			ScrollTracker.reset();
		}
	}
}
