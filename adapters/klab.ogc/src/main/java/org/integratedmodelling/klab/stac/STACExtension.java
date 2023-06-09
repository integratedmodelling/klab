package org.integratedmodelling.klab.stac;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Version;

/**
 * An list of the most popular extensions for STAC.
 * Currently, we only define extensions with a maturity of Stable, Candidate, Pilot or Deprecated.
 * https://stac-extensions.github.io/
 */
public enum STACExtension {
    ElectroOptical("eo"),
    FileInfo("file"),
    ItemAssetsDefinition("item-assets"),
    Projection("projection"),
    ScientificCitation("scientific"),
    ViewGeometry("view"),
    Datacube("datacube"),
    Processing("processing"),
    Raster("raster"),
    SAR("sar"),
    Satellite("sat"),
    VersioningIndicators("version"),
    AlternateAssets("alternate-assets"),
    AnonymizedLocation("anonymized-location"),
    CARD4L_OpticalAndSAR("card4l"),
    Classification("classification"),
    Grid("grid"),
    Label("label"),
    MilitaryGridReferenceSystem("mgrs"),
    NOAA_GOES("goes"), // NOAA Geostationary Operational Environmental Satellite
    NOAA_MRMS_QPE("noaa-mrms-qpe"),
    Order("order"),
    PointCloud("pointcloud"),
    Stats("stats"),
    Storage("storage"),
    Table("table"),
    Timestamps("timestamps"),
    XarrayAssets("xarray-assets"),
    SingleFileSTAC("single-file-stac"),
    TimeSeries("timeseries");

    private String name;
    
    STACExtension(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getExtensionName(String identifier) {
        return StringUtils.substringBetween(identifier, "https://stac-extensions.github.io/", "/v");
    }

    public static Version getVersion(String identifier) {
        return Version.create(StringUtils.substringBetween(identifier, "/v", "/schema.json"));
    }

    public boolean isDeprecated() {
        return this.name.equals(SingleFileSTAC.name) || this.name.equals(TimeSeries.name);
    }

    public boolean isSupported() {
        return this.name == ElectroOptical.name;
    }

    public static STACExtension valueOfLabel(String label) {
        for (STACExtension e : values()) {
            if (e.name.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
