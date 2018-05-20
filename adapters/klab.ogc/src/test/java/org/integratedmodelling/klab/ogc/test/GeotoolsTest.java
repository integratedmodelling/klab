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

		WCSService imWcs = new WCSService("http://www.integratedmodelling.org/geoserver/wcs", Version.create("1.1.1"));
		WCSService usWcs = new WCSService(
				"https://www.mrlc.gov/arcgis/services/LandCover/USGS_EROS_LandCover_NLCD/MapServer/WCSServer",
				Version.create("2.0.1"));
		
		System.out.println("--- IM ---");
		for (WCSService.WCSLayer layer : imWcs.getLayers()) {
			System.out.println(layer.getIdentifier());
		}
		System.out.println("--- US ---");
		for (WCSService.WCSLayer layer : usWcs.getLayers()) {
			System.out.println(layer.getIdentifier());
		}

	}

	// 1.1.0
	
//	{
//		  "Keywords" : {
//		    "Keyword" : [ "WCS", "GeoTIFF", "cn26tx70_yave_ll" ]
//		  },
//		  "Identifier" : "javier-martinez-global-climate:cn26tx70_yave_ll",
//		  "SupportedFormat" : [ "image/png", "image/jpeg", "image/tiff", "text/plain" ],
//		  "Abstract" : "Generated from GeoTIFF",
//		  "Title" : "cn26tx70_yave_ll",
//		  "Domain" : {
//		    "BoundingBox" : [ {
//		      "UpperCorner" : "180.0 90.0",
//		      "crs" : "urn:ogc:def:crs:OGC:1.3:CRS84",
//		      "LowerCorner" : "-180.0 -60.0",
//		      "dimensions" : "2"
//		    }, {
//		      "UpperCorner" : "90.0 180.0",
//		      "crs" : "urn:ogc:def:crs:EPSG::4326",
//		      "LowerCorner" : "-60.0 -180.0",
//		      "dimensions" : "2"
//		    } ],
//		    "GridCRS" : {
//		      "GridBaseCRS" : "urn:ogc:def:crs:EPSG::4326",
//		      "GridOrigin" : "-179.99583333333334 89.99583333333334",
//		      "GridCS" : "urn:ogc:def:cs:OGC:0.0:Grid2dSquareCS",
//		      "GridOffsets" : "0.008333333333333333 0.0 0.0 -0.008333333333333333",
//		      "GridType" : "urn:ogc:def:method:WCS:1.1:2dGridIn2dCrs"
//		    }
//		  },
//		  "Range" : {
//		    "Identifier" : "contents",
//		    "InterpolationMethods" : {
//		      "InterpolationMethod" : [ "nearest", "linear", "cubic" ],
//		      "Default" : "nearest neighbor"
//		    },
//		    "Definition" : {
//		      "MaximumValue" : "Infinity",
//		      "MinimumValue" : "-Infinity"
//		    },
//		    "Axis" : {
//		      "identifier" : "Bands",
//		      "AvailableKeys" : "GRAY_INDEX"
//		    }
//		  },
//		  "SupportedCRS" : [ "urn:ogc:def:crs:EPSG::4326", "EPSG:4326" ]
//		}
	
	// 2.0
	
//	{
//		  "rangeType" : {
//		    "field" : [ {
//		      "name" : "band_1",
//		      "Quantity" : {
//		        "uom" : "unknown",
//		        "description" : "Band 1",
//		        "constraint" : "0.0 255.0"
//		      }
//		    }, {
//		      "name" : "band_2",
//		      "Quantity" : {
//		        "uom" : "unknown",
//		        "description" : "Band 2",
//		        "constraint" : "0.0 255.0"
//		      }
//		    }, {
//		      "name" : "band_3",
//		      "Quantity" : {
//		        "uom" : "unknown",
//		        "description" : "Band 3",
//		        "constraint" : "0.0 255.0"
//		      }
//		    } ]
//		  },
//		  "boundedBy" : {
//		    "lowerCorner" : "-14497453.9106248 2480608.8817100283",
//		    "srsName" : "http://www.opengis.net/def/crs/EPSG/0/3857",
//		    "srsDimension" : "2",
//		    "axisLabels" : "x y",
//		    "uomLabels" : "",
//		    "upperCorner" : "-7087933.9106248002 6960328.8817100283"
//		  },
//		  "CoverageId" : "Coverage11",
//		  "domainSet" : {
//		    "axisLabels" : "x y",
//		    "origin" : {
//		      "srsName" : "http://www.opengis.net/def/crs/EPSG/0/3857",
//		      "pos" : "-14497438.9106248 6960313.8817100283",
//		      "id" : "grid_origin_Coverage11"
//		    },
//		    "offsetVector" : [ "30 0", "0 -30" ],
//		    "id" : "grid_Coverage11",
//		    "dimension" : "2",
//		    "limits" : {
//		      "high" : "246983 149323",
//		      "low" : "0 0"
//		    }
//		  },
//		  "id" : "Coverage11",
//		  "ServiceParameters" : {
//		    "nativeFormat" : "image/tiff",
//		    "CoverageSubtype" : "RectifiedGridCoverage"
//		  }
//		}
	
}
