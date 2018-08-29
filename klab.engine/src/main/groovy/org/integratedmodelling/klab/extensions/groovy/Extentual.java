package org.integratedmodelling.klab.extensions.groovy;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IExtent;


/**
 * Anything extentual can produce one or more extents. Used to milk
 * scale from objects passed when creating observations.
 * 
 * @author Ferd
 *
 */
public interface Extentual {

    Collection<IExtent> getExtents();
}
