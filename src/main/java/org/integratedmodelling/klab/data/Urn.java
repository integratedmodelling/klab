package org.integratedmodelling.klab.data;

import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IMetadata;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;

/**
 * The k.LAB URN, with methods to create one from file names and concept declarations. A URN can be
 * partially resolved (using the resolve call), i.e. it will know its geometry and metadata without
 * actually building the IResource, or fully resolved to a IResource (get call).
 *  
 * @author Ferd
 *
 */
public class Urn {

    Version version;
    IGeometry geometry;
    IMetadata metadata;
    
    /**
     * Pattern to validate a RFC 2141-compliant URN, just to be on the right side of things. 
     */
    public final static Pattern URN_PATTERN = Pattern
            .compile("^urn:[a-z0-9][a-z0-9-]{0,31}:([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$", Pattern.CASE_INSENSITIVE);

    public static boolean isCompliant(String urn) {
        return URN_PATTERN.matcher(urn).matches();
    }
    
    /**
     * Available after resolution unless the version was explicitly part of the URN.
     * 
     * @return
     */
    public Version getVersion() {
        return version;
    }
    
    /**
     * The Geometry, Available after resolution.
     * 
     * @return
     */
    public IGeometry getGeometry() {
        return geometry;
    }
    
    /**
     * Metadata. Available after resolution.
     * 
     * @return
     */
    public IMetadata getMetadata() {
    	return metadata;
    }
}
