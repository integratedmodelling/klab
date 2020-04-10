package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.processing.ScaleChooserInstantiator;
import org.integratedmodelling.klab.exceptions.KlabException;

public class WatershedPolygonInstantiator extends ScaleChooserInstantiator implements IExpression {
	
	static String[] resourceUrns = { 
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev04_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev05_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev06_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev07_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev08_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev09_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev10_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev11_v1c",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev12_v1c" };

	public WatershedPolygonInstantiator() {
	}

	public WatershedPolygonInstantiator(boolean whole) {
		super(whole);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		boolean whole = parameters.get("whole", Boolean.FALSE);
		WatershedPolygonInstantiator ret = new WatershedPolygonInstantiator(whole);
		return ret;
	}

	@Override
	public String[] getResourceUrns() {
		return resourceUrns;
	}

}
