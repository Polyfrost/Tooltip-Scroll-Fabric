package com.provismet.tooltipscroll.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.provismet.tooltipscroll.Options;
import com.provismet.tooltipscroll.ScrollTracker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.platform.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = Screen.class)
public abstract class ScreenMixin {

    @WrapOperation(method = "handleInputs", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;handleMouse()V"))
    private void tooltipscroll$scrollTooltip(Screen screen, Operation<Void> original) {
        ScrollTracker.handleScreenMouse(() -> original.call(screen));
    }

    @Inject(method = "renderTooltip(Ljava/util/List;II)V", at = @At("HEAD"))
    private void tooltipscroll$applyTracker(List<String> text, int mouseX, int mouseY, CallbackInfo ci) {
        ScrollTracker.unlock();
        ScrollTracker.update();
        ScrollTracker.setItem(text);
    }

    @Inject(
            method = "renderTooltip(Ljava/util/List;II)V",
            at = @At(value = "CONSTANT", args = "intValue=-267386864")
    )
    private void tooltipscroll$editXY(
            List<String> text, int mouseX, int mouseY, CallbackInfo ci,
            @Local(ordinal = 3) LocalIntRef effectiveX,
            @Local(ordinal = 4) LocalIntRef effectiveY
    ) {
        if (Options.matrixMode) return;

        effectiveX.set(effectiveX.get() + ScrollTracker.getXOffset());
        effectiveY.set(effectiveY.get() + ScrollTracker.getYOffset());

        if (Options.startOnTop && !ScrollTracker.hasMoved()) {
            int originalY = effectiveY.get();
            if (effectiveY.get() < 4) {
                effectiveY.set(4);
                ScrollTracker.setInitialYOffset(4 - originalY);
            }
        }
    }

    @Inject(method = "renderTooltip(Ljava/util/List;II)V", at = @At("HEAD"))
    private void tooltipscroll$headMatrices(List<String> text, int mouseX, int mouseY, CallbackInfo ci) {
        if (!Options.matrixMode) return;

        GlStateManager.pushMatrix();
        GlStateManager.translated(ScrollTracker.getXOffset(), ScrollTracker.getYOffset(), 0.0F);
    }

    @Inject(method = "renderTooltip(Ljava/util/List;II)V", at = @At("RETURN"))
    private void tooltipscroll$tailMatrices(List<String> text, int mouseX, int mouseY, CallbackInfo ci) {
        if (!Options.matrixMode) return;

        GlStateManager.popMatrix();
    }
}