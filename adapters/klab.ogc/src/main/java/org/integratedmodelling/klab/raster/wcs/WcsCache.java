package org.integratedmodelling.klab.raster.wcs;

import java.util.HashMap;

import org.hortonmachine.gears.io.wcs.IWebCoverageService;

/**
 * 
 * A singleton to hold wcs services and avoid re-reading capabilities everytime.
 *
 */
public enum WcsCache {
	INSTANCE;
	
	private HashMap<String, IWebCoverageService> CACHE = new HashMap<>();
	
	/**
	 * Get the service in cache or <code>null</code>, if none available.
	 * 
	 * @param url the url of the service.
	 * @return the {@link IWebCoverageService service} or null.
	 */
	public IWebCoverageService get(String url) {
		
		return CACHE.get(url);
	}

	/**
	 * Get the service in cache or try to instantiate it, if none available.
	 * 
	 * @param url the url of the service.
	 * @return the {@link IWebCoverageService service}.
	 */
	public IWebCoverageService getOrCreate(String url) throws Exception {
		IWebCoverageService wcs = CACHE.get(url);
		if(wcs == null) {
			wcs = IWebCoverageService.getServiceForVersion(url, null);
			if(wcs.getVersion() != null) {
				CACHE.put(url, wcs);
				return wcs;
			}
		}
		return wcs;
	}
	

}
