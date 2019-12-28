package org.integratedmodelling.landcover.model;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.landcover.model.LandcoverChange.Conversion;

public class ConversionStatistics {

	Map<IConcept, Map<IConcept, Double>> transitions = new HashMap<>();
	Map<IConcept, Double> allFrom = new HashMap<>();
	Map<IConcept, Double> allTo = new HashMap<>();

	public void reset() {
		allFrom.clear();
		allTo.clear();
		transitions.clear();
	}

	public void add(Conversion conversion, ILocator locator) {
		// in km2
		double area = Observations.INSTANCE.getArea(locator)/1000000;
		
		Double lost = allFrom.get(conversion.from);
		Double gained = allTo.get(conversion.to);
		lost = lost == null ? -area : lost - area;
		gained = gained == null ? area : gained + area;
		allFrom.put(conversion.from, lost);
		allTo.put(conversion.to, gained);
		Map<IConcept, Double> t = transitions.get(conversion.from);
		if (t == null) {
			t = new HashMap<>();
			transitions.put(conversion.from, t);
		}
		Double moved = t.get(conversion.to);
		t.put(conversion.to, moved == null ? area : moved + area);
	}
	
	public void print() {
		
	}

}
