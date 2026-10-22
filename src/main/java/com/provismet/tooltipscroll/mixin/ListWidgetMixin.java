package com.provismet.tooltipscroll.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.provismet.tooltipscroll.ScrollTracker;
import net.minecraft.client.gui.widget.ListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ListWidget.class)
public abstract class ListWidgetMixin {
    @ModifyExpressionValue(method = "handleMouse", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false))
    private int tooltipscroll$consumeWheel(int wheel) {
        if (wheel != 0) ScrollTracker.consumeWheel();
        return wheel;
    }
}
