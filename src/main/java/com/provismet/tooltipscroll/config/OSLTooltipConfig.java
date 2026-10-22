package com.provismet.tooltipscroll.config;

import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.DoubleOption;
import net.ornithemc.osl.config.api.config.option.IntegerOption;
import net.ornithemc.osl.config.api.config.option.Option;
import net.ornithemc.osl.config.api.config.option.validator.IntegerValidators;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;
import net.ornithemc.osl.config.api.serdes.config.ConfigSerializers;
import net.ornithemc.osl.core.api.json.JsonFile;

import java.util.HashMap;
import java.util.Map;

public class OSLTooltipConfig extends BaseConfig {

    public static final FileSerializerType<JsonFile> SERIALIZER_TYPE = SerializerTypes.register("tooltipscroll_json", JsonFile::new);

    public static final OSLTooltipConfig INSTANCE = new OSLTooltipConfig();

    static {
        ConfigSerializers.register(SERIALIZER_TYPE, new FlatJsonConfigSerializer());
    }

    private final Map<Option, String> jsonKeys = new HashMap<>();

    public final BooleanOption canScroll = option("canScroll", new BooleanOption("entry.tooltipscroll.canscroll", "entrytooltip.tooltipscroll.canscroll", true));
    public final BooleanOption useWASD = option("useWASD", new BooleanOption("entry.tooltipscroll.usewasd", "entrytooltip.tooltipscroll.usewasd", false));
    public final BooleanOption startOnTop = option("startOnTop", new BooleanOption("entry.tooltipscroll.startontop", "entrytooltip.tooltipscroll.startontop", false));
    public final BooleanOption resetOnUnlock = option("resetOnUnlock", new BooleanOption("entry.tooltipscroll.resetonunlock", "entrytooltip.tooltipscroll.resetonunlock", true));
    public final BooleanOption useLShift = option("useLShift", new BooleanOption("entry.tooltipscroll.uselshift", "entrytooltip.tooltipscroll.uselshift", true));
    public final BooleanOption invertXScroll = option("invertXScroll", new BooleanOption("entry.tooltipscroll.invertxscroll", "entrytooltip.tooltipscroll.invertxscroll", false));
    public final BooleanOption invertYScroll = option("invertYScroll", new BooleanOption("entry.tooltipscroll.invertyscroll", "entrytooltip.tooltipscroll.invertyscroll", false));
    public final BooleanOption matrixCompatibilityMode = option("matrixCompatibilityMode", new BooleanOption("entry.tooltipscroll.matrix_compatibility", "entrytooltip.tooltipscroll.matrix_compatibility", false));

    public final IntegerOption scrollSpeed = option("scrollSpeed", new IntegerOption("entry.tooltipscroll.scrollspeed", "entrytooltip.tooltipscroll.scrollspeed", 10, IntegerValidators.min(1)));
    public final IntegerOption keyboardScrollSpeed = option("keyboardScrollSpeed", new IntegerOption("entry.tooltipscroll.scrollspeedkeys", "entrytooltip.tooltipscroll.scrollspeedkeys", 5, IntegerValidators.min(1)));
    public final DoubleOption scrollSmoothness = option("scrollSmoothness", new DoubleOption("entry.tooltipscroll.smoothness", "entrytooltip.tooltipscroll.smoothness", 0.25, value -> value >= 0.05 && value <= 1.0));

    private OSLTooltipConfig() {}

    private <O extends Option> O option(String jsonKey, O option) {
        jsonKeys.put(option, jsonKey);
        return option;
    }

    public String getJsonKey(Option option) {
        return jsonKeys.get(option);
    }

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public String getName() {
        return "tooltipscroll";
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
        registerOptions("category.tooltipscroll.general",
            canScroll, useWASD, startOnTop, resetOnUnlock, useLShift,
            invertXScroll, invertYScroll,
            scrollSpeed, keyboardScrollSpeed, scrollSmoothness, matrixCompatibilityMode
        );
    }
}
