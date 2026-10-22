package com.provismet.tooltipscroll.mixin;

import com.provismet.tooltipscroll.ScrollTracker;
import net.minecraft.client.gui.screen.inventory.menu.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {
    @Inject(method = "handleMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/inventory/menu/CreativeInventoryScreen$CreativePlayerMenu;scrollItems(F)V"))
    private void tooltipscroll$consumeWheel(CallbackInfo ci) {
        ScrollTracker.consumeWheel();
    }
}
