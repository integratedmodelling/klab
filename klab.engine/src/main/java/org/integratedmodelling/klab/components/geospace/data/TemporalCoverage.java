package org.integratedmodelling.klab.components.geospace.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.TemplateUtils;

/**
 * Wrapper for a GridCoverage2D that will return the most appropriate of a set
 * of temporal slices, potentially also taking care of aggregation. Can be
 * initialized from several files or URLs using templates. The logic to convert
 * the URL into data is abstract and can be implemented on top of Geotools,
 * OpenDAP or other backend.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class TemporalCoverage {
	
	/*
	 * List of sources. The time in the pair may be known in advance or be null,
	 * requiring the URLs to be opened.
	 */
	List<Pair<ITime, String>> sources = Collections.synchronizedList(new ArrayList<>());

	/*
	 * The geometry of reference, initially the same one as the passed sources.
	 */
	IGeometry geometry;

	/**
	 * Use to point to a location with multiple files, like a folder, a
	 * multidimensional file or URL (Netcdf or OpenDAP with CF conventions) or to a
	 * service providing multiple coverages (e.g. a getCapabilities url).
	 * 
	 * @param url
	 */
	protected TemporalCoverage(String url) {

	}

	/**
	 * Use to specify a template for multiple URLs. Template variables named with
	 * temporal events (year, month, week, day, hour, minute, second) will be used
	 * to define the temporal slice, unless the content can also provide temporal
	 * context.
	 * 
	 * @param urlTemplate
	 * @param templateVariables
	 */
	protected TemporalCoverage(String urlTemplate, Map<String, Object> templateVariables) {
		
		
		
		for (Pair<String, Map<String, Object>> tmp : TemplateUtils.getExpansion(urlTemplate, templateVariables)) {
			
		}
	}

	/**
	 * Return a new coverage subset and reprojected to the passed scale. Call before
	 * getCoverage() to ensure the locators are understood. Adapts the coverages to
	 * the resolution and may prepare caches and storage for faster response when
	 * getCoverage is called.
	 * 
	 * @param scale
	 * @return
	 */
	public TemporalCoverage at(IScale scale) {
		return null;
	}

	/**
	 * Return a coverage for the passed locator. A scale must have been set
	 * beforehand.
	 * 
	 * @param scale
	 * @param locator
	 * @return
	 */
	public GridCoverage2D getCoverage(ILocator locator) {
		return null;
	}

	/**
	 * Implement to turn the URL into a coverage. A temporal extent may already be
	 * known from the URL analysis, to be used as a default if the source does not
	 * include temporal metadata, or be null. If the temporal extent cannot be
	 * determined and time is null, an exception should be thrown.
	 * 
	 * @param url
	 * @return
	 */
	abstract Pair<ITime, GridCoverage2D> getCoverage(String url, @Nullable ITime time);

}
