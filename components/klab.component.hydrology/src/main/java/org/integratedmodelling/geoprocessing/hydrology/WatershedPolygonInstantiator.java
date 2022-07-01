package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.processing.ScaleChooserInstantiator;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

@Deprecated
public class WatershedPolygonInstantiator extends ScaleChooserInstantiator implements IExpression {

	static String[] resourceUrns = { 
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev04#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev05#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev06#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev07#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev08#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev09#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev10#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev11#intersect=false",
			"im.weather:wwf.hydrosheds:hydrology.global:basinatlas.v10.lev12#intersect=false"
	};

	public WatershedPolygonInstantiator() {
	}

	public WatershedPolygonInstantiator(boolean whole) {
		super(whole);
	}

	@Override
	public Object eval(IContextualizationScope context, Object...params) throws KlabException {
		
	    Parameters<String> parameters = Parameters.create(params);
		boolean whole = parameters.get("whole", Boolean.FALSE);
		boolean boundingbox = parameters.get("boundingbox", Boolean.FALSE);
		boolean align = parameters.get("align", Boolean.FALSE);
		ScaleChooserInstantiator.Strategy strategy = ScaleChooserInstantiator.Strategy
				.valueOf(parameters.get("strategy", "cover").toUpperCase());
		int maxobjects = parameters.get("maxobjects", -1.0).intValue();
		double minCoverage = parameters.get("mincoverage", 0.45);
		int detail = parameters.get("granularity", 0.0).intValue();
		int cellBuffer = parameters.get("cellbuffer", 0.0).intValue();
		
		WatershedPolygonInstantiator ret = new WatershedPolygonInstantiator(whole);
		
		ret.setMaxObjects(maxobjects);
		ret.setMinCoverage(minCoverage);
		ret.setStrategy(strategy);
		ret.setWhole(whole);
		ret.setBoundingBox(boundingbox);
		ret.setAlignGrid(align);
		ret.setDetail(detail);
		ret.setCellBuffer(cellBuffer);
		
		return ret;
	}

	@Override
	public String[] getResourceUrns() {
		return resourceUrns;
	}

}
