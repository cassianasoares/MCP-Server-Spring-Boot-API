package com.demo.mcpservicetube.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static String getResourcePath(String resourcePath) throws IOException {
        return Files.readString(Paths.get("src/test/resources/" + resourcePath));
    }

}
