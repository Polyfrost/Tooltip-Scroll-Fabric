package com.provismet.tooltipscroll.config;

import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.DoubleOption;
import net.ornithemc.osl.config.api.config.option.IntegerOption;
import net.ornithemc.osl.config.api.config.option.validator.IntegerValidators;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;
import net.ornithemc.osl.config.api.serdes.config.ConfigSerializers;
import net.ornithemc.osl.core.api.json.JsonFile;

public class OSLTooltipConfig extends BaseConfig {

    public static final FileSerializerType<JsonFile> SERIALIZER_TYPE = SerializerTypes.register("tooltipscroll_json", JsonFile::new);

    public static final OSLTooltipConfig INSTANCE = new OSLTooltipConfig();

    static {
        ConfigSerializers.register(SERIALIZER_TYPE, new FlatJsonConfigSerializer());
        INSTANCE.init();
    }

    public final BooleanOption canScroll = new BooleanOption("canScroll", "entrytooltip.tooltipscroll.canscroll", true);
    public final BooleanOption useWASD = new BooleanOption("useWASD", "entrytooltip.tooltipscroll.usewasd", false);
    public final BooleanOption startOnTop = new BooleanOption("startOnTop", "entrytooltip.tooltipscroll.startontop", false);
    public final BooleanOption resetOnUnlock = new BooleanOption("resetOnUnlock", "entrytooltip.tooltipscroll.resetonunlock", true);
    public final BooleanOption useLShift = new BooleanOption("useLShift", "entrytooltip.tooltipscroll.uselshift", true);
    public final BooleanOption invertXScroll = new BooleanOption("invertXScroll", "entrytooltip.tooltipscroll.invertxscroll", false);
    public final BooleanOption invertYScroll = new BooleanOption("invertYScroll", "entrytooltip.tooltipscroll.invertyscroll", false);
    public final BooleanOption matrixCompatibilityMode = new BooleanOption("matrixCompatibilityMode", "entrytooltip.tooltipscroll.matrix_compatibility", false);

    public final IntegerOption scrollSpeed = new IntegerOption("scrollSpeed", "entrytooltip.tooltipscroll.scrollspeed", 10, IntegerValidators.min(1));
    public final IntegerOption keyboardScrollSpeed = new IntegerOption("keyboardScrollSpeed", "entrytooltip.tooltipscroll.scrollspeedkeys", 5, IntegerValidators.min(1));
    public final DoubleOption scrollSmoothness = new DoubleOption("scrollSmoothness", "entrytooltip.tooltipscroll.smoothness", 0.25, value -> value >= 0.05 && value <= 1.0);

    private OSLTooltipConfig() {}

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public String getName() {
        return "title.tooltipscroll.config";
    }

    @Override
    public String getSaveName() {
        return "tooltipscroll.json";
    }

    @Override
    public ConfigScope getScope() {
        return ConfigScope.GLOBAL;
    }

    @Override
    public LoadingPhase getLoadingPhase() {
        return LoadingPhase.START;
    }

    @Override
    public FileSerializerType<?> getType() {
        return SERIALIZER_TYPE;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void init() {
        registerOptions("general",
            canScroll, useWASD, startOnTop, resetOnUnlock, useLShift,
            invertXScroll, invertYScroll, matrixCompatibilityMode,
            scrollSpeed, keyboardScrollSpeed, scrollSmoothness
        );
    }
}
