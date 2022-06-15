package org.integratedmodelling.klab.rest;

public class Localization {

    private String isoCode;
    private String languageDescription;
    private String localizedDescription;
    private String localizedLabel;
    
    public String getIsoCode() {
        return isoCode;
    }
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    public String getLanguageDescription() {
        return languageDescription;
    }
    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }
    public String getLocalizedDescription() {
        return localizedDescription;
    }
    public void setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
    }
    public String getLocalizedLabel() {
        return localizedLabel;
    }
    public void setLocalizedLabel(String localizedLabel) {
        this.localizedLabel = localizedLabel;
    }

}
