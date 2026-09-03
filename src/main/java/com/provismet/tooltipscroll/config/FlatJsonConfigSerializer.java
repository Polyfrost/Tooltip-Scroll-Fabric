package com.provismet.tooltipscroll.config;

import java.io.IOException;

import net.ornithemc.osl.config.api.config.Config;
import net.ornithemc.osl.config.api.config.option.Option;
import net.ornithemc.osl.config.api.config.option.group.OptionGroup;
import net.ornithemc.osl.config.api.serdes.SerializationSettings;
import net.ornithemc.osl.config.api.serdes.config.ConfigSerializer;
import net.ornithemc.osl.config.api.serdes.config.option.JsonOptionSerializer;
import net.ornithemc.osl.config.api.serdes.config.option.JsonOptionSerializers;
import net.ornithemc.osl.core.api.json.JsonFile;

public class FlatJsonConfigSerializer implements ConfigSerializer<JsonFile> {

    @Override
    public void serialize(Config config, SerializationSettings settings, JsonFile json) throws IOException {
        try {
            json.write();

            for (OptionGroup group : config.getGroups()) {
                for (Option option : group.getOptions()) {
                    json.writeName(option.getName());
                    serializeOption(option, settings, json);
                }
            }
        }
        finally {
            json.close();
        }
    }

    @Override
    public void deserialize(Config config, SerializationSettings settings, JsonFile json) throws IOException {
        try {
            json.read();

            while (json.hasNext()) {
                String name = json.readName();
                Option option = findOption(config, name);

                if (option == null) {
                    json.skipValue();
                }
                else {
                    deserializeOption(option, settings, json);
                }
            }
        }
        finally {
            json.close();
        }
    }

    private static Option findOption(Config config, String name) {
        for (OptionGroup group : config.getGroups()) {
            Option option = group.getOption(name);
            if (option != null) return option;
        }
        return null;
    }

    private <O extends Option> void serializeOption(O option, SerializationSettings settings, JsonFile json) throws IOException {
        JsonOptionSerializer<O> serializer = JsonOptionSerializers.get(option.getClass());

        if (serializer == null) {
            throw new IOException("don't know how to serialize option " + option);
        }
        serializer.serialize(option, settings, json);
    }

    private <O extends Option> void deserializeOption(O option, SerializationSettings settings, JsonFile json) throws IOException {
        JsonOptionSerializer<O> serializer = JsonOptionSerializers.get(option.getClass());

        if (serializer == null) {
            throw new IOException("don't know how to deserialize option " + option);
        }
        serializer.deserialize(option, settings, json);
    }
}
