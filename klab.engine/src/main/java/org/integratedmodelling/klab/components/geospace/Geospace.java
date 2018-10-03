package org.integratedmodelling.klab.components.geospace;

import java.util.Arrays;
import java.util.Collection;

import org.geotools.factory.Hints;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.IServiceProvider;
import org.integratedmodelling.klab.api.extensions.component.Initialize;

import com.vividsolutions.jts.geom.GeometryFactory;

@Component(id = "geospace", version = Version.CURRENT)
public class Geospace implements IServiceProvider {

	public static GeometryFactory gFactory = new GeometryFactory();

	static {
	}

	public Geospace() {
		/**
		 * Ensure that all CRSs use a X = horizontal axis convention, independent of
		 * what the projection says. Do not change this or things will stop working
		 * entirely.
		 */
		System.setProperty("org.geotools.referencing.forceXY", "true");
		Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);

	}

	@Initialize
	public void initialize() {
		// TODO create the desired geometry factory
		// TODO set up defaults for projections etc.
	}

	@Override
	public Collection<String> getServiceDefinitions() {
		// TODO Auto-generated method stub
		return Arrays.asList(new String[] { "extents.kdl", "features.kdl", "osm.kdl", "services.kdl" });
	}

	// void registerAdditionalCRS() throws KlabException {
	//
	// URL epsg = null;
	//
	// File epp = new File(Configuration.INSTANCE.getDataPath() + File.separator +
	// "epsg.properties");
	// if (epp.exists()) {
	// try {
	// epsg = epp.toURI().toURL();
	// } catch (MalformedURLException e) {
	// throw new KlabIOException(e);
	// }
	// }
	//
	// if (epsg != null) {
	// Hints hints = new Hints(Hints.CRS_AUTHORITY_FACTORY,
	// PropertyAuthorityFactory.class);
	// ReferencingFactoryContainer referencingFactoryContainer =
	// ReferencingFactoryContainer.instance(hints);
	// PropertyAuthorityFactory factory;
	// try {
	// factory = new PropertyAuthorityFactory(referencingFactoryContainer,
	// Citations.fromName("EPSG"), epsg);
	// ReferencingFactoryFinder.addAuthorityFactory(factory);
	// } catch (IOException e) {
	// throw new KlabIOException(e);
	// }
	// }
	// }

	// public JCS getWFSCache() throws KlabException {
	//
	// if (_wfsCache == null) {
	//
	// try {
	// _wfsCache = JCS.getInstance("wfs");
	// } catch (CacheException e) {
	// throw new KlabIOException(e);
	// }
	//
	// }
	// return _wfsCache;
	// }
	//
	// public JCS getWCSCache() throws KlabException {
	//
	// if (_wcsCache == null) {
	//
	// try {
	// _wcsCache = JCS.getInstance("wcs");
	// } catch (CacheException e) {
	// throw new KlabIOException(e);
	// }
	//
	// }
	// return _wcsCache;
	// }

}
