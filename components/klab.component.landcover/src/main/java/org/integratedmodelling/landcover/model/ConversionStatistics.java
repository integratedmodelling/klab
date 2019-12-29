package org.integratedmodelling.landcover.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Concepts;
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
		double area = Observations.INSTANCE.getArea(locator) / 1000000;

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

	public String summarize(double totalArea) {

		int maxSize = 0;
		Map<IConcept, String> labels = new HashMap<>();
		Set<IConcept> allConcepts = new HashSet<>(allFrom.keySet());
		allConcepts.addAll(allTo.keySet());

		/*
		 * build labels
		 */
		for (IConcept c : allConcepts) {
			String label = Concepts.INSTANCE.getDisplayName(c);
			if (label.length() > maxSize) {
				maxSize = label.length();
			}
			labels.put(c, label);
		}

		maxSize += 3;

		StringBuffer ret = new StringBuffer(1024);

		double total = 0;
		NumberFormat format = NumberFormat.getInstance();
		
		List<Entry<IConcept, Double>> froms = new ArrayList<>(allFrom.entrySet()); 
		List<Entry<IConcept, Double>> tos = new ArrayList<>(allTo.entrySet()); 
		double[] columnTotals = new double[allTo.size()];
		
		/* one row/column for the correspondent in table; another for totals */
		for (int row = 0; row <= allFrom.size() + 1; row++) {

			double rowTotal = 0;
			for (int column = 0; column <= allTo.size() + 1; column++) {
				String token = "Sperma";
				if (row == 0) {
					if (column == 0) {
						token = "Source";
					} else if (column == allTo.size() + 1) {
						token = "Totals";
					} else if (column > 0) {
						token = Concepts.INSTANCE.getDisplayName(tos.get(column -1).getKey());
					}
				} else if (row == allFrom.size() + 1) {
					if (column == 0) {
						token = "Totals";
					} else if (column == allTo.size() + 1) {
						token = format.format(total);
					} else {
						token = format.format(columnTotals[column - 1]);
					}
				} else {
					// intermediate rows
					if (column == 0) {
						// from concept
						token = Concepts.INSTANCE.getDisplayName(froms.get(row-1).getKey());
					} else if (column == allTo.size() + 1) {
						// row total
						token = format.format(rowTotal);
						rowTotal = 0;
					} else {
						// column value
						IConcept from = froms.get(row-1).getKey();
						IConcept to = tos.get(column-1).getKey();
						Double dvalue = transitions.get(from).get(to);
						double value = dvalue == null ? 0 : dvalue;
						// add to row
						token = format.format(value);
						// add to rowTotal
						rowTotal += value;
						// add to total
						total += value;
						// add to column totals
						columnTotals[column-1] += value;
					}
				}
				ret.append(StringUtils.rightPad(token, maxSize));
			}

			ret.append("\n");

		}

		return ret.toString();
	}

}
