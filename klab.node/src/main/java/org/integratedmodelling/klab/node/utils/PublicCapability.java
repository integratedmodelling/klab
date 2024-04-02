package org.integratedmodelling.klab.node.utils;

import java.util.Arrays;
import java.util.List;

public class PublicCapability {
    private final static List<String> PUBLIC_CAPABILITIES = Arrays.asList("stats");

    public static boolean isPublicCapability(String capability) {
        return PUBLIC_CAPABILITIES.contains(capability);
    }
}
