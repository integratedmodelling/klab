package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.kactors.KeyValuePair;
import org.integratedmodelling.kactors.kactors.ParameterList;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Arguments with possible unnamed parameters, actually named _p1, _p2...
 * 
 * @author Ferd
 *
 */
public class KActorsArguments extends Parameters<String> {

	protected KActorsArguments() {
	}

	/*
	 * Should admit keys with values - if key is followed by non-key, it's its
	 * value. All unnamed parameters must be at the beginning.
	 */
	public KActorsArguments(ParameterList parameters) {

		// first element is a key, second a value. If first is null, it's an unnamed
		// parameter
		List<Pair<String, Object>> pairs = new ArrayList<>();
		boolean key = false;
		for (KeyValuePair pair : parameters.getPairs()) {
			if (pair.getTag() != null) {
				pairs.add(new Pair<>("tag", pair.getTag().substring(1)));
			} else if (pair.getKey() != null) {
				// add new key; leave open for next value if it's a :key form
				key = !pair.getKey().startsWith("!");
				pairs.add(
						new Pair<>(pair.getKey().substring(1), new KActorsValue(!pair.getKey().startsWith("!"), null)));
			} else if (pair.getName() == null) {
				if (key) {
					// had key before:
					pairs.get(pairs.size() - 1).setSecond(new KActorsValue(pair.getValue(), null));
					key = false;
				} else {
					pairs.add(new Pair<>(null, new KActorsValue(pair.getValue(), null)));
				}
			} else {
				put(pair.getName(), new KActorsValue(pair.getValue(), null));
			}
		}

		for (Pair<String, Object> pair : pairs) {
			if (pair.getFirst() == null) {
				putUnnamed(pair.getSecond());
			} else {
				put(pair.getFirst(), pair.getSecond());
			}
		}

//		for (KeyValuePair pair : parameters.getPairs()) {
//			if (pair.getKey() != null) {
//				put(pair.getKey().substring(1), new KActorsValue(!pair.getKey().startsWith("!"), null));
//			} else if (pair.getTag() != null) {
//				put("tag", pair.getTag().substring(1));
//			} else if (pair.getName() == null) {
//				putUnnamed(new KActorsValue(pair.getValue(), null));
//			} else {
//				put(pair.getName(), new KActorsValue(pair.getValue(), null));
//			}
//		}
	}

}
