package org.integratedmodelling.klab.raster.test;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.geotools.wcs.WCSConfiguration;
import org.geotools.xml.Parser;
import org.xml.sax.SAXException;

import net.opengis.wcs11.GetCapabilitiesType;
import net.opengis.wcs20.Wcs20Factory;
import net.opengis.wcs20.impl.Wcs20FactoryImpl;


public class GeotoolsTest {
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		String baseurl = "https://www.wcs.nrw.de/geobasis/wcs_nw_dgm?service=wcs&request=getcapabilities";
		GeotoolsTest me = new GeotoolsTest();
		me.getCapabilitiesType(baseurl);

	}

	GetCapabilitiesType getCapabilitiesType(String capRequestPath)
			throws IOException, SAXException, ParserConfigurationException {
		Parser parser = new Parser(new WCSConfiguration());
		URL url = new URL(capRequestPath);
		Object capabilitiesType = parser.parse(url.openStream());
		
		Wcs20Factory factory = Wcs20FactoryImpl.init();
		net.opengis.wcs20.GetCapabilitiesType wcsGetCapabilities = factory.createGetCapabilitiesType();
		wcsGetCapabilities.setBaseUrl(capRequestPath); 
		
		return null;
	}
}
