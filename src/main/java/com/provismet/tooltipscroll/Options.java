package com.provismet.tooltipscroll;

import com.provismet.tooltipscroll.config.OSLTooltipConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.ornithemc.osl.config.api.serdes.SerializationSettings;
import net.ornithemc.osl.config.api.serdes.config.ConfigSerializer;
import net.ornithemc.osl.config.api.serdes.config.ConfigSerializers;
import net.ornithemc.osl.core.api.json.JsonFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Options {

    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("tooltipscroll.json");

    public static boolean canScroll = true;
    public static boolean useWASD = false;
    public static boolean startOnTop = false;
    public static boolean resetOnUnlock = true;
    public static boolean useLShift = true;
    public static boolean invertXScroll = false;
    public static boolean invertYScroll = false;
    public static boolean matrixMode = false;

    public static void saveJSON() {
        OSLTooltipConfig config = OSLTooltipConfig.INSTANCE;
        config.canScroll.set(canScroll);
        config.useWASD.set(useWASD);
        config.startOnTop.set(startOnTop);
        config.resetOnUnlock.set(resetOnUnlock);
        config.useLShift.set(useLShift);
        config.invertXScroll.set(invertXScroll);
        config.invertYScroll.set(invertYScroll);
        config.matrixCompatibilityMode.set(matrixMode);
        config.scrollSpeed.set(ScrollTracker.scrollSize);
        config.keyboardScrollSpeed.set(ScrollTracker.scrollSizeKeyboard);
        config.scrollSmoothness.set(ScrollTracker.smoothnessModifier);

        try {
            ConfigSerializer<JsonFile> serializer = ConfigSerializers.get(OSLTooltipConfig.SERIALIZER_TYPE);
            serializer.serialize(config, SerializationSettings.forFile(), OSLTooltipConfig.SERIALIZER_TYPE.open(FILE));
        }
        catch (IOException e) {
            TooltipScrollClient.LOGGER.error("Encountered error whilst trying to save config JSON.", e);
        }
    }

    public static void readJSON() {
        OSLTooltipConfig config = OSLTooltipConfig.INSTANCE;
        config.load();
        config.resetAll();

        if (Files.exists(FILE)) {
            try {
                ConfigSerializer<JsonFile> serializer = ConfigSerializers.get(OSLTooltipConfig.SERIALIZER_TYPE);
                serializer.deserialize(config, SerializationSettings.forFile(), OSLTooltipConfig.SERIALIZER_TYPE.open(FILE));
            }
            catch (IOException e) {
                TooltipScrollClient.LOGGER.error("Encountered error whilst trying to read config JSON.", e);
            }
        }
        else {
            TooltipScrollClient.LOGGER.info("Failed to find TooltipScroll config, constructing default.");
        }

        canScroll = config.canScroll.get();
        useWASD = config.useWASD.get();
        startOnTop = config.startOnTop.get();
        resetOnUnlock = config.resetOnUnlock.get();
        useLShift = config.useLShift.get();
        invertXScroll = config.invertXScroll.get();
        invertYScroll = config.invertYScroll.get();
        matrixMode = config.matrixCompatibilityMode.get();
        ScrollTracker.scrollSize = Math.max(1, config.scrollSpeed.get());
        ScrollTracker.scrollSizeKeyboard = Math.max(1, config.keyboardScrollSpeed.get());
        ScrollTracker.smoothnessModifier = Math.clamp(config.scrollSmoothness.get(), 0.05, 1.0);

        saveJSON();
    }
}