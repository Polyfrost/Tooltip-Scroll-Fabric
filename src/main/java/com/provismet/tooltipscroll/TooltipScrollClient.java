package com.provismet.tooltipscroll;

import com.provismet.tooltipscroll.config.OSLTooltipConfig;
import net.ornithemc.osl.config.api.ConfigManager;
import net.ornithemc.osl.keybinds.api.KeybindEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;

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
    public void initClient() {
        ConfigManager.register(OSLTooltipConfig.INSTANCE);
        KeybindEvents.REGISTER_KEYBINDS.register(TooltipScrollKeybinds::init);
    }
}
