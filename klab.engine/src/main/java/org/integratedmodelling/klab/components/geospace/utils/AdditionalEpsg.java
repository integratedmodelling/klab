package org.integratedmodelling.klab.components.geospace.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.geotools.metadata.iso.citation.Citations;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.referencing.factory.PropertyAuthorityFactory;
import org.geotools.referencing.factory.ReferencingFactoryContainer;
import org.geotools.util.factory.Hints;

/**
 * Class to hold additional epsgs that need to be registered ad engine startup 
 * in order to be supported in klab.
 * 
 * <p>
 * To add a new epsg, define a new enumeration entry containing the chosen epsg code and the 
 * wkt representation. The latter needs to be formatted into a single line srting.   
 */
public enum AdditionalEpsg {
    EPSG9221(9221,
            "PROJCS[\"Hartebeesthoek94 / ZAF BSU Albers 25E\",GEOGCS[\"Hartebeesthoek94\",DATUM[\"Hartebeesthoek94_based_on_WGS84_ellipsoid\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],TOWGS84[0,0,0,0,0,0,0]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]]],PROJECTION[\"Albers_Conic_Equal_Area\"],PARAMETER[\"latitude_of_center\",-30],PARAMETER[\"longitude_of_center\",25],PARAMETER[\"standard_parallel_1\",-22],PARAMETER[\"standard_parallel_2\",-38],PARAMETER[\"false_easting\",1400000],PARAMETER[\"false_northing\",1300000],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH]]");

    private String projWkt;
    private int epsgCode;

    AdditionalEpsg( int epsgCode, String projWkt ) {
        this.epsgCode = epsgCode;
        this.projWkt = projWkt;
    }

    String getProperty() {
        return epsgCode + "=" + projWkt;
    }

    /**
     * Regsiter all the additional epsg definitions held by this enumeration.
     * 
     * @throws Exception
     */
    public static void registerAdditionalEpsgCodes() throws Exception {

        File file = File.createTempFile("epsg", ".properties");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));) {
            for(AdditionalEpsg epsg : AdditionalEpsg.values()) {
                writer.append(epsg.getProperty());
                writer.append("\n");
            }
        }

        Hints hints = new Hints(Hints.CRS_AUTHORITY_FACTORY, PropertyAuthorityFactory.class);
        ReferencingFactoryContainer referencingFactoryContainer = ReferencingFactoryContainer.instance(hints);

        PropertyAuthorityFactory factory = new PropertyAuthorityFactory(referencingFactoryContainer, Citations.fromName("EPSG"),
                file.toURI().toURL());

        ReferencingFactoryFinder.addAuthorityFactory(factory);
        ReferencingFactoryFinder.scanForPlugins();

    }

}
