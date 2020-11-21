package org.integratedmodelling.klab.ogc;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.ogc.fscan.FSCANEncoder;
import org.integratedmodelling.klab.ogc.fscan.FSCANImporter;
import org.integratedmodelling.klab.ogc.fscan.FSCANPublisher;
import org.integratedmodelling.klab.ogc.fscan.FSCANValidator;
import org.integratedmodelling.klab.ogc.integration.Postgis;

/**
 * Specialized vector adapter that merges one or more vector sources (limited to
 * polygons for now) and builds a postgres-backed index that can quickly return
 * the most appropriately scaled one for a bounding box, intended as the one
 * that best fills it with a single shape. Specific file resources can be
 * assigned to "levels of organization" to reflect hierarchies of containment
 * and to choose the most appropriate level for a specified bounding box.
 * <p>
 * As a default the adapter will return one shape and will have URN options to
 * change the number of shapes returned and their simplification level. Shapes
 * have an immutable ID in the metadata to ease caching.
 * <p>
 * This adapter is meant to support intelligent geographical selection of
 * scale-dependent features.
 * 
 * @author Ferd
 *
 */
@ResourceAdapter(type = FSCANAdapter.ID, version = Version.CURRENT)
public class FSCANAdapter implements IResourceAdapter {

	public static final String ID = "fscan";

	private static Postgis postgis;

	public static Postgis getPostgis() {
		if (Postgis.isEnabled()) {
			postgis = Postgis.create();
			postgis.isOnline();
		}
		return postgis;
	}
	
	public static boolean isOnline() {
		return getPostgis() != null && getPostgis().isOnline();
	}
	
	
	@Override
	public String getName() {
		return ID;
	}

	@Override
	public IResourceValidator getValidator() {
		return new FSCANValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new FSCANPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new FSCANEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new FSCANImporter();
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		return null;
	}

	@Override
	public IPrototype getResourceConfiguration() {
		return new Prototype(
				Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/fscan.kdl"))
						.getActuators().iterator().next(),
				null);
	}

}
