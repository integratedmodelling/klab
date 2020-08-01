package org.integratedmodelling.authorities.wrb.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.authorities.wrb.model.Identity;
import org.integratedmodelling.authorities.wrb.model.Identity.SpecifiedQualifier;
import org.integratedmodelling.authorities.wrb.model.ReferenceSoilGroup;
import org.integratedmodelling.authorities.wrb.model.Vocabulary;
import org.integratedmodelling.authorities.wrb.utils.WRBUtils;
import org.integratedmodelling.contrib.jtopas.ReaderSource;
import org.integratedmodelling.contrib.jtopas.StandardTokenizer;
import org.integratedmodelling.contrib.jtopas.StandardTokenizerProperties;
import org.integratedmodelling.contrib.jtopas.Token;
import org.integratedmodelling.contrib.jtopas.Tokenizer;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.CollectionUtils;

import com.google.common.collect.Sets;

public class Parser {

	private Vocabulary vocabulary;

	public Parser(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	static Set<String> disjointSpecifiers;
	static Set<String> demotedSpecifiers;
	static Set<String> unsortedSpecifiers;

	static {
		disjointSpecifiers = new HashSet<>();
		disjointSpecifiers.add("Epi");
		disjointSpecifiers.add("Endo");
		disjointSpecifiers.add("Amphy");
		disjointSpecifiers.add("Ano");
		disjointSpecifiers.add("Kato");
		disjointSpecifiers.add("Panto");
		disjointSpecifiers.add("Bathy");

		demotedSpecifiers = new HashSet<>();
		demotedSpecifiers.add("Thapto");
		demotedSpecifiers.add("Bathy");
		demotedSpecifiers.add("Proto");
	}

	/**
	 * Parse a WRB string into the intermediate representation.
	 * 
	 * @param definition
	 * @return
	 * @throws KlabValidationException
	 */
	public Identity parse(String definition) throws KlabValidationException {

		StandardTokenizerProperties properties = new StandardTokenizerProperties();
		properties.setSeparators("(),");

		try (Tokenizer tokenizer = new StandardTokenizer(properties)) {

			tokenizer.setSource(new ReaderSource(new StringReader(definition)));
			WRBIdentity ret = new WRBIdentity();

			while (tokenizer.hasMoreTokens()) {
				Token token = tokenizer.nextToken();
				if (token.getType() == Token.EOF) {
					break;
				}
				if (token.getImage().equals("(")) {
					ret.openSpecifierGroup();
				} else if (token.getImage().equals(")")) {
					ret.closeSpecifierGroup();
				} else if (!token.getImage().equals(",")) {
					ret.addToken(token.getImage());
				}
			}

			ret.closeParsing();

			return ret.validate();
		}
	}

	/**
	 * Intermediate representation for a WRB identity, with methods to build a trait
	 * composition that represents it semantically in the terms of the CREA WRB
	 * vocabulary.
	 */
	public class WRBIdentity {

		private ReferenceSoilGroup rsg;
		private List<SpecifiedQualifier> principalQualifiers = new ArrayList<>();
		private List<SpecifiedQualifier> secondaryQualifiers = new ArrayList<>();
		private boolean supplementalMode;
		private Set<String> errors = new HashSet<>();

		/**
		 * Validate the input and build the final identity. The order is kept and is
		 * significant (ab != ba) only in principal qualifiers. The supplemental are
		 * sorted by ID, removing the specifier before sorting if Proto-, Bathy- or
		 * Thapto- are given.
		 * <p>
		 * Rules from WRB2014 (TODO have Giovanni vet them):
		 * <ul>
		 * <li>Raw qualifiers must be allowed by the RSG unless the allowed list is
		 * empty (i.e. there is no specification).</li>
		 * <li>Proto-, Bathy- and Thapto-qualifiers given as principal are moved to
		 * supplementary as per WRB2014 and sorted alphabetically according to the ID of
		 * the non-specified qualifier.</li>
		 * <li>Qualifiers with a parent are allowed for a RSG if the parent is.</li>
		 * <li>Supra- and Epi- are not compatible in the same RSG.</li>
		 * <li>Only one of Epi-, Endo-, Amphy-, Ano-, Kato-, Panto- and Bathy- is
		 * applicable in a RSG.</li>
		 * </ul>
		 */
		public Identity validate() throws KlabValidationException {

			Identity ret = new Identity();

			/*
			 * Main RSG must exist
			 */
			ret.setSoilGroup(this.rsg);
			ret.getErrors().addAll(errors);
			if (rsg == null) {
				ret.getErrors().add("No soil group specified");
			} else {

				/*
				 * Check that all classifiers belong to the RSG and are compatible within the
				 * RSG constraints.
				 */
				if (rsg.getPrincipalQualifiers().size() > 0) {
					Map<Set<String>, Integer> useCount = new HashMap<>();
					Set<String> principalIds = new HashSet<>();
					for (SpecifiedQualifier sq : this.principalQualifiers) {
						principalIds.add(sq.getQualifier().getName());
						boolean ok = false;
						for (Set<String> qset : rsg.getPrincipalQualifiers()) {
							useCount.put(qset, 0);
							if (qset.contains(sq.getQualifier().getName())
									|| (sq.getQualifier().getParentQualifier() != null
											&& qset.contains(sq.getQualifier().getParentQualifier()))) {
								ok = true;
								useCount.put(qset, useCount.get(qset) + 1);
							}
						}
						if (!ok) {
							errors.add("Qualifier " + sq.getQualifier().getName()
									+ " is not allowed as principal qualifier for soil group " + rsg.getName());
						}
					}

					for (Set<String> sq : useCount.keySet()) {
						if (useCount.get(sq) > 1) {
							errors.add(
									"Only one principal qualifier in the set " + sq + " can be used in a definition");
						}
					}
				}

				if (rsg.getSupplementaryQualifiers().size() > 0) {

					Set<String> secondaryIds = new HashSet<>();
					Map<Set<String>, Integer> useCount = new HashMap<>();

					for (SpecifiedQualifier sq : this.secondaryQualifiers) {
						secondaryIds.add(sq.getQualifier().getName());
						boolean ok = false;
						for (Set<String> qset : rsg.getSupplementaryQualifiers()) {
							useCount.put(qset, 0);
							if (qset.contains(sq.getQualifier().getName())
									|| (sq.getQualifier().getParentQualifier() != null
											&& qset.contains(sq.getQualifier().getParentQualifier()))) {
								ok = true;
								useCount.put(qset, useCount.get(qset) + 1);
							}
						}
						if (!ok) {
							errors.add("Qualifier " + sq.getQualifier().getName()
									+ " is not allowed as supplementary qualifier for soil group " + rsg.getName());
						}
					}

					for (Set<String> sq : useCount.keySet()) {
						if (useCount.get(sq) > 1) {
							errors.add("Only one supplementary qualifier in the set " + sq
									+ " can be used in a definition");
						}
					}
				}
			}

			/*
			 * Check for any sibling qualifiers in the lists
			 */
			Map<Set<String>, Integer> useCount = new HashMap<>();
			List<Set<String>> families = new ArrayList<>(vocabulary.getQualifierFamilies().values());
			families.add(Sets.newHashSet("Supra", "Epi"));

			for (Set<String> siblings : families) {
				for (String qq : siblings) {
					if (principalQualifiers.contains(qq)) {
						useCount.put(siblings, useCount.get(siblings) == null ? 0 : (useCount.get(siblings) + 1));
					}
				}
			}

			for (Set<String> sq : useCount.keySet()) {
				if (useCount.get(sq) > 1) {
					errors.add("Only one principal qualifier in the family " + sq + " can be used in a definition");
				}
			}

			useCount.clear();
			for (Set<String> siblings : families) {
				for (String qq : siblings) {
					if (secondaryQualifiers.contains(qq)) {
						useCount.put(siblings, useCount.get(siblings) == null ? 0 : (useCount.get(siblings) + 1));
					}
				}
			}

			for (Set<String> sq : useCount.keySet()) {
				if (useCount.get(sq) > 1) {
					errors.add("Only one supplementary qualifier in the family " + sq + " can be used in a definition");
				}
			}

			/*
			 * check for disjointness of specifiers in both lists
			 */
			Set<String> used = new HashSet<>();
			for (SpecifiedQualifier qq : CollectionUtils.join(principalQualifiers, secondaryQualifiers)) {
				if (qq.getSpecifier() != null && disjointSpecifiers.contains(qq.getSpecifier().getName())) {
					used.add(qq.getSpecifier().getName());
				}
			}

			if (used.size() > 1) {
				errors.add("Specifiers " + used + " cannot be used together in the same definition");
			}

			/*
			 * perform specifier normalization, moving whatever needs to in the
			 * supplementary list.
			 */
			Set<SpecifiedQualifier> toDemote = new HashSet<>();
			for (SpecifiedQualifier q : principalQualifiers) {
				if (q.getSpecifier() != null && demotedSpecifiers.contains(q.getSpecifier().getName())) {
					toDemote.add(q);
				}
			}

			/*
			 * mount the qualifier lists in the result
			 */
			for (SpecifiedQualifier q : principalQualifiers) {
				if (toDemote.contains(q)) {
					ret.getSupplementaryQualifiers().add(q);
				} else {
					ret.getPrincipalQualifiers().add(q);
				}
			}

			ret.getSupplementaryQualifiers().addAll(secondaryQualifiers);

			/*
			 * normalize the order of the supplementary qualifiers
			 */
			ret.getSupplementaryQualifiers().sort(new Comparator<SpecifiedQualifier>() {

				@Override
				public int compare(SpecifiedQualifier o1, SpecifiedQualifier o2) {
					String toCompare1 = o1.getStringForm();
					if (o1.getSpecifier() != null && demotedSpecifiers.contains(o1.getSpecifier().getName())) {
						toCompare1 = o1.getQualifier().getName();
					}
					String toCompare2 = o2.getStringForm();
					if (o2.getSpecifier() != null && demotedSpecifiers.contains(o2.getSpecifier().getName())) {
						toCompare2 = o2.getQualifier().getName();
					}
					return toCompare1.compareTo(toCompare2);
				}
			});

			ret.getErrors().addAll(errors);

			return ret;
		}

		public void openSpecifierGroup() {
			supplementalMode = true;
		}

		public void closeSpecifierGroup() {
			if (!supplementalMode) {
				error("syntax error: mismatched parentheses");
			}
			supplementalMode = false;
		}

		public void closeParsing() {
			if (supplementalMode) {
				error("syntax error: mismatched parentheses");
			}
		}

		/*
		 * just match and collect the input, checking for unknown tokens, >1 RSG and for
		 * parenthesis matching. The rest is done at validation.
		 */
		public void addToken(String token) {

			token = StringUtils.capitalize(token.toLowerCase());

			SpecifiedQualifier qualifier = null;
			ReferenceSoilGroup group = vocabulary.getGroups().get(token);
			if (group == null) {
				qualifier = WRBUtils.splitSpecifiers(token, vocabulary);
			}

			if (group != null) {
				if (this.rsg == null) {
					this.rsg = group;
				} else {
					this.errors.add("Cannot have more than one soil group: " + group.getName() + " added after "
							+ this.rsg.getName());
				}
			} else if (qualifier != null) {
				if (supplementalMode) {
					secondaryQualifiers.add(qualifier);
				} else {
					principalQualifiers.add(qualifier);
				}
			} else {
				this.errors.add("Unrecognized token: " + token);
			}
		}

		private void error(String string) {
			this.errors.add(string);
		}

		public boolean isSpecMode() {
			return supplementalMode;
		}

		public void setSpecMode(boolean specMode) {
			this.supplementalMode = specMode;
		}

		public Set<String> getErrors() {
			return errors;
		}

		public void setErrors(Set<String> errors) {
			this.errors = errors;
		}
	}

}
