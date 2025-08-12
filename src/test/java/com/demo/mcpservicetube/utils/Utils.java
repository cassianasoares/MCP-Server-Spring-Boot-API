package com.demo.mcpservicetube.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class Utils {

    public String getResourcePath(String resourcePath) throws IOException {
        return Files.readString(Paths.get("src/test/resources/" + resourcePath));
    }

}
