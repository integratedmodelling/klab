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
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.geotools.wcs.WCSConfiguration;
import org.geotools.xml.Parser;
import org.integratedmodelling.klab.utils.collections.Collections;
import org.xml.sax.SAXException;
import net.opengis.wcs11.GetCapabilitiesType;

// TODO: Auto-generated Javadoc
/**
 * The Class GeotoolsTest.
 */
/*
 * TEMPORARY - not a test case, just a sandbox for requests with poorly undocumented Geotools WCS
 * schemata. Delete when everything is figured out.
 */
public class GeotoolsTest {
  
  /**
   * The main method.
   *
   * @param args the arguments
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static void main(String[] args)
      throws IOException, SAXException, ParserConfigurationException {
    // String baseurl =
    // "https://www.wcs.nrw.de/geobasis/wcs_nw_dgm?service=wcs&request=getcapabilities";
    String baseurl = "http://www.integratedmodelling.org/geoserver/wcs?request=getcapabilities";
    String sdeurl =
        "https://www.mrlc.gov/arcgis/services/LandCover/USGS_EROS_LandCover_NLCD/MapServer/WCSServer?request=GetCapabilities&version=2.0&service=WCS";
    GeotoolsTest me = new GeotoolsTest();
    me.getCapabilitiesType(sdeurl);
  }

  GetCapabilitiesType getCapabilitiesType(String capRequestPath)
      throws IOException, SAXException, ParserConfigurationException {
    Parser parser = new Parser(new WCSConfiguration());
    URL url = new URL(capRequestPath);
    Map<?, ?> capabilitiesType = (Map<?, ?>) parser.parse(url.openStream());
    System.out.println(Collections.printAsJson(capabilitiesType));

    // Wcs20Factory factory = Wcs20FactoryImpl.init();
    // net.opengis.wcs20.GetCapabilitiesType wcsGetCapabilities =
    // factory.createGetCapabilitiesType();
    // wcsGetCapabilities.setBaseUrl(capRequestPath);

    return null;
  }
}
