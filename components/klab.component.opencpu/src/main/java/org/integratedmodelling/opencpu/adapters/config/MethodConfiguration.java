package org.integratedmodelling.opencpu.adapters.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Bean read from the <i>method</i>.json file in the adapter's configuration area (or the
 * classpath). The catalog can be in common with other methods; the list of catalogs harvested from
 * the json specs is used to validate resources.
 * 
 * Packages needed and their versions are defined at the method level. If the installed OpenCPU
 * runtime does not have them or versions are not met, the method is non-functional and the
 * resources using it will be offline.
 * 
 * If the allowedGroups set is not empty, only the named groups can access the method.
 * 
 * @author Ferd
 *
 */
public class MethodConfiguration {

    private String name;
    private String catalog;
    private String documentation;
    private Map<String, OperationConfiguration> operations = new HashMap<>();
    // package name -> minimum version
    private Map<String, String> requiredPackages = new HashMap<>();
    // required OpenCPU/R version
    private String requiredVersion;
    private Set<String> allowedGroups = new HashSet<>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCatalog() {
        return catalog;
    }
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    public String getDocumentation() {
        return documentation;
    }
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
    public Map<String, OperationConfiguration> getOperations() {
        return operations;
    }
    public void setOperations(Map<String, OperationConfiguration> operations) {
        this.operations = operations;
    }
    public Map<String, String> getRequiredPackages() {
        return requiredPackages;
    }
    public void setRequiredPackages(Map<String, String> requiredPackages) {
        this.requiredPackages = requiredPackages;
    }
    public String getRequiredVersion() {
        return requiredVersion;
    }
    public void setRequiredVersion(String requiredVersion) {
        this.requiredVersion = requiredVersion;
    }
    public Set<String> getAllowedGroups() {
        return allowedGroups;
    }
    public void setAllowedGroups(Set<String> allowedGroups) {
        this.allowedGroups = allowedGroups;
    }

}
