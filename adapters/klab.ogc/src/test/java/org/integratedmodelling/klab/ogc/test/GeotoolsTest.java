/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.ogc.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.raster.wcs.WCSService;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class GeotoolsTest.
 */
/*
 * TEMPORARY - not a test case, just a sandbox for requests with poorly
 * undocumented Geotools WCS schema parsing. Delete when everything is figured
 * out.
 */
public class GeotoolsTest {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 */
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

		// String baseurl =
		// "https://www.wcs.nrw.de/geobasis/wcs_nw_dgm?service=wcs&request=getcapabilities";

		WCSService imWcs = new WCSService("http://www.integratedmodelling.org/geoserver/wcs", Version.create("1.1.0"));
		WCSService usWcs = new WCSService(
				"https://www.mrlc.gov/arcgis/services/LandCover/USGS_EROS_LandCover_NLCD/MapServer/WCSServer",
				Version.create("2.0.1"));
		
		System.out.println("--- IM ---");
		for (WCSService.WCSLayer layer : imWcs.getLayers()) {
			System.out.println(layer + "");
		}
		System.out.println("--- US ---");
		for (WCSService.WCSLayer layer : usWcs.getLayers()) {
			System.out.println(layer + "");
		}

	}
}
