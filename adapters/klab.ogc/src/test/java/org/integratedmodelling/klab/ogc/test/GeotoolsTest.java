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

/*
 * TEMPORARY - not a test case, just a sandbox for requests with poorly undocumented Geotools WCS
 * schemata. Delete when everything is figured out.
 */
public class GeotoolsTest {
  public static void main(String[] args)
      throws IOException, SAXException, ParserConfigurationException {
    // String baseurl =
    // "https://www.wcs.nrw.de/geobasis/wcs_nw_dgm?service=wcs&request=getcapabilities";
    String baseurl = "http://www.integratedmodelling.org/geoserver/wcs?request=getcapabilities";
    GeotoolsTest me = new GeotoolsTest();
    me.getCapabilitiesType(baseurl);
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
