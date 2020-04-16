package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.processing.ScaleChooserInstantiator;
import org.integratedmodelling.klab.exceptions.KlabException;

public class WatershedPolygonInstantiator extends ScaleChooserInstantiator implements IExpression {

	static String[] resourceUrns = { 
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev04_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev05_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev06_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev07_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev08_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev09_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev10_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev11_v1c#intersect=false",
			"local:ferdinando.villa:im.watersheds:hybas_eu_lev12_v1c#intersect=false" };

	public WatershedPolygonInstantiator() {
	}

	public WatershedPolygonInstantiator(boolean whole) {
		super(whole);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		
		boolean whole = parameters.get("whole", Boolean.FALSE);
		boolean boundingbox = parameters.get("boundingbox", Boolean.FALSE);
		boolean align = parameters.get("align", Boolean.FALSE);
		ScaleChooserInstantiator.Strategy strategy = ScaleChooserInstantiator.Strategy
				.valueOf(parameters.get("strategy", "cover").toUpperCase());
		int maxobjects = parameters.get("maxobjects", -1.0).intValue();
		double minCoverage = parameters.get("mincoverage", 0.2);
		int detail = parameters.get("granularity", 0.0).intValue();
		
		WatershedPolygonInstantiator ret = new WatershedPolygonInstantiator(whole);
		
		ret.setMaxObjects(maxobjects);
		ret.setMinCoverage(minCoverage);
		ret.setStrategy(strategy);
		ret.setWhole(whole);
		ret.setBoundingBox(boundingbox);
		ret.setAlignGrid(align);
		ret.setDetail(detail);
		
		return ret;
	}

	@Override
	public String[] getResourceUrns() {
		return resourceUrns;
	}

}
