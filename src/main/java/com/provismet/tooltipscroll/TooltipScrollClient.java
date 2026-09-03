package com.provismet.tooltipscroll;

import net.ornithemc.osl.keybinds.api.KeybindEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ClientModInitializer;

public class TooltipScrollClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Tooltip Scroll");
    public static final String MODID = "tooltipscroll";

    public static boolean hasCloth() {
        try {
            Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onInitializeClient () {
        Options.readJSON();
        KeybindEvents.REGISTER_KEYBINDS.register(TooltipScrollKeybinds::init);
    }
}
