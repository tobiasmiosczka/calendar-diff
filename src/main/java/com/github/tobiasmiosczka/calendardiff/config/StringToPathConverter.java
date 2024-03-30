package com.github.tobiasmiosczka.calendardiff.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationPropertiesBinding
public class StringToPathConverter implements Converter<String, Path> {

    @Override
    public Path convert(String pathAsString) {
        return Paths.get(pathAsString);
    }
}