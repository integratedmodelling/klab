package org.integratedmodelling.authorities.wrb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.authorities.wrb.model.Identity;
import org.integratedmodelling.authorities.wrb.model.Qualifier;
import org.integratedmodelling.authorities.wrb.model.ReferenceSoilGroup;
import org.integratedmodelling.authorities.wrb.model.Specifier;
import org.integratedmodelling.authorities.wrb.model.Vocabulary;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.JsonUtils;

public class WRBUtils {

	private enum Voc {
		RSG, QUALIFIERS, Specifiers
	}

	/**
	 * Chomp away specifiers from the front of the term until no more modifiers are
	 * present, then present all together as a SpecifiedQualifier. Will digest more
	 * than one specifier but raise an exception if it's more than one. No
	 * validation other than term matching.
	 * 
	 * @param term
	 * @param vocabulary
	 * @return
	 * @throws KlabValidationException
	 */
	public static Identity.SpecifiedQualifier splitSpecifiers(String term, Vocabulary vocabulary)
			throws KlabValidationException {

		Identity.SpecifiedQualifier ret = new Identity.SpecifiedQualifier();
		List<String> specs = new ArrayList<>();

		while (true) {
			boolean found = false;
			for (String s : vocabulary.getSpecifiers().keySet()) {
				if (term.toUpperCase().startsWith(s.toUpperCase())) {
					term = term.substring(s.length());
					specs.add(s);
					found = true;
					break;
				}
			}
			if (!found) {
				term = StringUtils.capitalize(term.toLowerCase());
				if (vocabulary.getQualifiers().get(term) == null) {
					return null;
				}
				ret.setQualifier(vocabulary.getQualifiers().get(term));
				break;
			}
		}

		if (specs.size() > 0) {
			if (specs.size() > 1) {
				return null;
			}
			ret.setSpecifier(vocabulary.getSpecifiers().get(specs.get(0)));
		}

		return ret;
	}

	public static Vocabulary read2014() {

		Vocabulary ret = new Vocabulary("2014");

		try (InputStream input = WRBUtils.class.getResource("/wrb2014/rsg2014.csv").openStream()) {
			CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(input));
			ReferenceSoilGroup group = null;
			int rank = 0;
			for (CSVRecord record : csvParser) {
				if (record.get(5) != null && !record.get(5).isEmpty()) {
					group = new ReferenceSoilGroup();
					group.setName(StringUtils.capitalize(record.get(5).trim().toLowerCase()));
					ret.getGroups().put(group.getName(), group);
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
		} catch (Exception ex) {
			throw new KlabIOException(ex);
		}

		Set<String> qs = new HashSet<>();
		qs.add(Voc.RSG.name());
		qs.add(Voc.Specifiers.name());
		qs.add(Voc.QUALIFIERS.name());

		try (InputStream input = WRBUtils.class.getResource("/wrb2014/vocabulary2014.csv").openStream()) {
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
						ret.getQualifiers().put(qualifier.getName(), qualifier);
						break;
					case RSG:
						code = record.get(4).trim();
						name = record.get(5).trim();
						ReferenceSoilGroup group = ret.getGroups().get(name);
						group.setCode(code);
						group.setUri(record.get(7));
						String doc = "";
						for (int col = 8;; col++) {
							if (record.size() <= col || record.get(col) == null || record.get(col).trim().isEmpty()) {
								break;
							}
							doc += (doc.isEmpty() ? "" : "\n") + record.get(col);
						}
						group.setDescription(doc);
						break;
					case Specifiers:
						code = record.get(4).trim();
						name = record.get(5).trim();
						Specifier specifier = new Specifier();
						specifier.setName(name);
						specifier.setCode(code);
						specifier.setDescription(record.get(8));
						ret.getSpecifiers().put(specifier.getName(), specifier);
						break;
					default:
						break;

					}
				}
			}
		} catch (Exception ex) {
			throw new KlabIOException(ex);
		}

		for (ReferenceSoilGroup group : ret.getGroups().values()) {
			for (Set<String> s : group.getPrincipalQualifiers()) {
				for (String q : s) {
					if (splitSpecifiers(q, ret) == null) {
						throw new KlabValidationException("Reference qualifier " + q + " is undefined");
					}
				}
			}
		}

		return ret;
	}
	

	public static void main(String[] args) throws IOException {
		Vocabulary vocabulary = WRBUtils.read2014();
		System.out.println(JsonUtils.printAsJson(vocabulary));
	}
}
