package org.integratedmodelling.tables.adapter.sdmx;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabValidationException;

import it.bancaditalia.oss.sdmx.api.Dimension;

/**
 * Proxy to a DSD query without dataflow or parameters for better API.
 * 
 * @author Ferd
 *
 */
public class SDMXQuery extends LinkedHashMap<String, String> {

	private static final long serialVersionUID = 8063231811096946082L;

	public SDMXQuery(String string, List<Dimension> dimensions) {

		int ch = -1;
		List<String> parts = new ArrayList<>();
		StringBuffer buf = new StringBuffer(24);
		for (int n = 0; n < string.length(); n++) {
			if (string.charAt(n) == '.') {
				if (ch == '.' || ch < 0) {
					parts.add("*");
				} else {
					parts.add(buf.toString());
					buf = new StringBuffer(24);
				}
			} else {
				buf.append(string.charAt(n));
			}
			ch = string.charAt(n);
		}

		if (ch == '.') {
			parts.add("*");
		}

		if (parts.size() != dimensions.size()) {
			throw new KlabValidationException("SDMX query string " + string
					+ " implies a different number of dimensions than the dataflow's " + dimensions.size());
		}
		
		int n = 0;
		for (Dimension dimension : dimensions) {
			this.put(dimension.getName(), parts.get(n++));
		}

	}

}
