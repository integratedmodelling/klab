package org.integratedmodelling.klab.components.geospace.visualization;

import java.util.HashMap;
import java.util.Map;

import org.geotools.styling.Style;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.observations.IState;

/**
 * Rendering functions for raster coverages and possibly more. Uses Geotools' SLD support and
 * manages SLD definitions created from classpath, files, URLs and annotations.
 * 
 * @author ferdinando.villa
 *
 */
public enum Renderer {

	INSTANCE;
	
	Map<String, Style> styles = new HashMap<>();
	
	Renderer() {
		// TODO lookup and load any pre-defined styles in the classpath and local workspace
	}
	
	/**
	 * Get a style with fixed values from a known resource or pre-loaded ID.
	 * 
	 * @param id
	 * @return
	 */
	public Style getStyle(String id) {
		
		Style ret = styles.get(id);
		if (ret == null) {
			// ID may be classpath file, predefined ID, URL, local file
		}
		return null;
	}
	
	/**
	 * Get a style from a colorbrewer template, customized to the values
	 * in the passed state.
	 *  
	 * @param id
	 * @param state
	 * @return
	 */
	public Style getStyle(String id, IState state, IParameters parameters) {
		
		return null;
	}
}
