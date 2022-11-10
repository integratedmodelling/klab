package org.integratedmodelling.opencpu.adapters.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean read from the <i>method</i>.json file in the adapter's configuration area (or the
 * classpath). The catalog can be in common with other methods; the list of catalogs harvested from
 * the json specs is used to validate resources.
 * 
 * Packages needed and their versions are defined at the method level. If the installed OpenCPU
 * runtime does not have them or versions are not met, the method is non-functional and the
 * resources using it will be offline.
 * 
 * @author Ferd
 *
 */
public class MethodConfiguration {

    private String name;
    private String catalog;
    private Map<String, OperationConfiguration> operations = new HashMap<>();
    // package name -> minimum version
    private Map<String, String> requiredPackages = new HashMap<>();
}
