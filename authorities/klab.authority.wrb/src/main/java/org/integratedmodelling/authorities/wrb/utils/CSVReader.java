package org.integratedmodelling.authorities.wrb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.integratedmodelling.authorities.wrb.model.Qualifier;
import org.integratedmodelling.authorities.wrb.model.ReferenceSoilGroup;
import org.integratedmodelling.authorities.wrb.model.Specifier;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;

public class CSVReader {

	enum Voc {
		RSG, QUALIFIERS, Specifiers
	}

	public static void main(String[] args) throws IOException {

		Map<String, ReferenceSoilGroup> groups = new LinkedHashMap<>();
		Map<String, Qualifier> qualifiers = new LinkedHashMap<>();
		Map<String, Specifier> specifiers = new LinkedHashMap<>();

		try (InputStream input = CSVReader.class.getResource("/wrb2014/rsg2014.csv").openStream()) {
			CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(input));
			ReferenceSoilGroup group = null;
			int rank = 0;
			for (CSVRecord record : csvParser) {
				if (record.get(5) != null && !record.get(5).isEmpty()) {
					group = new ReferenceSoilGroup();
					group.setName(StringUtils.capitalize(record.get(5).trim().toLowerCase()));
					groups.put(group.getName(), group);
					rank = 0;
				} else if (record.get(4) != null && !record.get(4).isEmpty()) {
					rank = record.get(4).trim().startsWith("Supplementary") ? 2 : 1;
				} else {
					if (rank == 1) {
						// read admitted principal qualifiers
						Set<String> principal = new LinkedHashSet<>();
						for (int col = 6;; col++) {
							String qualifier = record.get(col);
							if (qualifier == null || qualifier.trim().isEmpty()) {
								break;
							}
							principal.add(qualifier.trim());
						}
						group.getPrincipalQualifiers().add(principal);
					} else if (rank == 2) {
						// read admitted supplementary qualifiers
						Set<String> supplementary = new LinkedHashSet<>();
						for (int col = 6;; col++) {
							String qualifier = record.get(col);
							if (qualifier == null || qualifier.trim().isEmpty()) {
								break;
							}
							supplementary.add(qualifier.trim());
						}
						group.getSupplementaryQualifiers().add(supplementary);
					}
				}
			}
		}

		Set<String> qs = new HashSet<>();
		qs.add(Voc.RSG.name());
		qs.add(Voc.Specifiers.name());
		qs.add(Voc.QUALIFIERS.name());

		try (InputStream input = CSVReader.class.getResource("/wrb2014/vocabulary2014.csv").openStream()) {
			CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(input));
			Voc vocabulary = null;
			String parentQualifier = null;
			for (CSVRecord record : csvParser) {
				if (record.get(4) != null && !record.get(4).trim().isEmpty() && qs.contains(record.get(4).trim())) {
					vocabulary = Voc.valueOf(record.get(4).trim());
				} else if (record.get(4) != null && !record.get(4).isEmpty()) {
					switch (vocabulary) {
					case QUALIFIERS:
						String code = record.get(4).trim();
						String name = record.get(5).trim();
						if (name == null || name.isEmpty()) {
							name = record.get(6).trim();
						} else {
							parentQualifier = name;
						}
						Qualifier qualifier = new Qualifier();
						qualifier.setName(name);
						qualifier.setCode(code);
						if (!name.equals(parentQualifier)) {
							qualifier.setParentQualifier(parentQualifier);
						}
						qualifier.setDescription(record.get(8));
						qualifiers.put(qualifier.getName(), qualifier);
						break;
					case RSG:
						code = record.get(4).trim();
						name = record.get(5).trim();
						ReferenceSoilGroup group = groups.get(name);
						group.setCode(code);
						group.setUri(record.get(7));
						String doc = "";
						for (int col = 8;; col++) {
							if (record.size() >= col || record.get(col) == null || record.get(col).trim().isEmpty()) {
								break;
							}
							doc += (doc.isEmpty() ? "" : "\n") + record.get(col);
						}
						group.setDescription(doc);
						break;
					case Specifiers:
						break;
					default:
						break;

					}
				}
			}
		}

		for (ReferenceSoilGroup group : groups.values()) {
			for (Set<String> s : group.getPrincipalQualifiers()) {
				for (String q : s) {
					if (!qualifiers.containsKey(q) && !isSpecified(q, specifiers)) {
						System.out.println("Reference qualifier " + q + " is undefined");
					}
				}
			}
		}

		System.out.println(JsonUtils.printAsJson(groups));
		System.out.println(JsonUtils.printAsJson(qualifiers));

	}

	private static boolean isSpecified(String q, Map<String, Specifier> specifiers) {
		// TODO Auto-generated method stub
		return false;
	}

}
