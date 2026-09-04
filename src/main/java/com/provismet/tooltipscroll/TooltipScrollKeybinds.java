package com.provismet.tooltipscroll;

import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.keybinds.api.KeybindRegistry;

public class TooltipScrollKeybinds {

    public static final String CATEGORY = "key.category.tooltipscroll.keys";

    public static final KeyBinding moveUp = KeybindRegistry.register(
            "key.tooltipscroll.moveUp",
            0,
            CATEGORY
    );
    public static final KeyBinding moveDown = KeybindRegistry.register(
            "key.tooltipscroll.moveDown",
            0,
            CATEGORY
    );
    public static final KeyBinding reset = KeybindRegistry.register(
            "key.tooltipscroll.reset",
            0,
            CATEGORY
    );
    public static final KeyBinding horizontal = KeybindRegistry.register(
            "key.tooltipscroll.horizontal",
            0,
            CATEGORY
    );

    public static void init() {}
}
