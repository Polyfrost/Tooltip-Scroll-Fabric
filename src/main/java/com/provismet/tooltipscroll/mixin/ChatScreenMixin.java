package com.provismet.tooltipscroll.mixin;

import com.provismet.tooltipscroll.ScrollTracker;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Inject(method = "handleMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/chat/ChatGui;scroll(I)V"))
    private void tooltipscroll$consumeWheel(CallbackInfo ci) {
        ScrollTracker.consumeWheel();
    }
}
